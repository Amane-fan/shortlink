package com.usts.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usts.shortlink.project.dao.entity.ShortLinkDO;
import com.usts.shortlink.project.dao.mapper.ShortLinkMapper;
import com.usts.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkCountQueryDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.project.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

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

    /**
     * 短链接分页查询
     * @return 分页结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);

    /**
     * 统计gid集合中，每个gid的短链接数量
     * @param ids gid集合
     * @return 每个gid的短链接数量
     */
    List<ShortLinkCountQueryDTO> groupShortLinkCount(List<String> ids);
}
