package com.imooc.mall.pojo;

import lombok.Data;

import java.sql.Date;

/**
 * Created by 赵国英
 * 2020-3-15
 */
@Data
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Date createTime;

    private Date UpdateTime;

}
