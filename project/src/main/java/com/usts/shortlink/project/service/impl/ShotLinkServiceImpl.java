package com.usts.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usts.shortlink.project.common.convention.exception.ServiceException;
import com.usts.shortlink.project.dao.entity.ShortLinkDO;
import com.usts.shortlink.project.dao.mapper.ShortLinkMapper;
import com.usts.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.project.service.ShortLinkService;
import com.usts.shortlink.project.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 短链接接口实现层
 */
@Service
@Slf4j
public class ShotLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Autowired
    private RBloomFilter<String> rBloomFilter;

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        ShortLinkDO shortLinkDO = BeanUtil.copyProperties(shortLinkCreateReqDTO, ShortLinkDO.class);
        shortLinkDO.setShortUri(generateShortUri(shortLinkCreateReqDTO));
        shortLinkDO.setFullShortUrl(shortLinkDO.getDomain() + "/" + shortLinkDO.getShortUri());
        shortLinkDO.setClickNum(0);
        shortLinkDO.setEnableStatus(1);
        shortLinkDO.setEnableStatus(0);
        shortLinkDO.setDelFlag(0);

        try {
            baseMapper.insert(shortLinkDO);
        } catch (DuplicateKeyException ex) {
            log.warn("短链接: {} 重复入库", shortLinkDO.getFullShortUrl());
            throw new ServiceException("短链接重复入库!");
        }

        rBloomFilter.add(shortLinkDO.getFullShortUrl());

        return ShortLinkCreateRespDTO.builder()
                .gid(shortLinkDO.getGid())
                .originUrl(shortLinkDO.getOriginUrl())
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .build();
    }

    private String generateShortUri(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String domain = shortLinkCreateReqDTO.getDomain();
        String originUrl = shortLinkCreateReqDTO.getOriginUrl();
        int generateCount = 1;
        do {
            String shortUri = HashUtil.hashToBase62(originUrl);
            if (!rBloomFilter.contains(domain + "/" + shortUri)) {
                return shortUri;
            }
            log.warn("短链接生成第{}次冲突", generateCount);
            generateCount++;
            if (generateCount > 10) {
                throw new ServiceException("短链接生成频繁，请稍后再试！");
            }
            // 使用UUID重新改变originUrl，避免同一时间海量用户请求，uri重复的问题
            originUrl += UUID.randomUUID();
        } while (true);
    }

}
