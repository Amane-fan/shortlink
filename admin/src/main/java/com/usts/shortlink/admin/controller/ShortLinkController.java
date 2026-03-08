package com.usts.shortlink.admin.controller;

import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCountQueryRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkPageResultRespDTO;
import com.usts.shortlink.admin.service.ShortLinkRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
public class ShortLinkController {

    private final ShortLinkRemoteService shortLinkRemoteService;

    public ShortLinkController(ShortLinkRemoteService shortLinkRemoteService) {
        this.shortLinkRemoteService = shortLinkRemoteService;
    }

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        return shortLinkRemoteService.createShortLink(shortLinkCreateReqDTO);
    }

    /**
     * 短链接分页查询
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<ShortLinkPageResultRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        return shortLinkRemoteService.pageShortLink(shortLinkPageReqDTO);
    }

    /**
     * 统计分组短链接数量
     */
    @GetMapping("/api/short-link/admin/v1/count")
    public Result<List<ShortLinkCountQueryRespDTO>> groupShortLinkCount(@RequestParam("ids") List<String> ids) {
        return shortLinkRemoteService.groupShortLinkCount(ids);
    }
}
