package kr.or.ysedu.c402.user;


import java.security.Principal;
import java.util.List;

import javax.print.attribute.HashAttributeSet;
import javax.security.auth.Subject;

import org.aspectj.weaver.Member;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import kr.or.ysedu.c402.question.Question;
import kr.or.ysedu.c402.question.QuestionRepository;
import kr.or.ysedu.c402.question.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	private final UserRepository userRepository;
	
	private final QuestionService questionService;
	
	private final QuestionRepository questionRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(),  userCreateForm.getPassword1());
		}catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		}catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		
		return "redirect:/";	
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/data")
	public String userdata(Principal principal, Model model) {
		String loginUser = principal.getName();
		SiteUser user = userService.getUser(loginUser);
		model.addAttribute("user", user);
		model.addAttribute("questionList", questionService.getUserQuestion(loginUser, 5));
		return "user_data";
	}
	
	@GetMapping("/pwModify")
	public String passwordModify(UserPasswordForm userPasswordFrom) {
		return "user_from";
	}
	
	@PostMapping("/pwModify")
	public String passwordModify(@Valid UserPasswordForm userPasswordForm, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()){
			return "user_from";
		}
		SiteUser user = userService.getUser(principal.getName());
		if(!passwordEncoder.matches(userPasswordForm.getPassword(), user.getPassword())) {
			bindingResult.reject("notPassword", "현재 비밀번호가 일치하지 않습니다.");
			return "user_from";
		}
		if(!userPasswordForm.getNewPw1().equals(userPasswordForm.getNewPw2())) {
			bindingResult.rejectValue("newPw2", "passwordInCorrect", "새 패스워드가 일치하지 않습니다.");
			return "user_from";
		}
		
		userService.upDatePassword(user, userPasswordForm.getNewPw1());
		
		redirectAttributes.addFlashAttribute("successMessage", "비밀번호 변경 완료");
		
		return "redirect:/user/data";
	}
	
	@GetMapping("/emailModify")
	public String emailModify(UserEmailForm userEmailForm) {
		return "email_from";
	}
	
	@PostMapping("/emailModify")
	public String emailModify(@Valid UserEmailForm userEmailForm, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			return "email_from";
		}
		
		SiteUser user = userService.getUser(principal.getName());
		
		if(user.getEmail().equals(userEmailForm.getNewEmail())) {
			bindingResult.reject("notEmail", "현재 이메일과 일치합니다.");
			return "email_from";
		}
		
		userService.upDateEmail(user, userEmailForm.getNewEmail());
		
		redirectAttributes.addFlashAttribute("successMessage", "이메일 변경 완료");
		
		return "redirect:/user/data";	
	}
}
