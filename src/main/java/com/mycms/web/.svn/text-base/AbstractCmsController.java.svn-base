package com.mycms.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.mycms.domain.Menu;
import com.mycms.repository.MenuRepository;

public abstract class AbstractCmsController {

	@Autowired
	private MenuRepository menuRepository;

	@ModelAttribute
	public void getMenus(ModelMap model){
		model.addAttribute("sitemenus1",menuRepository.findAll());
	}
}
