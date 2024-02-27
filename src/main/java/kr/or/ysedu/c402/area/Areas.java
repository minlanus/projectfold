package kr.or.ysedu.c402.area;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Areas {
	
	private Long id;
	
	@Id
	@Column(length = 10)
	private String ar;
	
}
