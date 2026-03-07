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
import org.springframework.stereotype.Service;

/**
 * 短链接接口实现层
 */
@Service
public class ShotLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        ShortLinkDO shortLinkDO = BeanUtil.copyProperties(shortLinkCreateReqDTO, ShortLinkDO.class);
        shortLinkDO.setShortUri(HashUtil.hashToBase62(shortLinkDO.getOriginUrl()));
        shortLinkDO.setFullShortUrl(shortLinkDO.getDomain() + "/" + shortLinkDO.getShortUri());
        shortLinkDO.setClickNum(0);
        shortLinkDO.setEnableStatus(0);
        shortLinkDO.setEnableStatus(0);
        shortLinkDO.setDelFlag(0);

        int insert = baseMapper.insert(shortLinkDO);
        if (insert < 1) {
            throw new ServiceException("新增短链接失败！");
        }

        return ShortLinkCreateRespDTO.builder()
                .gid(shortLinkDO.getGid())
                .originUrl(shortLinkDO.getOriginUrl())
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .build();
    }

}
