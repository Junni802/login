package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    /**
     * UUID를 활용하여 랜덤번호를 만드는 메서드 생성
     */
    @Test
    void Random() {
        for (int i=0; i<10; i++) {
            String sessionID = UUID.randomUUID().toString();
            System.out.println(i + 1 + "번째 값입니다.");
            System.out.println("sessionID = " + sessionID);
            System.out.println("");
        }
    }

    @Test
    void sessionTest() {

        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expire = sessionManager.getSession(request);
        Assertions.assertThat(expire).isNull();
    }

}