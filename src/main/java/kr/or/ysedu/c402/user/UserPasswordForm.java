package kr.or.ysedu.c402.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordForm {
	
	@NotEmpty(message = "사용자의 현재 비밀버호 입력")
	private String password;
	
	@NotEmpty(message = "새 비밀번호는 필수항목입니다.")
	private String newPw1;
	
	@NotEmpty(message = "새 비밀번호는 필수항목입니다.")
	private String newPw2;
}
