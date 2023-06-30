package hello.login.encoding;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EncodingService {

    String originPw = "";
    String afterPw = "";

    @Autowired
    private PasswordEncoder encoder;

    /**
     * 사용자 기본 암호를 encoding형식을 사용하여 암호화 걸어주는 메서드
     *
     * @param originPw
     * @return
     */
    public String createEncodingPW(CharSequence originPw) {
        return encoder.encode(originPw);
    }

    /**
     * 사용자 기본 비밀번호와 DB에 저장된 암호화된 암호를 매칭 시켜주는 메서드
     *
     * @param originpw
     * @param afterPW
     * @return
     */
    public Boolean findEncodingPW(CharSequence originpw, String afterPW) {
        return encoder.matches(originpw, afterPW);
    }


}
