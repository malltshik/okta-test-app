package com.scoober.okta.web;

import com.scoober.okta.security.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@EnableWebMvc
@Configuration
class WebMvcContextConfig implements WebMvcConfigurer {
  
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HandlerMethodArgumentResolver() {

            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType().equals(User.class);
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                          NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
                return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                        .filter(auth -> auth instanceof AbstractOAuth2TokenAuthenticationToken)
                        .map(auth -> (AbstractOAuth2TokenAuthenticationToken<?>) auth)
                        .map(AbstractOAuth2TokenAuthenticationToken::getTokenAttributes)
                        .map(User::new)
                        .orElse(null);
            }

        });
    }

}