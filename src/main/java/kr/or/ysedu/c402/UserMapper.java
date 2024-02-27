package kr.or.ysedu.c402;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ysedu.c402.user.SiteUser;

@RequestMapping
public interface UserMapper {
	List<SiteUser> selectAllUser();
}
