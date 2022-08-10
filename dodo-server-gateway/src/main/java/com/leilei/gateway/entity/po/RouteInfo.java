package com.leilei.gateway.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lei
 * @create 2022-04-11 14:02
 * @desc 路由信息
 **/
@Data
@TableName("v7_test_gateway_route")
public class RouteInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String routeId;
    private String uri;
    private String predicates;
    private String filters;
    private Boolean enabled;
    private String description;
    private Integer orderNum;
    private Long createTime;
    private Long modifyTime;
    private String createId;
    private String modifyId;

    @TableLogic
    private Boolean deleteFlag;
}
