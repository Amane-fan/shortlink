package com.usts.shortlink.admin.service;

import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCountQueryRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkPageResultRespDTO;

import java.util.List;

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

    /**
     * 统计分组短链接数量
     */
    Result<List<ShortLinkCountQueryRespDTO>> groupShortLinkCount(List<String> ids);
}
