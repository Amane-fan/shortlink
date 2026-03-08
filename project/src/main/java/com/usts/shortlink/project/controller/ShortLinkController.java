package com.usts.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.usts.shortlink.project.common.convention.result.Result;
import com.usts.shortlink.project.common.convention.result.Results;
import com.usts.shortlink.project.dao.mapper.ShortLinkMapper;
import com.usts.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkCountQueryDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.usts.shortlink.project.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        ShortLinkCreateRespDTO result = shortLinkService.createShortLink(shortLinkCreateReqDTO);
        return Results.success(result);
    }

    /**
     * 短链接分页查询
     * @param shortLinkPageReqDTO 分页请求参数
     * @return 分页响应参数
     */
    @GetMapping("/api/short-link/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        IPage<ShortLinkPageRespDTO> result = shortLinkService.pageShortLink(shortLinkPageReqDTO);
        return Results.success(result);
    }

    /**
     * 统计gid集合中，每个gid的短链接数量
     * @param ids gid集合
     * @return 每个gid的短链接数量
     */
    @GetMapping("/api/short-link/v1/count")
    public Result<List<ShortLinkCountQueryDTO>> groupShortLinkCount(@RequestParam("ids") List<String> ids) {
        List<ShortLinkCountQueryDTO> result = shortLinkService.groupShortLinkCount(ids);
        return Results.success(result);
    }
}
