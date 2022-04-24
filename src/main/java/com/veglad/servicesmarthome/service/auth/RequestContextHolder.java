package com.veglad.servicesmarthome.service.auth;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
public class RequestContextHolder {

    private RequestContext requestContext;

    public RequestContext getContext() {
        return this.requestContext;
    }

    public void setContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }
}
