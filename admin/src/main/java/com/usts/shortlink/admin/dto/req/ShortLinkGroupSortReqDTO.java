package com.usts.shortlink.admin.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短链接分组排序参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkGroupSortReqDTO {

    /**
     * 短链接分组id
     */
    private String gid;

    /**
     * 短链接分组排序
     */
    private Integer sortOrder;

}
