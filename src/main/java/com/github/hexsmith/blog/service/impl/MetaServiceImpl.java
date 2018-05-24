package com.github.hexsmith.blog.service.impl;

import com.github.hexsmith.blog.constant.WebConstant;
import com.github.hexsmith.blog.dto.MetaDto;
import com.github.hexsmith.blog.dto.Types;
import com.github.hexsmith.blog.exception.BizException;
import com.github.hexsmith.blog.mapper.MetaVoMapper;
import com.github.hexsmith.blog.model.vo.ContentVo;
import com.github.hexsmith.blog.model.vo.MetaVo;
import com.github.hexsmith.blog.model.vo.MetaVoExample;
import com.github.hexsmith.blog.model.vo.RelationshipVoKey;
import com.github.hexsmith.blog.service.ContentService;
import com.github.hexsmith.blog.service.MetaService;
import com.github.hexsmith.blog.service.RelationshipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hexsmith
 * @since 2018-05-23 23:18
 * @version v1.0
 */
@Service
public class MetaServiceImpl implements MetaService {

    @Resource
    private MetaVoMapper metaDao;

    @Resource
    private RelationshipService relationshipService;

    @Resource
    private ContentService contentService;

    @Override
    public MetaDto getMeta(String type, String name) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
            return metaDao.selectDtoByNameAndType(name, type);
        }
        return null;
    }

    @Override
    public Integer countMeta(Integer mid) {
        return metaDao.countWithSql(mid);
    }

    @Override
    public List<MetaVo> getMetas(String types) {
        if (StringUtils.isNotBlank(types)) {
            MetaVoExample metaVoExample = new MetaVoExample();
            metaVoExample.setOrderByClause("sort desc, mid desc");
            metaVoExample.createCriteria().andTypeEqualTo(types);
            return metaDao.selectByExample(metaVoExample);
        }
        return null;
    }

    @Override
    public List<MetaDto> getMetaList(String type, String orderby, int limit) {
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isBlank(orderby)) {
                orderby = "count desc, a.mid desc";
            }
            if (limit < 1 || limit > WebConstant.MAX_POSTS) {
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaDao.selectFromSql(paraMap);
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(int mid) {
        MetaVo metas = metaDao.selectByPrimaryKey(mid);
        if (null != metas) {
            String type = metas.getType();
            String name = metas.getName();

            metaDao.deleteByPrimaryKey(mid);

            List<RelationshipVoKey> rlist = relationshipService.getRelationshipById(null, mid);
            if (null != rlist) {
                for (RelationshipVoKey r : rlist) {
                    ContentVo contents = contentService.getContents(String.valueOf(r.getCid()));
                    if (null != contents) {
                        ContentVo temp = new ContentVo();
                        temp.setCid(r.getCid());
                        if (type.equals(Types.CATEGORY.getType())) {
                            temp.setCategories(reMeta(name, contents.getCategories()));
                        }
                        if (type.equals(Types.TAG.getType())) {
                            temp.setTags(reMeta(name, contents.getTags()));
                        }
                        contentService.updateContentByCid(temp);
                    }
                }
            }
            relationshipService.deleteById(null, mid);
        }
    }

    @Override
    @Transactional
    public void saveMeta(String type, String name, Integer mid) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
            MetaVoExample metaVoExample = new MetaVoExample();
            metaVoExample.createCriteria().andTypeEqualTo(type).andNameEqualTo(name);
            List<MetaVo> metaVos = metaDao.selectByExample(metaVoExample);
            MetaVo metas;
            if (metaVos.size() != 0) {
                throw new BizException("已经存在该项");
            } else {
                metas = new MetaVo();
                metas.setName(name);
                if (null != mid) {
                    MetaVo original = metaDao.selectByPrimaryKey(mid);
                    metas.setMid(mid);
                    metaDao.updateByPrimaryKeySelective(metas);
//                    更新原有文章的categories
                    contentService.updateCategory(original.getName(), name);
                } else {
                    metas.setType(type);
                    metaDao.insertSelective(metas);
                }
            }
        }
    }

    @Override
    @Transactional
    public void saveMetas(Integer cid, String names, String type) {
        if (null == cid) {
            throw new BizException("项目关联id不能为空");
        }
        if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)) {
            String[] nameArr = StringUtils.split(names, ",");
            for (String name : nameArr) {
                this.saveOrUpdate(cid, name, type);
            }
        }
    }

    private void saveOrUpdate(Integer cid, String name, String type) {
        MetaVoExample metaVoExample = new MetaVoExample();
        metaVoExample.createCriteria().andTypeEqualTo(type).andNameEqualTo(name);
        List<MetaVo> metaVos = metaDao.selectByExample(metaVoExample);

        int mid;
        MetaVo metas;
        if (metaVos.size() == 1) {
            metas = metaVos.get(0);
            mid = metas.getMid();
        } else if (metaVos.size() > 1) {
            throw new BizException("查询到多条数据");
        } else {
            metas = new MetaVo();
            metas.setSlug(name);
            metas.setName(name);
            metas.setType(type);
            metaDao.insertSelective(metas);
            mid = metas.getMid();
        }
        if (mid != 0) {
            Long count = relationshipService.countById(cid, mid);
            if (count == 0) {
                RelationshipVoKey relationships = new RelationshipVoKey();
                relationships.setCid(cid);
                relationships.setMid(mid);
                relationshipService.insertVo(relationships);
            }
        }
    }


    private String reMeta(String name, String metas) {
        String[] ms = StringUtils.split(metas, ",");
        StringBuilder sbuf = new StringBuilder();
        for (String m : ms) {
            if (!name.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }

    @Override
    @Transactional
    public void saveMeta(MetaVo metas) {
        if (null != metas) {
            metaDao.insertSelective(metas);
        }
    }

    @Override
    @Transactional
    public void update(MetaVo metas) {
        if (null != metas && null != metas.getMid()) {
            metaDao.updateByPrimaryKeySelective(metas);
        }
    }
}