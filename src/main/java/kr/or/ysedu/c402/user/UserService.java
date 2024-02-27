package kr.or.ysedu.c402.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import groovyjarjarantlr4.v4.codegen.model.chunk.ThisRulePropertyRef_start;
import jakarta.validation.constraints.Email;
import kr.or.ysedu.c402.DataNotFoundException;
import kr.or.ysedu.c402.question.Question;
import kr.or.ysedu.c402.question.QuestionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService{
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final QuestionRepository questionRepository;
	
	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		if(siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
	
	public void upDatePassword(SiteUser user, String newPw1) {
		user.setPassword(passwordEncoder.encode(newPw1));
		userRepository.save(user);
	}
	public void upDateEmail(SiteUser user, String newEmail) {
		user.setEmail(newEmail);
		userRepository.save(user);
	}
	
}	
