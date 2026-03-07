package com.usts.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usts.shortlink.admin.dao.entity.GroupDO;
import com.usts.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.usts.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短链接分组
     * @param groupName 短链接分组名称
     */
    void saveGroup(String groupName);

    /**
     * 查询短链接分组
     * @return 短链接分组
     */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
     * 修改短链接分组名称
     * @param shortLinkGroupUpdateReqDTO 短链接分组修改名称以及gid
     */
    void updateGroup(ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO);
}
