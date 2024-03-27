package com.rest.api.store.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;


/**
 * The @EnableRedisIndexedHttpSession annotation creates a Spring Bean with the name of springSessionRepositoryFilter that
 * implements Filter. The filter is in charge of replacing the HttpSession implementation to be backed by Spring Session.
 * In this instance, Spring Session is backed by RedisIndexedSessionRepository.
 * For more info
 * <a href="https://github.com/spring-projects/spring-session/issues/2235">...</a>
 * <a href="https://docs.spring.io/spring-session/reference/http-session.html#httpsession">...</a>
 */
@Configuration
@EnableRedisIndexedHttpSession
@Slf4j
@RequiredArgsConstructor
public class RedisConfig extends AbstractHttpSessionApplicationInitializer {

    private final RedisProperties redisProperties;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration(this.redisProperties.getHost(), this.redisProperties.getPort());
        configuration.setPassword(redisProperties.getPassword());

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
