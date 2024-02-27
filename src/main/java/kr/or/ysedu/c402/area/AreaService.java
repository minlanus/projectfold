package kr.or.ysedu.c402.area;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AreaService {
	
	private final AreaRepository areaRepository;
	
	public void create1(String areas) {
		Areas area = new Areas();
		area.setAr(areas);
		this.areaRepository.save(area);
	}
	
	public List<Areas> getArea() {
		return areaRepository.findAll();
	}
	
	
}
