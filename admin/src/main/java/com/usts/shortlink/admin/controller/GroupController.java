package com.usts.shortlink.admin.controller;

import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.common.convention.result.Results;
import com.usts.shortlink.admin.remote.dto.req.ShortLinkGroupSaveReqDTO;
import com.usts.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.usts.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.usts.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.usts.shortlink.admin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/api/short-link/admin/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO shortLinkGroupSaveReqDTO) {
        groupService.saveGroup(shortLinkGroupSaveReqDTO.getName());
        return Results.success();
    }

    /**
     * 查询短链接分组
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        List<ShortLinkGroupRespDTO> result = groupService.listGroup();
        return Results.success(result);
    }

    /**
     * 修改短链接分组名称
     * @param shortLinkGroupUpdateReqDTO
     * @return
     */
    @PutMapping("/api/short-link/admin/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO) {
        groupService.updateGroup(shortLinkGroupUpdateReqDTO);
        return Results.success();
    }

    /**
     * 删除短链接分组
     * @param gid 分组id
     */
    @DeleteMapping("/api/short-link/admin/v1/group")
    public Result<Void> deleteGroup(@RequestParam("gid") String gid) {
        groupService.deleteGroup(gid);
        return Results.success();
    }

    /**
     * 分组排序
     * @param list 分组集合
     */
    @PostMapping("/api/short-link/admin/v1/group/sort")
    public Result<Void> orderGroup(@RequestBody List<ShortLinkGroupSortReqDTO> list) {
        groupService.orderGroup(list);
        return Results.success();
    }

}
