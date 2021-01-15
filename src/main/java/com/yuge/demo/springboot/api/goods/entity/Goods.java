package com.yuge.demo.springboot.api.goods.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Goods implements Serializable {
    private Long id;

    /**
    * 名字
    */
    private String name;

    /**
    * 类型
    */
    private Integer type;

    /**
    * 生产地址
    */
    private String address;
}