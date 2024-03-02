package com.codecrafter.typhoon.config.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 간단하게, 요청들어온 IP, URL 남기는 필터
 */
@Slf4j
public class RequestLoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		if (request instanceof HttpServletRequest httpServletRequest) {
			StringBuilder fullUrl = new StringBuilder(httpServletRequest.getRequestURL());

			if (httpServletRequest.getQueryString() != null) {
				fullUrl.append('?').append(httpServletRequest.getQueryString());
			}
			String ipAddress = request.getRemoteAddr();
			log.info("\bRequest from IP: {} to URL: {}", ipAddress, fullUrl.toString());
		}

		chain.doFilter(request, response);

		int status = ((HttpServletResponse)response).getStatus();
		log.info("STATUS = {}\n", status);
	}

}
