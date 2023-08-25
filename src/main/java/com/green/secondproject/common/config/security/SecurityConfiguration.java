package com.green.secondproject.common.config.security;

import com.green.secondproject.common.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


//spring security 5.7.0부터 WebSecurityConfigurerAdapter deprecated 됨
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService service;

    //webSecurityCustomizer 를 제외한 모든 것, 시큐리티를 거친다. 보안과 연관
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
        httpSecurity.authorizeHttpRequests(authz ->
                    authz.requestMatchers(
                                    mvc.pattern("/swagger.html"), mvc.pattern("/swagger-ui/**"), mvc.pattern("/v3/api-docs/**"),
                                    mvc.pattern("/index.html"), mvc.pattern("/"), mvc.pattern("/static/**"),

                                    mvc.pattern("/api/sign-in"), mvc.pattern("/api/sign-up"),
                                    mvc.pattern("/api/admin/sign-in"), mvc.pattern("/api/admin/sign-up"),
                                    mvc.pattern("/api/mail-confirm"), mvc.pattern("/api/code-confirm"),
                                    mvc.pattern("**exception**")
                            ).permitAll()
                            .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/refresh-token")).permitAll()
                            .requestMatchers(mvc.pattern("/api/mypage/**")).hasAnyRole("TC", "STD")
                            .requestMatchers(mvc.pattern("/api/header/**")).hasAnyRole("TC", "STD")
                            .requestMatchers(mvc.pattern("/api/timetable")).hasAnyRole("TC", "STD")
                            .requestMatchers(mvc.pattern("/api/subject/**")).hasAnyRole("TC", "STD")
                            .requestMatchers(mvc.pattern("/api/logout")).hasAnyRole("TC", "STD")
                            .requestMatchers(mvc.pattern("/api/side")).hasAnyRole("TC", "STD", "ADMIN")
                            .requestMatchers(mvc.pattern("/api/teacher/**")).hasRole("TC")
                            .requestMatchers(mvc.pattern("/api/schedule")).hasAnyRole("TC", "ADMIN")
                            .requestMatchers(mvc.pattern("/api/student/**")).hasRole("STD")
                            .requestMatchers(mvc.pattern("/api/meal/**")).hasRole("STD")
                            .requestMatchers(mvc.pattern("/api/admin/**")).hasRole("ADMIN")
                            .anyRequest().permitAll()
                ) //사용 권한 체크
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 사용 X
        .httpBasic(http -> http.disable()) //UI 있는 시큐리티 설정을 비활성화
                .csrf(csrf -> csrf.disable()) //CSRF 보안이 필요 X, 쿠키와 세션을 이용해서 인증을 하고 있기 때문에 발생하는 일, https://kchanguk.tistory.com/197
                .exceptionHandling(except -> {
                    except.accessDeniedHandler(new CustomAccessDeniedHandler());
                    except.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, service), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    //시큐리티를 거치지 않는다. 보안과 전혀 상관없는 페이지 및 리소스

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        //함수형 인터페이스 람다
//        WebSecurityCustomizer lamda = (web) -> web.ignoring()
//                    .requestMatchers(HttpMethod.GET, "/sign-api/refresh-token");
//        return lamda;
//    }
}
