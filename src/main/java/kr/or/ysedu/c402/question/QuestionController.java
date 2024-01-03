package kr.or.ysedu.c402.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionRepository questionRepository;
	
//	@ResponseBody
	@GetMapping("/question/list")
	public String list(Model model) {
		List<Question> questionList = this.questionRepository.findAll();
        model.addAttribute("questionList", questionList);
		return "question_list";
	}
}
