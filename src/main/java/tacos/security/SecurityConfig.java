package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// Deprecated. https://devlog-wjdrbs96.tistory.com/434, https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// 스프링 시큐리티 기본 구성 클래스
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").access("permitAll")
                .and()
                .httpBasic();
        return http.build();
    }

    // 인메모리(내장) 사용자 스토어를 이용함. 그러나 테스트 용으로는 좋아도 사용자 정보 추가/변경 시 애플리케이션 재시작이 필수여서 권장 X
    // 사용자 스토어는 한 명 이상의 사용자를 처리할 수 있도록 사용자 정보를 유지/관리하는 저장소를 말한다.
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("user1")
                .password("{noop}password1") // {noop}은 인코딩 하지 않음(암호 처리 하지 않음)을 의미한다. https://w-giraffe.tistory.com/111?category=936508
                .authorities("ROLE_USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("user2")
                .password("{noop}password2")
                .authorities("ROLE_USER").build());
        return inMemoryUserDetailsManager;
    }
}
