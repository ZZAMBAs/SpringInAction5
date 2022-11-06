package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// Deprecated. https://devlog-wjdrbs96.tistory.com/434, https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

// 스프링 시큐리티 기본 구성 클래스
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // ExpressionInterceptUrlRegistry 객체 반환. 이 객체를 사용해 URL 경로와 패턴 및 해당 경로 보안 요구사항 구성 가능.
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").permitAll() // /design, /orders 요청은 ROLE_USER 권한을 갖는 사용자에게만 허용하고 이외 모든 요청은 모든 사용자에게 허용.
                // https://velog.io/@ha0kim/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9D%B8-%EC%95%A1%EC%85%98-4.-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0 의 4.3.1 참조
                .and() // 앞 인증 구성 코드와 연결시킨다는 의미의 메서드
                .formLogin() // 커스텀 로그인 폼을 위해 호출.
                .loginPage("/login") // 커스텀 로그인 페이지 경로 지정.
        // 일반적으로 스프링 시큐리티는 로그인하면 해당 사용자의 로그인이 필요하다 판단한 당시에 사용자가 머물던 페이지로 바로 이동한다.
        // 그러나 사용자가 직접 로그인 페이지로 이동했을 경우, 로그인한 후에는 루트 경로로 이동한다.
        // 하지만 로그인 한 후 이동할 페이지를 .defaultSuccessUrl() 메서드로 바꿀 수 있다. 이 메서드의 두번째 인자로 true를 전달하면 로그인 후 무조건 첫 파라미터 페이지로 이동한다.
                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 페이지
                .and()
                .csrf(); // CSRF 공격 방지
        return http.build();
    }

    // 기본 암호화 방식은 BCrypt 이므로, NoEncodingPasswordEncoder로 임시로 바꾼다.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
