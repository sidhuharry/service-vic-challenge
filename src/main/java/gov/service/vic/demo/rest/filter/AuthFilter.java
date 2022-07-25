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

        //Required for h2console
        if (httpRequest.getRequestURL().toString().contains("h2console")) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        String authTokenInRequest = httpRequest.getHeader("Authorization");

        if (!(authTokenInRequest != null && authTokenInRequest.equals(hardcodedAuthToken))) {
            // auth failure
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
