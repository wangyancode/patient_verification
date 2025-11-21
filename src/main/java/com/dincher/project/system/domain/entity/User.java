package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户账号信息(sys_user)数据库映射实体类
 *
 * @author wangxiao
 * @date 2023-07-25 14:03:27
 */
@Data
public class User extends CommonEntity{ 

    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer userId;
    
    @TableField("user_account")
    @ApiModelProperty(value = "账号")
    private String userAccount;
    
    @TableField("user_password")
    @ApiModelProperty(value = "密码")
    private String userPassword;
    
    @TableField("user_name")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @TableField("user_sex")
    @ApiModelProperty(value = "性别")
    private String userSex;
    
    @TableField("user_status")
    @ApiModelProperty(value = "启用状态 0 正常 1 停用")
    private String userStatus;

    @TableField("document_id")
    @ApiModelProperty(value = "上传头像返回的附件ID")
    private Integer documentId;

    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField("reset_password_flag")
    @ApiModelProperty(value = "是否修改密码标记：0-是，1-否")
    private String resetPasswordFlag;

    @TableField("positional_title")
    @ApiModelProperty(value = "职称值：来自字典值")
    private String positionalTitle;

    @TableField("region_type")
    @ApiModelProperty(value = "所属区域类型(必须固定，对应字典管理类型)：病区-user_ward，科室-user_dept")
    private String regionType;
    
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色列表")
    private List<Role> roles;


    //类型 1管理员 2普通技师 3科主任
    private Integer type;

    public User(Integer userId)
    {
        this.userId = userId;
    }

    public User() {

    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Integer userId)
    {
        return userId != null && 1L == userId;
    }

    
}

