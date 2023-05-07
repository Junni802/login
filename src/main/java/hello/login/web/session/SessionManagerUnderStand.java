package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SessionManagerUnderStand {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {
        // 세션  Id를 생성하기 위한 UUID를 활용하여 세션 Id생성
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    /**
     * 세션 조회
     */
    public Object getSessionId(HttpServletRequest request) {
        Cookie cookie = getCookie(request, SESSION_COOKIE_NAME);
        if(cookie == null) {
            return null;
        }

        return sessionStore.get(cookie.getValue());
    }

    public void expire(HttpServletRequest request) {
        Cookie cookie = getCookie(request, SESSION_COOKIE_NAME);
        if(cookie != null) {
            sessionStore.remove(cookie.getValue());
        }
    }

    public Cookie getCookie(HttpServletRequest request, String sessionId) {
        Cookie[] cookies = request.getCookies();
        if(cookies ==  null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .findAny()
                .orElse(null);
    }

    /**
     * 세션 제거
     */

}
