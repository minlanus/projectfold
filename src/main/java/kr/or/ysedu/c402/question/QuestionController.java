package kr.or.ysedu.c402.question;

import java.security.Principal;
import java.util.List;

import javax.security.auth.Subject;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import kr.or.ysedu.c402.answer.Answer;
import kr.or.ysedu.c402.answer.AnswerForm;
import kr.or.ysedu.c402.answer.AnswerRepository;
import kr.or.ysedu.c402.answer.AnswerService;
import kr.or.ysedu.c402.user.SiteUser;
import kr.or.ysedu.c402.user.UserService;

import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionRepository questionRepository;
	
	private final AnswerService answerService;
	
	private final QuestionService questionService;
	
	private final UserService userService;
	
	
//	@ResponseBody
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="1") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Question> paging = this.questionService.getList(page - 1, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,
			@RequestParam(value = "answerPage", defaultValue = "1") int answerPage) {
		Question question = this.questionService.getQuestion(id);
		Page<Answer> answerPageing = this.answerService.getList(answerPage - 1, question);
		model.addAttribute("question", question);
		model.addAttribute("answerPageing", answerPageing);
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create")
	public String questionCreate(@Valid QuestionForm questionForm,
			BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
            return "question_form";
        }
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(),
				questionForm.getContent(), siteUser, 0);
		return "redirect:/question/list"; // 질문 저장 후 질문목록으로 이동
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String quetionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
}

