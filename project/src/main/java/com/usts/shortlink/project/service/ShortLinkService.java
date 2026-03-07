package com.usts.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usts.shortlink.project.dao.entity.ShortLinkDO;
import com.usts.shortlink.project.dao.mapper.ShortLinkMapper;
import com.usts.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkCreateRespDTO;

/**
 * 短链接接口层
 */
public interface ShortLinkService {

    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO 短链接创建参数
     * @return 创建短链接返回信息
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);
}
