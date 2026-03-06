package com.usts.shortlink.admin.controller;

import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.common.convention.result.Results;
import com.usts.shortlink.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.usts.shortlink.admin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接分组控制层
 */
@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    /**
     * 新增短链接分组
     * @param shortLinkGroupSaveReqDTO 短链接名称
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO shortLinkGroupSaveReqDTO) {
        groupService.saveGroup(shortLinkGroupSaveReqDTO.getName());
        return Results.success();
    }

}
