package com.usts.shortlink.admin.service.Impl;

import com.usts.shortlink.admin.common.convention.exception.ServiceException;
import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.dto.req.ShortLinkCreateReqDTO;
import com.usts.shortlink.admin.dto.req.ShortLinkPageReqDTO;
import com.usts.shortlink.admin.dto.resp.ShortLinkCreateRespDTO;
import com.usts.shortlink.admin.dto.resp.ShortLinkPageResultRespDTO;
import com.usts.shortlink.admin.service.ShortLinkRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 短链接远程调用实现层
 */
@Service
@Slf4j
public class ShortLinkRemoteServiceImpl implements ShortLinkRemoteService {

    private final RestTemplate restTemplate;

    @Value("${short-link.project.base-url}")
    private String projectBaseUrl;

    public ShortLinkRemoteServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String requestUrl = projectBaseUrl + "/api/short-link/v1/create";
        HttpEntity<ShortLinkCreateReqDTO> requestEntity = new HttpEntity<>(shortLinkCreateReqDTO);
        try {
            ResponseEntity<Result<ShortLinkCreateRespDTO>> responseEntity = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return getResponseBody(responseEntity, "创建短链接");
        } catch (RestClientException ex) {
            log.error("调用 project 模块创建短链接接口失败", ex);
            throw new ServiceException("调用 project 模块创建短链接接口失败");
        }
    }

    @Override
    public Result<ShortLinkPageResultRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        URI requestUri = UriComponentsBuilder.fromHttpUrl(projectBaseUrl)
                .path("/api/short-link/v1/page")
                .queryParam("gid", shortLinkPageReqDTO.getGid())
                .queryParam("current", shortLinkPageReqDTO.getCurrent())
                .queryParam("size", shortLinkPageReqDTO.getSize())
                .build(true)
                .toUri();
        try {
            ResponseEntity<Result<ShortLinkPageResultRespDTO>> responseEntity = restTemplate.exchange(
                    requestUri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return getResponseBody(responseEntity, "分页查询短链接");
        } catch (RestClientException ex) {
            log.error("调用 project 模块分页查询短链接接口失败", ex);
            throw new ServiceException("调用 project 模块分页查询短链接接口失败");
        }
    }

    private <T> Result<T> getResponseBody(ResponseEntity<Result<T>> responseEntity, String action) {
        Result<T> result = responseEntity.getBody();
        if (result == null) {
            throw new ServiceException(action + "响应为空");
        }
        return result;
    }
}
