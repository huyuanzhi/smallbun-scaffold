package org.smallbun.fast.manage.org.service.impl;

import org.smallbun.fast.common.base.BaseTreeDataServiceImpl;
import org.smallbun.fast.manage.org.dao.SysOrgMapper;
import org.smallbun.fast.manage.org.entity.SysOrgEntity;
import org.smallbun.fast.manage.org.service.SysOrgService;
import org.smallbun.fast.manage.org.vo.SysOrgVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

import static org.smallbun.framework.constant.UrlPrefixConstant.UNIQUE;
import static org.smallbun.framework.toolkit.AutoMapperUtil.mapping;

/**
 * 系统部门接口
 *
 * @author SanLi
 * Created by 2689170096@qq.com on 2018/8/3
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOrgServiceImpl extends BaseTreeDataServiceImpl<SysOrgMapper, SysOrgEntity> implements SysOrgService {


	/**
	 * model
	 * @param request
	 * @return
	 */
	@Override
	public SysOrgVO model(HttpServletRequest request) {
		if (!request.getRequestURI().contains(UNIQUE)) {
			return StringUtils.isEmpty(request.getParameter(ID)) ?
					new SysOrgVO() :
					mapping(getById(request.getParameter(ID)), new SysOrgVO());
		}
		return new SysOrgVO();
	}

	/**
	 * 根据角色id获取菜单
	 *
	 * @param id
	 * @return
	 */
	@Override
	public List<SysOrgEntity> findByRoleId(Serializable id) {
		return baseMapper.findByRoleId(id);
	}

	/**
	 * <p>
	 * TableId 注解存在更新记录，否插入一条记录
	 * </p>
	 *
	 * @param entity 实体对象
	 * @return boolean
	 */
	@Override
	public boolean saveOrUpdate(SysOrgEntity entity) {
		//获取父级ids
		SysOrgEntity parent = baseMapper.selectById(entity.getParentId());
		//设置ids
		entity.setParentIds(parent.getParentIds() + "," + parent.getId());
		return super.saveOrUpdate(entity);
	}
}
