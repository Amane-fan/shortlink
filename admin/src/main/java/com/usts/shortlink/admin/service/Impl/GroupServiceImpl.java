package com.usts.shortlink.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usts.shortlink.admin.dao.entity.GroupDO;
import com.usts.shortlink.admin.dao.mapper.GroupMapper;
import com.usts.shortlink.admin.service.GroupService;
import com.usts.shortlink.admin.util.RandomGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                        .build();
                baseMapper.insert(groupDO);
                break;
            }

        }

    }
}
