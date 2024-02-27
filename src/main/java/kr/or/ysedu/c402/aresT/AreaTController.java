package kr.or.ysedu.c402.aresT;

import java.util.List;

import org.h2.util.json.JSONItemType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import kr.or.ysedu.c402.area.AreaService;
import kr.or.ysedu.c402.area.Areas;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/areaT")
@Controller
public class AreaTController {
	
	private final AreaTService areaTService;
	
	private final AreaService areaService;
	

	@GetMapping("/areaup")
	public String areaup(Model model, AreaTable areaTable) {
		List<Areas> area = this.areaService.getArea();
		model.addAttribute("area", area);
		return "area_home";
	}
	
	@PostMapping("/areaup")
	public String areaup(@Valid AreaTable areaTable, BindingResult bindingResult,AreaForm areaForm ,@RequestParam(value="name", defaultValue="") String st) {
		if(st.equals("남자")) {
			this.areaTService.create1(areaTable.getContent(), areaForm.getSt_m(), areaTable.getAr());
		}else if(st.equals("여자")) {
			this.areaTService.create1(areaTable.getContent(), areaForm.getSt_s(), areaTable.getAr());
		}
		return "redirect:/areaT/areaup";
	}
}
