package kr.or.ysedu.c402.aresT;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import kr.or.ysedu.c402.area.Areas;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AreaTable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 200)
	private String content;
	
	@Column(length = 10)
	private String st;
	
	@ManyToOne
	private Areas ar;
	
}
