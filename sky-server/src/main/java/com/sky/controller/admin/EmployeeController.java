package com.sky.controller.admin;

import com.google.j2objc.annotations.WeakOuter;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")//tags标签： 描述EmployeeController这个类的作用
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")//value标签        //json格式参数,使用@RequestBody
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping("")//Post类型请求
    @ApiOperation(value = "新增员工")
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO){//json格式参数,使用@RequestBody
        log.info("新增员工：{}",employeeDTO);//输出日志
        System.out.println("当前线程的id"+Thread.currentThread().getId());//获取当前线程的id
        employeeService.save(employeeDTO);//调用service层
        return Result.success();//Result():向前端返回结果
    }

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")//Get类型请求
    @ApiOperation(value = "员工分页查询")
    public Result<PageResult> page( EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询,参数为：{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);//调用service层的 pageQuery分页查询方法
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用禁用员工账号")
    //路径(path)参数 {status} :必用 @PathVariable ; json/query参数:不用 @PathVariable
    public Result startOrStop(@PathVariable Integer status,Long id/* status:路径参数 */){
        log.info("启用禁用员工账号:{},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")//path路径参数 @PathVariable
    @ApiOperation(value = "根据id查询员工信息")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping()
    @ApiOperation(value = "编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){//json格式参数,使用@RequestBody
        log.info("编辑员工信息:{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
