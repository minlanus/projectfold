package kr.or.ysedu.c402.area;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {
	
	private final AreaService areaService;
	
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Areas> area = this.areaService.getArea();
		model.addAttribute("area", area);
		return "area_home";
	}
	
}
