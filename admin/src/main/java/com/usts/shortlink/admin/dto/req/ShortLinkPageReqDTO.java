package com.usts.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 短链接分页请求参数
 */
@Data
public class ShortLinkPageReqDTO {

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页条数
     */
    private Long size = 10L;

    /**
     * 分组id
     */
    private String gid;
}
