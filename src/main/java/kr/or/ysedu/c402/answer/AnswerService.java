package kr.or.ysedu.c402.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import kr.or.ysedu.c402.question.Question;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public void create(Question qiestion, String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(qiestion);
		this.answerRepository.save(answer);
	}
}
