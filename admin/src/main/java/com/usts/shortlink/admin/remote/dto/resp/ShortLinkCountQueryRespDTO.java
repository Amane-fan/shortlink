package com.usts.shortlink.admin.remote.dto.resp;

import lombok.Data;

/**
 * 分组短链接数量返回参数
 */
@Data
public class ShortLinkCountQueryRespDTO {

    /**
     * 分组id
     */
    private String gid;

    /**
     * 短链接数量
     */
    private Integer shortLinkCount;
}
