package kr.or.ysedu.c402.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import kr.or.ysedu.c402.question.Question;
import java.util.List;
import kr.or.ysedu.c402.user.SiteUser;
import java.util.Set;


public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}
