package com.dincher.project.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.StringUtils;
import com.dincher.framework.web.controller.BaseController;
import com.dincher.framework.web.domain.RequestQueryBody;
import com.dincher.framework.web.domain.ResponseEntity;
import com.dincher.framework.web.page.TableDataInfo;
import com.dincher.framework.web.util.ResponseUtil;
import com.dincher.project.system.domain.dto.ChangeStatusDTO;
import com.dincher.project.system.domain.dto.ResetPwdDTO;
import com.dincher.project.system.domain.dto.UserPageDTO;
import com.dincher.project.system.domain.vo.UserRegionVO;
import com.dincher.project.system.domain.vo.UserVO;
import com.dincher.project.system.service.UserRegionService;
import com.dincher.project.system.service.UserRoleService;
import com.dincher.project.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户账号信息(sys_user)控制层
 *
 * @author wangxiao
 * @date 2023-07-25 14:03:28
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户账号信息(sys_user)APi")
public class UserController extends BaseController{

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserRegionService userRegionService;

    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询")
    public TableDataInfo<List<UserVO>> page(@RequestBody RequestQueryBody<UserPageDTO> requestQueryBody) {
        startPage(requestQueryBody);
        List<UserVO> UserVOList = userService.selectDataList(requestQueryBody.getParam());
        return getDataTable(UserVOList);
    }

    @PostMapping(value ="/save")
    @ApiOperation(value = "新增数据")
    public ResponseEntity insert(@RequestBody UserVO userVO) {
        UserVO account = userService.getOne(new QueryWrapper<UserVO>().eq("user_account", userVO.getUserAccount()));
        if(StringUtils.isNotNull(account)){
            return ResponseUtil.error("账号已存在");
        }
        return ResponseUtil.success(userService.saveData(userVO));
    }

    /**
     * @author wangxiao
     * @date 2023-07-25 16:27:10
     *
     */
    @PutMapping(value ="/update")
    @ApiOperation(value = "修改数据")
    public ResponseEntity update(@RequestBody UserVO userVO) {
        return ResponseUtil.success(userService.updateData(userVO));
    }

    @DeleteMapping(value ="/delete")
    @ApiOperation(value = "删除数据")
    public ResponseEntity delete(@RequestBody List<Integer> ids) {
        List<UserVO> userList = userService.list(new QueryWrapper<UserVO>().in("user_id", ids));
        for (UserVO userVO : userList) {
            if(userVO.getUserStatus().equals("0")){
               return ResponseUtil.error("用户已启用，不可删除");
            }
        }
        // 删除用户与角色关联
        userRoleService.deleteDataBatch(ids.stream().toArray(Integer[]::new));
        userRegionService.remove(new QueryWrapper<UserRegionVO>().in("user_id",ids));
        return ResponseUtil.success(userService.removeByIds(ids));
    }

    @PutMapping("/resetPwd")
    @ApiOperation(value = "重置密码")
    public ResponseEntity resetPwd(@RequestBody ResetPwdDTO resetPwdDTO)
    {
        UserVO userVO = new UserVO();
        userVO.setUserId(resetPwdDTO.getUserId());
        userVO.setUserPassword(SecurityUtils.encryptPassword(resetPwdDTO.getUserPassword()));
        return ResponseUtil.success(userService.updateById(userVO));
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "修改用户状态")
    public ResponseEntity changeStatus(@RequestBody ChangeStatusDTO changeStatusDTO)
    {
        UserVO userVO = new UserVO();
        userVO.setUserId(changeStatusDTO.getUserId());
        userVO.setUserStatus(changeStatusDTO.getUserStatus());
        return ResponseUtil.success(userService.updateById(userVO));
    }

    @RequestMapping("/updatePwd")
    @ApiOperation(value = "密码修改")
    public ResponseEntity<Boolean> updatePwd(String oldPassword, String newPassword)
    {

        //强密码验证
        if (null != newPassword && null != newPassword) {
            boolean checkPass = this.validateStrongPassword(newPassword);
            if (!checkPass) {
                throw new UsernameNotFoundException("密码规则说明：小写字母+大写字母+数字+特殊符号+长度为8-16位！");
            }
        }
        return userService.updatePwd(oldPassword, newPassword);
    }

    // 正则表达式用于验证密码强度
    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";

    /**
     * 验证密码是否符合强密码规则
     *
     * @param password 待验证的密码
     * @return 如果密码符合规则返回true，否则返回false
     */
    public static boolean validateStrongPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    @GetMapping("/getPersonalInfo")
    @ApiOperation(value = "个人中心")
    public ResponseEntity<UserVO> getPersonalInfo() {
        return ResponseUtil.success(userService.getPersonalInfo());
    }

    @PostMapping("/updateAvatar")
    @ApiOperation(value = "用户头像修改")
    public ResponseEntity updateAvatar(Integer documentId) {
        return ResponseUtil.success(userService.updateAvatar(documentId));
    }

}

