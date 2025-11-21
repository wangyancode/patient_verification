package com.dincher.project.system.controller;

import com.dincher.common.constant.UserConstants;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.ServletUtils;
import com.dincher.common.utils.StringUtils;
import com.dincher.common.utils.poi.ExcelUtil;
import com.dincher.framework.security.service.SysPermissionService;
import com.dincher.framework.security.service.TokenService;
import com.dincher.framework.web.controller.BaseController;
import com.dincher.framework.web.domain.AjaxResult;
import com.dincher.framework.web.domain.LoginUser;
import com.dincher.framework.web.page.TableDataInfo;
import com.dincher.project.system.domain.entity.Role;
import com.dincher.project.system.service.RoleService;
import com.dincher.project.system.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息
 * 
 *
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController
{
    @Resource
    private RoleService roleService;

    @Resource
    private TokenService tokenService;
    
    @Resource
    private SysPermissionService permissionService;
    
    @Resource
    private UserService userService;

    @GetMapping("/list")
    public TableDataInfo list(Role role)
    {
        startPage();
        List<Role> list = roleService.selectRoleList(role);

        return getDataTable(list);
    }

    @GetMapping("/export")
    public AjaxResult export(Role role)
    {
        List<Role> list = roleService.selectRoleList(role);
        ExcelUtil<Role> util = new ExcelUtil<Role>(Role.class);
        return util.exportExcel(list, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable Integer roleId)
    {
        return AjaxResult.success(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Role role)
    {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role)))
        {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Role role)
    {
//        roleService.checkRoleAllowed(role);
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role)))
        {
            return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        
        if (roleService.updateRole(role) > 0)
        {
            // 更新缓存用户权限
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin())
            {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.getById(loginUser.getUser().getUserId()));
                tokenService.setLoginUser(loginUser);
            }
            return AjaxResult.success();
        }
        return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */

    @PutMapping("/dataScope")
    public AjaxResult dataScope(@RequestBody Role role)
    {
//        roleService.checkRoleAllowed(role);
        return toAjax(roleService.authDataScope(role));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody Role role)
    {
//        roleService.checkRoleAllowed(role);
        role.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable Integer[] roleIds)
    {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        return AjaxResult.success(roleService.selectRoleAll());
    }
}