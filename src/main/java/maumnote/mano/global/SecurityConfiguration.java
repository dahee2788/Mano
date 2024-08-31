package maumnote.mano.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정 관련된 파일이라는 어노테이션
@EnableWebSecurity // 시큐리티를 활성화하는 어노테이션
public class SecurityConfiguration {


    // fail핸들러를 bean으로 만들어서 주입
    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    // 시큐리티 5.7.0-M2 부터 WebSecurityConfigurerAdapter 사용되지 않음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 페이지 권한 - 주소에 대한 권한설정
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/", "/notebook").permitAll() // 권한없이 접근 가능
                        .anyRequest().authenticated() // 그 외의 요청은 권한 필요
        );

        // 로그인 페이지 설정 ,실패처리
        http.formLogin(formLogin ->
                formLogin.loginPage("/login")
                        .failureHandler(getFailureHandler()) // 실패했을 때 처리
                        .permitAll());

        return http.build();

    }
}

