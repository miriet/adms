package postech.adms.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;

import java.util.List;

public class BasicController {
	public static Integer getCurrentPage(String page) {
		Integer result = null;

		if (page == null) {
			/* Exception 처리 */
			page = "0";
		}

		result = Integer.parseInt(page);
		if (result != 0) result--;

		return result;
	}

	public static void setModelWithkendoList(ModelMap model, Page<?> page) {
		model.addAttribute("rows",    page.getContent());
		model.addAttribute("records", page.getTotalElements());
		model.addAttribute("total",   page.getTotalPages());
		model.addAttribute("page",    page.getNumber());
		model.addAttribute("count",   page.getTotalElements());
	}

	public static void setModelWithkendoList(ModelMap model, Page<?> page, List<?> content) {
		model.addAttribute("rows",    content);
		model.addAttribute("records", page.getTotalElements());
		model.addAttribute("total",   page.getTotalPages());
		model.addAttribute("page",    page.getNumber());
		model.addAttribute("count",   page.getTotalElements());
	}

}
