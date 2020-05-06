/*
 * Copyright (c) 2018-2020. ‭‭‭‭‭‭‭‭‭‭‭‭[zuoqinggang] www.pingfangushi.com
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package cn.smallbun.scaffold.manage.web;

import cn.smallbun.scaffold.framework.common.result.ApiRestResult;
import cn.smallbun.scaffold.framework.common.toolkit.StringUtil;
import cn.smallbun.scaffold.framework.demo.annotation.DemoEnvironment;
import cn.smallbun.scaffold.framework.log.annotation.Log;
import cn.smallbun.scaffold.framework.log.enmus.Platform;
import cn.smallbun.scaffold.framework.mybatis.page.Page;
import cn.smallbun.scaffold.framework.mybatis.page.PageModel;
import cn.smallbun.scaffold.framework.validation.group.AddGroup;
import cn.smallbun.scaffold.framework.validation.group.UpdateGroup;
import cn.smallbun.scaffold.framework.web.BaseResource;
import cn.smallbun.scaffold.manage.entity.SysDictItemEntity;
import cn.smallbun.scaffold.manage.entity.SysRoleEntity;
import cn.smallbun.scaffold.manage.enums.RoleStatus;
import cn.smallbun.scaffold.manage.pojo.authority.UpdateAuthorizeBO;
import cn.smallbun.scaffold.manage.pojo.role.RoleAddOrUpdateVO;
import cn.smallbun.scaffold.manage.pojo.role.RoleAuthVO;
import cn.smallbun.scaffold.manage.pojo.role.RoleQuery;
import cn.smallbun.scaffold.manage.pojo.role.RoleQueryResultVO;
import cn.smallbun.scaffold.manage.service.ISysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.smallbun.scaffold.common.constant.PathConstant.MANAGE_API_PATH;
import static cn.smallbun.scaffold.framework.log.enmus.Operate.*;
import static cn.smallbun.scaffold.framework.mybatis.utils.MappingHelp.mapping;
import static cn.smallbun.scaffold.manage.web.SysRoleResource.API;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author SanLi Automatic generated
 * Created by qinggang.zuo@gmail.com / 2689170096@qq.com on  2019-05-14
 */

@Validated
@RestController
@Api(tags = API)
@Log(module = API)
@RequestMapping(MANAGE_API_PATH + "/role")
public class SysRoleResource extends BaseResource {

    final static String API = "系统角色API";

    /**
     * 添加
     *
     * @param vo {@link RoleAddOrUpdateVO} VO对象
     * @return {@link ApiRestResult} 通用返回对象
     */
    @DemoEnvironment
    @Log(feature = "新增角色", action = ADD, platform = Platform.MANAGE)
    @PreAuthorize("hasAuthority('manage:interface:role:add')")
    @ApiOperation(value = "新增角色", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 1)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiRestResult<Boolean> add(@Validated(value = { AddGroup.class }) @RequestBody RoleAddOrUpdateVO vo) {
        // 封装数据
        SysRoleEntity role = new SysRoleEntity();
        BeanUtils.copyProperties(vo, role);
        return new ApiRestResult<Boolean>().result(iSysRoleService.save(role)).build();
    }

    /**
     * 修改角色
     *
     * @param vo {@link RoleAddOrUpdateVO} 添加对象
     * @return {@link ApiRestResult} 通用返回对象
     */
    @DemoEnvironment
    @Log(feature = "修改角色", action = UPDATE, platform = Platform.MANAGE)
    @PreAuthorize("hasAuthority('manage:interface:role:update')")
    @ApiOperation(value = "修改角色", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 2)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiRestResult<Boolean> updateById(@Validated(value = { UpdateGroup.class }) @RequestBody RoleAddOrUpdateVO vo) {
        // 封装数据
        SysRoleEntity role = new SysRoleEntity();
        BeanUtils.copyProperties(vo, role);
        return new ApiRestResult<Boolean>().result(iSysRoleService.updateById(role)).build();
    }

    /**
     * 根据角色ID删除并清除缓存
     *
     * @param ids {@link String} ids
     * @return {@link ApiRestResult} 通用返回对象
     */
    @DemoEnvironment
    @Log(feature = "根据ID删除角色", action = REMOVE, platform = Platform.MANAGE)
    @ApiOperation(value = "根据ID删除角色", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 3)
    @DeleteMapping(value = "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('manage:interface:role:remove')")
    public ApiRestResult<Boolean> removeByIds(@PathVariable String ids) {
        return new ApiRestResult<Boolean>().result(
            iSysRoleService.removeByIds(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT))))
            .build();
    }

    /**
     * 分页查询角色信息
     *
     * @param pageModel {@link PageModel} 对象
     * @return {@link ApiRestResult} 通用返回对象
     */
    @Log(feature = "分页查询角色列表", action = FETCH, platform = Platform.MANAGE)
    @PreAuthorize("hasAnyAuthority('manage:interface:role:fetch')")
    @ApiOperation(value = "分页查询角色列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 4)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiRestResult<Page<RoleQueryResultVO>> getPage(PageModel pageModel, RoleQuery vo) {
        // 封装查询数据
        SysRoleEntity role = new SysRoleEntity();
        BeanUtils.copyProperties(vo, role);
        IPage<SysRoleEntity> iPage = iSysRoleService.page(pageModel, Wrappers.query(role));
        // 封装返回数据
        Page<RoleQueryResultVO> page = new Page<>();
        return new ApiRestResult<Page<RoleQueryResultVO>>().result(page).build();
    }

    /**
     * 通过ID查询角色信息
     *
     * @param id {@link String} id
     * @return {@link ApiRestResult} 通用返回对象
     */
    @Log(feature = "根据ID查询角色", action = FETCH, platform = Platform.MANAGE)
    @PreAuthorize("hasAuthority('manage:interface:role:fetch')")
    @ApiOperation(value = "根据ID查询角色", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 5)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiRestResult<RoleQueryResultVO> getById(@PathVariable String id) {
        // 转换为VO
        RoleQueryResultVO role = mapping(iSysRoleService.getById(id), new RoleQueryResultVO());
        return new ApiRestResult<RoleQueryResultVO>().result(role).build();
    }

    /**
     * 角色唯一验证
     *
     * @param value {@link SysDictItemEntity} value
     * @return {@link ApiRestResult} 通用返回对象
     */
    @Log(feature = "唯一角色验证", action = FETCH, platform = Platform.MANAGE)
    @ApiOperation(value = "唯一角色验证", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 6)
    @PreAuthorize("hasAuthority('manage:interface:role:unique')")
    @GetMapping(value = "unique", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiRestResult<Boolean> unique(SysRoleEntity value) {
        return new ApiRestResult<Boolean>().result(iSysRoleService.unique(value)).build();
    }

    /**
     * 根据角色查询用户具有权限
     *
     * @param id {@link String} 角色ID
     * @return {@link ApiRestResult} 通用返回对象
     */
    @Log(feature = "根据ID查询权限", action = FETCH, platform = Platform.MANAGE)
    @ApiOperation(value = "根据ID查询权限", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 7)
    @PreAuthorize("hasAuthority('manage:interface:role:authorize')")
    @GetMapping(value = "auth/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiRestResult<List<RoleAuthVO>> getRoleAuth(@PathVariable(value = "id") String id) {
        return new ApiRestResult<List<RoleAuthVO>>().result(iSysRoleService.getRoleAuthById(id))
            .build();
    }

    /**
     * 权限配置
     *
     * @param auth {@link UpdateAuthorizeBO} auth
     * @return {@link ApiRestResult} 通用返回对象
     */
    @DemoEnvironment
    @Log(feature = "角色权限设置", action = UPDATE, platform = Platform.MANAGE)
    @ApiOperation(value = "角色权限设置", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 8)
    @PreAuthorize("hasAuthority('manage:interface:role:authorize')")
    @PutMapping(value = "update/authorize", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiRestResult<Boolean> updateAuthorize(@RequestBody UpdateAuthorizeBO auth) {
        return new ApiRestResult<Boolean>().result(iSysRoleService.updateAuthorize(auth)).build();
    }

    /**
     * 根据ID更新角色类型状态
     *
     * @param id     id
     * @param status 状态
     * @return 结果
     */
    @Log(feature = "根据ID更新角色类型状态", action = UPDATE, platform = Platform.MANAGE)
    @PreAuthorize("hasAuthority('manage:interface:role:update')")
    @ApiOperation(value = "根据ID更新角色类型状态", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperationSupport(order = 9)
    @PutMapping(value = "/{id}/status/{status}")
    public ApiRestResult<Boolean> updateStatusById(@PathVariable String id,
                                                   @PathVariable RoleStatus status) {
        return new ApiRestResult<Boolean>().result(iSysRoleService.updateStatusById(id, status))
            .build();
    }

    /**
     * 注入角色业务接口
     */
    private final ISysRoleService iSysRoleService;

    public SysRoleResource(ISysRoleService iSysRoleService) {
        this.iSysRoleService = iSysRoleService;
    }
}
