package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails {
    // UserDetails는 스프링 시큐리티의 인터페이스이다. 사용자의 정보를 담는 인터페이스이다.
    // 스프링 시큐리티에서 사용자 정보를 불러오기 위해 구현해야 하는 인터페이스.
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String username;
    private final String password;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 계정 권한 목록 컬렉션 반환.
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료 여부 반환. true: 만료 안됨. false: 만료됨.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠김 여부 반환. true: 잠기지 않음. false: 잠김
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부 반환. true: 만료 안됨. false: 만료됨.
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화 여부 반환. true: 활성화 됨. false: 비활성화 됨.
        return true;
    }
}
