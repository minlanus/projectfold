package kr.or.ysedu.c402.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEmailForm {
	
	@NotEmpty(message = "새 Email는 필수항목입니다.")
	private String newEmail;
}
