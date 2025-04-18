package com.mfy.memefy.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The {@link RateLimitFilter} class
 *
 * @author Oleh Ivasiuk
 */
@Component
public class RateLimitFilter implements Filter {
    private final Bucket bucket;

    public RateLimitFilter() {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write(
                    "{\"code\": 429, \"message\": \"Too many requests\", \"status\": \"TOO_MANY_REQUESTS\", " +
                            "\"path\": \"" + httpRequest.getRequestURI() + "\", \"time\": \"" + LocalDateTime.now() + "\"}"
            );
        }
    }
}
