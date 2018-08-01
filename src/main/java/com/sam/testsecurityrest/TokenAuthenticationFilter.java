package com.sam.testsecurityrest;

import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.apache.catalina.filters.CorsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.removeStart;

@FieldDefaults(level = PRIVATE, makeFinal = true)

final class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {



    private static final String BEARER = "Bearer";
    private Logger log = LoggerFactory.getLogger(getClass());

    TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


    /*    if (CorsUtils.isPreFlightRequest(request)) {
            log.info("here i am");
            response.setStatus(HttpServletResponse.SC_OK);
        }

        if(CorsUtils.isCorsRequest(request)) {
            log.info("here i am");
            response.setStatus(HttpServletResponse.SC_OK);

        }*/

        final String param = request.getHeader(AUTHORIZATION);

        log.info(param);

        final String token = ofNullable(param)
                .map(value -> removeStart(value, BEARER))
                .map(String::trim)
                .orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));


        Authentication auth = new UsernamePasswordAuthenticationToken(token, token);// username + tokenString
        Authentication aux = getAuthenticationManager().authenticate(auth); /////// chiama Authenticatioqweasdasdsadn provider
    /*    String str = (String) aux.getDetails();
        log.info("asd"  + str);*/
        return aux;
    }


   @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        //response.setStatus(HttpServletResponse.SC_FORBIDDEN);//qst funziona
        chain.doFilter(request, response);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("qua qua qua qua qua qua qua qua qua");

        log.info(failed.getMessage());
        //response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        super.unsuccessfulAuthentication(request, response, failed);
    }



}
