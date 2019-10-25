package br.com.rmsystems.auditlog.config;

import br.com.rmsystems.auditlog.service.AuditService;
import br.com.rmsystems.auditlog.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter class is the core of Audit Service.
 */
@Component
public class TransactionFilter implements Filter {

    @Autowired
    AuditService auditService;

    @Autowired
    SessionService sessionService;

    /**
     * This method handle all requests to the E-commerce analysing the route, parameters and headers from the request.
     * Furthermore it constructing and queueing the audit messages depending if this route need to be audit.
     * @param servletRequest the serveletRequest
     * @param servletResponse the serveletResponse
     * @param chain the filter chain
     * @throws IOException the IOException exception
     * @throws ServletException the ServletException exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
        boolean isAuthenticated = false;
        try {
            if (shouldSessionBeVerify(requestWrapper)) {
                sessionService.verifySession(requestWrapper);
            }
            isAuthenticated = true;
            chain.doFilter(requestWrapper, responseWrapper);

        } catch (Exception e) {
            isAuthenticated = false;
            throw e;
        } finally {
            /**
             * Audit all requests except those related to AuditResource and H2-Console
             */
            if (isAuthenticated && !StringUtils.isEmpty(requestWrapper.getServletPath()) &&
                    !requestWrapper.getServletPath().contains("/audit/") &&
                    !requestWrapper.getServletPath().contains("h2-console")
            ) {
                auditService.queueAudit(requestWrapper, responseWrapper);
            }

            responseWrapper.copyBodyToResponse();
        }
    }

    private  boolean shouldSessionBeVerify(ContentCachingRequestWrapper requestWrapper) {
        boolean verify=false;

        if(!isRequestErrorType(requestWrapper.getDispatcherType().name()) &&
                !isOpenURL(requestWrapper.getServletPath())) {
            verify=true;
        }
        return verify;
    }

    private boolean isOpenURL(final String url) {
        return url.contains("/register") || url.contains("/login") || url.contains("h2-console");
    }

    private boolean isRequestErrorType(String type) {
        return type.equals("ERROR");
    }
}
