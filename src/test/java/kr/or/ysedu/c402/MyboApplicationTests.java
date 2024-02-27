package kr.or.ysedu.c402;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ysedu.c402.answer.Answer;
import kr.or.ysedu.c402.answer.AnswerRepository;
import kr.or.ysedu.c402.question.Question;
import kr.or.ysedu.c402.question.QuestionRepository;
import kr.or.ysedu.c402.question.QuestionService;
import kr.or.ysedu.c402.user.SiteUser;
import kr.or.ysedu.c402.user.UserRepository;




@SpringBootTest
class MyboApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private AnswerRepository answerRepository;
    
    @Autowired
    private UserRepository userRepository;
    
  
    @Test
    void testJpa() {        
    	for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.create(subject, content, null, i);
        }
    }
}
