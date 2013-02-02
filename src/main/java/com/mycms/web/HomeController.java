package com.mycms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycms.domain.SitePage;
import com.mycms.repository.PageContentRepository;
import com.mycms.repository.SitePageRepository;
import com.mycms.web.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping("/")
public class HomeController extends AbstractCmsController {

	@Autowired
	private SitePageRepository sitePageRepository;
	@Autowired
	private PageContentRepository pageContentRepository;

	@RequestMapping("/")
	public ModelAndView index() {

		return new ModelAndView("homePage");

	}

	private String GetPath() {
		String filePath = HomeController.class.getProtectionDomain()
				.getCodeSource().getLocation().toString();
		String[] elements = filePath.split("/");
		String filePathOut = "";
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].equalsIgnoreCase("file:"))
				continue;
			if (elements[i].equalsIgnoreCase("web-inf"))
				break;
			filePathOut += elements[i] + "\\";
		}
		return filePathOut;
	}

	@RequestMapping("/{pageKey}")
	public ModelAndView loadPage(@PathVariable String pageKey) {

		SitePage page = sitePageRepository.readByValidKey(pageKey);
		//page.setPageContentRepository(pageContentRepository);
		if (page == null)
			throw new ResourceNotFoundException();
		ModelMap map = new ModelMap();
		map.addAttribute("pageKey", pageKey);
		map.addAttribute("page", page);

		return new ModelAndView("home", map);

	}

	@RequestMapping("/admin")
	public ModelAndView adminPage() {
		return new ModelAndView("admin");
	}
}
