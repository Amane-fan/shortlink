package com.usts.shortlink.admin.service;

import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.admin.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.admin.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.admin.dto.resp.ShortLinkPageResultRespDTO;

/**
 * 短链接远程调用接口层
 */
public interface ShortLinkRemoteService {

    /**
     * 创建短链接
     */
    Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);

    /**
     * 分页查询短链接
     */
    Result<ShortLinkPageResultRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);
}
