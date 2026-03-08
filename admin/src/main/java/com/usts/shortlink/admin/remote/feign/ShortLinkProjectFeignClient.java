package com.usts.shortlink.admin.remote.feign;

import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCountQueryRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkPageResultRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * project 模块短链接 Feign 客户端
 */
@FeignClient(name = "shortlink-project", url = "${short-link.project.base-url}")
public interface ShortLinkProjectFeignClient {

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/v1/create")
    Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO);

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/short-link/v1/page")
    Result<ShortLinkPageResultRespDTO> pageShortLink(@SpringQueryMap ShortLinkPageReqDTO shortLinkPageReqDTO);

    /**
     * 统计分组短链接数量
     */
    @GetMapping("/api/short-link/v1/count")
    Result<List<ShortLinkCountQueryRespDTO>> groupShortLinkCount(@RequestParam("ids") List<String> ids);
}
