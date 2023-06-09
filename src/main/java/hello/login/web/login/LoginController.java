package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member login = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(login == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");

            return "login/loginForm";
        }


        // 로그인 성공 처리

        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(login.getId()));
        response.addCookie(idCookie);

        // 로그인 성공 처리 TODO
        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member login = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(login == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");

            return "login/loginForm";
        }


        // 로그인 성공 처리

        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
        sessionManager.createSession(login, response);


        // 로그인 성공 처리 TODO
        return "redirect:/";
    }


//    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member login = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(login == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");

            return "login/loginForm";
        }


        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, login);

        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
//        sessionManager.createSession(login, response);


        // 로그인 성공 처리 TODO
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member login = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(login == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");

            return "login/loginForm";
        }


        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, login);

        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
//        sessionManager.createSession(login, response);


        // 로그인 성공 처리 TODO
        return "redirect:" + redirectURL;
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response) {
//        expireCookie(response, "memberId");
//        return "redirect:/";
//    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

//        sessionManager.expire(request);
        return "redirect:/";
    }

    public void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie("memberId", null);
        // cookie -> MaxAge가 0일 경우 자동으로 웹 브라우저에서 쿠키가 없는것으로 판단하고 쿠키가 없어진다.
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
