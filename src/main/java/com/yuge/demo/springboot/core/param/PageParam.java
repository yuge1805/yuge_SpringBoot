package com.yuge.demo.springboot.core.param;

import lombok.Data;

/**
 * @author: yuge
 * @date: 2021-01-18
 **/
@Data
public class PageParam {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

}
