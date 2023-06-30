package hello.login;

import hello.login.encoding.EncodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
public class EncdoignTest {

    private final EncodingService encodingService;

    CharSequence originPW = "123456789";

    String afterPw = "";

    @Test
    void encodingPw() {
        PasswordEncoder passwordEncoder = passwordEncoder();
        log.info(passwordEncoder.toString());
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
