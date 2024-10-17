package maumnote.mano.global.security;

import lombok.RequiredArgsConstructor;
import maumnote.mano.global.util.PermitAllUrlLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

import static maumnote.mano.global.Constants.FIRST_INDEX;

@Configuration // 설정 관련된 파일이라는 어노테이션
@EnableWebSecurity // 시큐리티를 활성화하는 어노테이션
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final MemberAuthenticationProvider memberAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public AuthenticationManager authManager() throws Exception {
        return new ProviderManager(Collections.singletonList(memberAuthenticationProvider));
    }

    @Bean
    public MemberAuthenticationFilter memberAuthenticationFilter(AuthenticationManager authenticationManager) {

        MemberAuthenticationFilter filter = new MemberAuthenticationFilter(authenticationManager);
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(loginFailureHandler);
        return filter;
    }

    // 시큐리티 5.7.0-M2 부터 WebSecurityConfigurerAdapter 사용되지 않음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        String[] permitAllUrls = PermitAllUrlLoader.getPermitAllUrls().toArray(new String[FIRST_INDEX]);

        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize -> authorize.requestMatchers(permitAllUrls).permitAll() // 권한없이 접근 가능 => 접근을 풀어야하는 화면은 이렇게 string으로 줄줄이 써야하는지?
                        .anyRequest().authenticated()) // 그 외의 요청은 권한 필요
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // jwt토큰방식으로 진행할거라 세션에 상태정보를 저장하지 않는 stateless로
//                .httpBasic(withDefaults());
//                .httpBasic().disable(); // 기본 인증 비활성화

        http.addFilterBefore(memberAuthenticationFilter(authManager), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}



