package com.usts.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usts.shortlink.admin.common.biz.user.UserContext;
import com.usts.shortlink.admin.common.convention.exception.ClientException;
import com.usts.shortlink.admin.common.convention.exception.ServiceException;
import com.usts.shortlink.admin.dao.entity.GroupDO;
import com.usts.shortlink.admin.dao.mapper.GroupMapper;
import com.usts.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.usts.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.usts.shortlink.admin.service.GroupService;
import com.usts.shortlink.admin.util.RandomGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接接口分组实现层
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    @Override
    public void saveGroup(String groupName) {
        String username = UserContext.getUsername();
        if (StrUtil.isBlank(username)) {
            throw new ClientException("当前登录用户不存在");
        }
        // 保证生成的gid都是唯一的
        while (true) {
            String GID = RandomGeneratorUtil.generateRandom();
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, GID)
                    .eq(GroupDO::getUsername, username);
            GroupDO hadGid = baseMapper.selectOne(queryWrapper);
            if (hadGid == null) {
                GroupDO groupDO = GroupDO.builder()
                        .name(groupName)
                        .gid(GID)
                        .username(username)
                        .sortOrder(0)
                        .build();
                baseMapper.insert(groupDO);
                break;
            }

        }
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        String username = UserContext.getUsername();
        if (StrUtil.isBlank(username)) {
            throw new ClientException("当前登录用户不存在");
        }
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, username)
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
    }

    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO) {
        String username = UserContext.getUsername();
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                        .eq(GroupDO::getGid, shortLinkGroupUpdateReqDTO.getGid())
                        .eq(GroupDO::getDelFlag, 0)
                        .eq(GroupDO::getUsername, username);
        GroupDO groupDO = new GroupDO();
        groupDO.setName(shortLinkGroupUpdateReqDTO.getName());
        int update = baseMapper.update(groupDO, updateWrapper);
        if (update < 1) {
            throw new ServiceException("分组名称更新失败!");
        }
    }

    @Override
    public void deleteGroup(String gid) {
        String username = UserContext.getUsername();
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, username);
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        int update = baseMapper.update(groupDO, updateWrapper);
        if (update < 1) {
            throw new ServiceException("分组名称删除失败!");
        }
    }
}
