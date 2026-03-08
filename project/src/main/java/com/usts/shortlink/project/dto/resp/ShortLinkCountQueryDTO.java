package com.usts.shortlink.project.dto.resp;

import lombok.Data;

@Data
public class ShortLinkCountQueryDTO {
    /**
     * 分组id
     */
    private String gid;

    /**
     * 短链接数量
     */
    private Integer shortLinkCount;

}
