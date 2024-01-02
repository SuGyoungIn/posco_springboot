    package com.example.posco_missions.controller;

    import com.example.posco_missions.model.dao.UserMapper;
    import com.example.posco_missions.model.dto.MissionDto;
    import com.example.posco_missions.model.dto.UserDto;
    import com.example.posco_missions.service.MissionService;
    import com.example.posco_missions.service.UserService;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.ui.Model;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;


    @Controller
    public class LoginController {

        @Autowired
        private UserMapper userMapper;

        @Autowired
        private UserService userService;

        @Autowired
        private MissionService missionService;


        @GetMapping(value = {"", "/","/home"})
        public String home(Model model, HttpSession session) {
            // 세션에서 사용자 정보 가져오기
            UserDto user = (UserDto) session.getAttribute("user");
            List<MissionDto> missionList = missionService.getAllMission();
            List<UserDto> userList = userService.getAllUser();
            // 사용자 정보가 있으면 홈 화면으로 이동
            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("missionList",missionList);
                model.addAttribute("userList",userList);
                return "home";
            } else {
                // 세션에 사용자 정보가 없으면 로그인 화면으로 리다이렉트
                return "redirect:/login";
            }
        }

        @GetMapping("/login")
        public String loginForm() {
            return "loginForm";
        }

        @PostMapping("/login")
        public String login(@RequestParam String userId, @RequestParam String pw, Model model, HttpServletRequest request) {
            // 간단한 로그인 로직: 사용자명과 비밀번호가 일치하면 로그인 성공

            UserDto user = userService.getOneUser(userId);
            if (user.getId().equals(userId) && user.getPw().equals(pw)) {
                // 세션 생성 또는 기존 세션 가져오기
                HttpSession session = request.getSession(true);
                // 세션에 사용자 정보 추가
                session.setAttribute("user", user);

                return "redirect:/home";
            } else {

                model.addAttribute("error", "Invalid username or password");
                return "loginForm";
            }

        }

        @PostMapping("/logout")
        public String logout(HttpServletRequest request){
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            // 로그아웃 후 로그인 화면으로 리다이렉트
            return "redirect:/login";
        }
    }
