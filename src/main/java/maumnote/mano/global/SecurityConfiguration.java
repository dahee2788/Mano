package maumnote.mano.global;

import lombok.RequiredArgsConstructor;
import maumnote.mano.global.login.LoginService;
import maumnote.mano.global.login.MemberAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // 설정 관련된 파일이라는 어노테이션
@EnableWebSecurity // 시큐리티를 활성화하는 어노테이션
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final LoginService loginService;


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(loginService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 5.7.0-M2 부터 WebSecurityConfigurerAdapter 사용되지 않음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http , AuthenticationManager authManager) throws Exception {

        // 페이지 권한 - 주소에 대한 권한설정
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/","/member","/login","/notebook").permitAll() // 권한없이 접근 가능 => 접근을 풀어야하는 화면은 이렇게 string으로 줄줄이 써야하는지?
                        .anyRequest().authenticated() // 그 외의 요청은 권한 필요
        ).httpBasic(withDefaults()); // 기본 인증 설정

        // 커스텀 필터 추가
        http.addFilterBefore(new MemberAuthenticationFilter(authManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


}

