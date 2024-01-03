package kr.or.ysedu.c402.question;

import java.util.List;
import java.util.Optional;
import kr.or.ysedu.c402.DataNotFoundException;

import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public List<Question> getList(){
		return this.questionRepository.findAll();
	}
	
}
