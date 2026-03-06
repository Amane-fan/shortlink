package com.usts.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usts.shortlink.admin.dao.entity.GroupDO;
import com.usts.shortlink.admin.dao.mapper.GroupMapper;
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
        // 保证生成的gid都是唯一的
        while (true) {

            String GID = RandomGeneratorUtil.generateRandom();
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, GID)
                    // TODO 设置用户名
                    .eq(GroupDO::getUsername, null);
            GroupDO hadGid = baseMapper.selectOne(queryWrapper);
            if (hadGid == null) {
                GroupDO groupDO = GroupDO.builder()
                        .name(groupName)
                        .gid(GID)
                        .sortOrder(0)
                        .build();
                baseMapper.insert(groupDO);
                break;
            }

        }
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        // TODO 获取用户名
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
//                .eq(GroupDO::getUsername, null)
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
    }
}
