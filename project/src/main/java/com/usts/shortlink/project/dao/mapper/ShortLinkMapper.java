package com.usts.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usts.shortlink.project.dao.entity.ShortLinkDO;
import com.usts.shortlink.project.dto.resp.ShortLinkCountQueryDTO;

import java.util.List;

/**
 * 短链接持久层
 */
public interface ShortLinkMapper extends BaseMapper<ShortLinkDO> {

    List<ShortLinkCountQueryDTO> groupShortLinkCount(List<String> ids);
}
