package com.veglad.servicesmarthome.controller.interceptor;

import com.veglad.servicesmarthome.service.auth.RequestContext;
import com.veglad.servicesmarthome.service.auth.RequestContextHolder;
import com.veglad.servicesmarthome.service.auth.RequestContextProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RequestContextInterceptor implements AsyncHandlerInterceptor {

    private final RequestContextProvider requestContextProvider;

    private final RequestContextHolder requestContextHolder;

    @Autowired
    public RequestContextInterceptor(RequestContextHolder requestContextHolder,
                                     RequestContextProvider requestContextProvider) {
        this.requestContextHolder = requestContextHolder;
        this.requestContextProvider = requestContextProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Integer personId = this.requestContextProvider.getId();

        this.requestContextHolder.setContext(new RequestContext(personId));

        return true;
    }
}
