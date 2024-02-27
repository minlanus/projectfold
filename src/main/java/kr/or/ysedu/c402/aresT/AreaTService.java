package kr.or.ysedu.c402.aresT;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ysedu.c402.area.Areas;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AreaTService {
	
	private final AreaTRepository areaTRepository;
	
	public void create1(String content, String st, Areas are) {
		AreaTable areaTable = new AreaTable();
		areaTable.setContent(content);
		areaTable.setSt(st);
		areaTable.setAr(are);
		this.areaTRepository.save(areaTable);
	}
	
}
