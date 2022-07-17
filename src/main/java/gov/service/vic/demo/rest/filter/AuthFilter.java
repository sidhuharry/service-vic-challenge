package gov.service.vic.demo.rest.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Value("${hardcoded.token.not.to.be.used.in.prod}")
    private String hardcodedAuthToken;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authTokenInRequest = httpRequest.getHeader("Authorization");

        if (!(authTokenInRequest != null && authTokenInRequest.equals("YS10b3RhbGx5LWxlZ2l0LXRva2VuLXRydXN0LW1l"))) {
            // auth failure
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
