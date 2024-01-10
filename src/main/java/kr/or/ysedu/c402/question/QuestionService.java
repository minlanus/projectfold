package kr.or.ysedu.c402.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.or.ysedu.c402.DataNotFoundException;
import kr.or.ysedu.c402.answer.Answer;
import kr.or.ysedu.c402.user.SiteUser;

import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	private Specification<Question> search(String kw) {
		return new Specification<Question>() {
			
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				query.distinct(true);	// 중복 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%" + kw + "%"),		// 내용
						cb.like(u1.get("username"), "%" + kw + "%"),	// 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"),		// 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%"));	// 답변 작성자
			}
		};
	}
	
	public List<Question> getList(){
		return this.questionRepository.findAll();
	}
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if (question.isPresent()) {
			Question question2 = question.get();
			question2.setView(question2.getView() + 1);
			this.questionRepository.save(question2);
			return question2;
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	public Page<Question> getList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.questionRepository.findAllByKeyword(kw, pageable);
	}
	
	public void create(String subject, String content, SiteUser user, int updateView) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		q.setView(updateView);
		this.questionRepository.save(q);
	}
	
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
}
