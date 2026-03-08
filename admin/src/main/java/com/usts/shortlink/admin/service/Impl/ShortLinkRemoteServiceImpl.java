package com.usts.shortlink.admin.service.Impl;

import com.usts.shortlink.admin.common.convention.errorcode.BaseErrorCode;
import com.usts.shortlink.admin.common.convention.exception.RemoteException;
import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCountQueryRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.admin.remote.dto.resp.ShortLinkPageResultRespDTO;
import com.usts.shortlink.admin.remote.feign.ShortLinkProjectFeignClient;
import com.usts.shortlink.admin.service.ShortLinkRemoteService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接远程调用实现层
 */
@Service
@Slf4j
public class ShortLinkRemoteServiceImpl implements ShortLinkRemoteService {

    private final ShortLinkProjectFeignClient shortLinkProjectFeignClient;

    public ShortLinkRemoteServiceImpl(ShortLinkProjectFeignClient shortLinkProjectFeignClient) {
        this.shortLinkProjectFeignClient = shortLinkProjectFeignClient;
    }

    @Override
    public Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        try {
            Result<ShortLinkCreateRespDTO> result = shortLinkProjectFeignClient.createShortLink(shortLinkCreateReqDTO);
            return getResponseBody(result, "创建短链接");
        } catch (FeignException ex) {
            log.error("调用 project 模块创建短链接接口失败", ex);
            throw new RemoteException("调用 project 模块创建短链接接口失败", ex, BaseErrorCode.REMOTE_ERROR);
        }
    }

    @Override
    public Result<ShortLinkPageResultRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        try {
            Result<ShortLinkPageResultRespDTO> result = shortLinkProjectFeignClient.pageShortLink(shortLinkPageReqDTO);
            return getResponseBody(result, "分页查询短链接");
        } catch (FeignException ex) {
            log.error("调用 project 模块分页查询短链接接口失败", ex);
            throw new RemoteException("调用 project 模块分页查询短链接接口失败", ex, BaseErrorCode.REMOTE_ERROR);
        }
    }

    @Override
    public Result<List<ShortLinkCountQueryRespDTO>> groupShortLinkCount(List<String> ids) {
        try {
            Result<List<ShortLinkCountQueryRespDTO>> result = shortLinkProjectFeignClient.groupShortLinkCount(ids);
            return getResponseBody(result, "统计分组短链接数量");
        } catch (FeignException ex) {
            log.error("调用 project 模块统计分组短链接数量接口失败", ex);
            throw new RemoteException("调用 project 模块统计分组短链接数量接口失败", ex, BaseErrorCode.REMOTE_ERROR);
        }
    }

    private <T> Result<T> getResponseBody(Result<T> result, String action) {
        if (result == null) {
            throw new RemoteException(action + "响应为空");
        }
        return result;
    }
}
