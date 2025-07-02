package com.sky.annotation;

/**
 * project(工程)-module(模块)-package(包)-class(类)
 * ClassName: AutoFill
 * Package: com.sky.annotation
 * Description:
 *
 * @Author Lee JiaCheng
 * @Create 2025/6/27 22:35
 * @Version 1.0
 */

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：用于标识某个方法的 公共字段自动填充处理
 *
 * 不同的方法 可能都会对同一个数据库字段进行操作
 * 例如：Employee与Category中 updateTime的更新
 *      那么我们可以用AOP(切面)的技术，用注解的方式
 *      来实现自动公共字段处理
 */
@Target(ElementType.METHOD)//只用于标识某个方法
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //数据库操作类型：UPDATE INSERT
    OperationType value();
}
