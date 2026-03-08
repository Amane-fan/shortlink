package com.usts.shortlink.admin.dto.resp;

import lombok.Data;

import java.util.List;

/**
 * 短链接分页结果返回参数
 */
@Data
public class ShortLinkPageResultRespDTO {

    /**
     * 数据列表
     */
    private List<ShortLinkPageRespDTO> records;

    /**
     * 总数
     */
    private Long total;

    /**
     * 每页条数
     */
    private Long size;

    /**
     * 当前页
     */
    private Long current;

    /**
     * 总页数
     */
    private Long pages;
}
