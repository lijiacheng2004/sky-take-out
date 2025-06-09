package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工登录时传递的数据模型")//对DTO实体对象的一个说明
public class EmployeeLoginDTO implements Serializable {

    @ApiModelProperty("用户名")//对DTO实体对象的属性的一个描述
    private String username;

    @ApiModelProperty("密码")//对DTO实体对象的属性的一个描述
    private String password;

}
