package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;

// 테스트용 임시
public class NoEncodingPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
