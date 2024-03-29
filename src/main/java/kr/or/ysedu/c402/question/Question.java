package kr.or.ysedu.c402.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import kr.or.ysedu.c402.answer.Answer;
import kr.or.ysedu.c402.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;
   
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
    
    @ManyToOne
    private SiteUser author;
   
    private LocalDateTime modifyDate;
    
    @ManyToMany
    Set<SiteUser> voter;
    
    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view;
    
 
}
