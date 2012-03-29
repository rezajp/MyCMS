package com.mycms.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycms.domain.Menu;
import com.mycms.domain.PageContent;
import com.mycms.domain.SitePage;
import com.mycms.repository.MenuRepository;
import com.mycms.repository.SitePageRepository;
import com.mycms.web.editors.SitePageEditor;

@Controller
public class MenuController extends AbstractCmsController {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private SitePageRepository sitePageRepository;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(SitePage.class, new SitePageEditor(
				sitePageRepository));
	}

	private List<Menu> getSortedMenus() {
		ArrayList<String> sortOrders = new ArrayList<String>();
		sortOrders.add("sortOrder");
		Sort sort = new Sort(Direction.ASC, sortOrders);
		return menuRepository.findAll(sort);
	}

	@RequestMapping("/admin/menus/list")
	public ModelAndView list() {
		return new ModelAndView("menusList", "menus", getSortedMenus());

	}

	@RequestMapping(value = "/admin/menus/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable int id) {

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("menu", menuRepository.findOne(id));
		modelMap.addAttribute("pages", sitePageRepository.findAll());

		return new ModelAndView("menuEdit", modelMap);

	}

	@RequestMapping(value = "/admin/menus/{id}/publish", method = RequestMethod.GET)
	public String publish(@PathVariable int id) {

		Menu menu = menuRepository.findOne(id);
		if (!menu.isPublished()) {
			menu.setPublished(true);
			menuRepository.save(menu);
		}

		return "redirect:/admin/menus/list";

	}

	@RequestMapping(value = "/admin/menus/{id}/remove", method = RequestMethod.GET)
	public String remove(@PathVariable int id) {

		Menu menu = menuRepository.findOne(id);
		if (menu.isPublished()) {
			menu.setPublished(false);
			menuRepository.save(menu);
		}

		return "redirect:/admin/menus/list";

	}

	@RequestMapping(value = "/admin/menus/edit/{id}", method = RequestMethod.POST)
	public String edit(@PathVariable("id") int id,
			@RequestParam("page") int page, @ModelAttribute Menu menu)
			throws Exception {
		SitePage sitePage = sitePageRepository.findOne(page);
		menu.setId(id);

		menu.setPage(sitePage);
		if (!isValid(menu))
			throw new Exception(
					"One and only one of the values 'Page' or 'Page Key' should be set");
		menuRepository.save(menu);
		return "redirect:/admin/menus/list";

	}

	private boolean isValid(Menu menu) {
		return !((menu.getPage() == null && menu.getPageKey().isEmpty()) || (menu
				.getPage() != null && !menu.getPageKey().isEmpty()));
	}

	@RequestMapping(value = "/admin/menus/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("menu", new Menu());
		modelMap.addAttribute("pages", sitePageRepository.findAll());
		return new ModelAndView("menuForm", modelMap);

	}

	@RequestMapping(value = "/admin/menus/create", method = RequestMethod.POST)
	public String create(@RequestParam int page, Menu menu) throws Exception {
		SitePage sitePage = sitePageRepository.findOne(page);
		menu.setPage(sitePage);
		if (!isValid(menu))
			throw new Exception(
					"One and only one of the values 'Page' or 'Page Key' should be set");

		menuRepository.save(menu);
		return "redirect:list";

	}

	@RequestMapping(value = "/admin/menus/{menuId}/down", method = RequestMethod.GET)
	public String moveDown(@PathVariable int menuId) {
		Menu menu = menuRepository.findOne(menuId);

		List<Menu> allMenus = getSortedMenus();

		int index = allMenus.indexOf(menu);

		if (index > -1 && index < allMenus.size() - 1) {
			Collections.swap(allMenus, index, index + 1);
			for (int i = 0; i < allMenus.size(); i++) {
				allMenus.get(i).setSortOrder(i + 1);
			}
			menuRepository.save(allMenus);
		}

		return "redirect:/admin/menus/list";

	}

	@RequestMapping(value = "/admin/menus/{menuId}/up", method = RequestMethod.GET)
	public String moveUo(@PathVariable int menuId) {
		Menu menu = menuRepository.findOne(menuId);

		List<Menu> allMenus = getSortedMenus();
		int index = allMenus.indexOf(menu);

		if (index > -1 && index > 0) {
			Collections.swap(allMenus, index - 1, index);
			for (int i = 0; i < allMenus.size(); i++) {
				allMenus.get(i).setSortOrder(i + 1);
			}
			menuRepository.save(allMenus);
		}

		return "redirect:/admin/menus/list";
	}

}
