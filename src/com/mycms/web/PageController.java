package com.mycms.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycms.domain.Content;
import com.mycms.domain.PageContent;
import com.mycms.domain.SitePage;
import com.mycms.repository.ContentRepository;
import com.mycms.repository.PageContentRepository;
import com.mycms.repository.SitePageRepository;
import com.mycms.web.editors.ContentEditor;

@Controller
public class PageController extends AbstractCmsController {

	@Autowired
	private SitePageRepository sitePageRepository;

	@Autowired
	private PageContentRepository pageContentRepository;

	@Autowired
	private ContentRepository contentRepository;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(Content.class, new ContentEditor(
				contentRepository));
	}

	@RequestMapping("/admin/sitepages/list")
	public ModelAndView list() {

		return new ModelAndView("sitePagesList", "sitePages",
				sitePageRepository.findAll());

	}

	@RequestMapping(value = "/admin/sitepages/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable int id) {

		return new ModelAndView("sitePageEdit", "sitePage",
				sitePageRepository.findOne(id));

	}

	@RequestMapping(value = "/admin/sitepages/edit/{id}", method = RequestMethod.POST)
	public String edit(@PathVariable int id, SitePage sitePage) {
		sitePage.setId(id);
		sitePageRepository.save(sitePage);
		return "redirect:/admin/sitepages/list";

	}

	@RequestMapping(value = "/admin/sitepages/create", method = RequestMethod.GET)
	public ModelAndView create() {

		return new ModelAndView("sitePageForm", "sitePage", new SitePage());

	}

	@RequestMapping(value = "/admin/sitepages/create", method = RequestMethod.POST)
	public String create(SitePage sitePage) {
		sitePageRepository.save(sitePage);
		return "redirect:/admin/sitepages/list";

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/add", method = RequestMethod.GET)
	public ModelAndView addContent(@PathVariable int pageId) {
		SitePage page = sitePageRepository.findOne(pageId);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("contents", contentRepository.findAll());
		modelMap.addAttribute("page", page);
		return new ModelAndView("addPageContent", modelMap);

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/add", method = RequestMethod.POST)
	public String addContent(@PathVariable int pageId, PageContent pageContent) {
		SitePage page = sitePageRepository.findOne(pageId);
		Set<PageContent> contents = page.getContents();
		int maxOrder = contents.size()>0 ? getListOfContents(contents).get(contents.size() - 1).getSortOrder() : 1;
		pageContent.setPage(page);
		pageContent.setSortOrder(maxOrder + 1);
		pageContentRepository.save(pageContent);
		return "redirect:/admin/sitepages/list";

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/{pageContentId}/down", method = RequestMethod.GET)
	public String moveDown(@PathVariable int pageId,
			@PathVariable int pageContentId) {
		SitePage page = sitePageRepository.findOne(pageId);
		PageContent pageContent = pageContentRepository.findOne(pageContentId);
		List<PageContent> listOfContents = getListOfContents(page.getContents());
		int indexOfContent = listOfContents.indexOf(pageContent);

		if (indexOfContent > -1 && indexOfContent < listOfContents.size() - 1) {
			Collections
					.swap(listOfContents, indexOfContent, indexOfContent + 1);
			for (int i = 0; i < listOfContents.size(); i++) {
				listOfContents.get(i).setSortOrder(i + 1);
			}
			pageContentRepository.save(listOfContents);
		}

		return "redirect:/admin/sitepages/list";

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/{pageContentId}/remove", method = RequestMethod.GET)
	public String removeContent(@PathVariable int pageId,
			@PathVariable int pageContentId) {
		pageContentRepository.delete(pageContentId);
		return "redirect:/admin/sitepages/list";

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/{pageContentId}/up", method = RequestMethod.GET)
	public String moveUo(@PathVariable int pageId,
			@PathVariable int pageContentId) {
		SitePage page = sitePageRepository.findOne(pageId);
		PageContent pageContent = pageContentRepository.findOne(pageContentId);
		List<PageContent> listOfContents = getListOfContents(page.getContents());
		int indexOfContent = listOfContents.indexOf(pageContent);

		if (indexOfContent > -1 && indexOfContent > 0) {
			Collections
					.swap(listOfContents, indexOfContent - 1, indexOfContent);
			for (int i = 0; i < listOfContents.size(); i++) {
				listOfContents.get(i).setSortOrder(i + 1);
			}
			pageContentRepository.save(listOfContents);
		}

		return "redirect:/admin/sitepages/list";

	}

	private List<PageContent> getListOfContents(Set<PageContent> contents) {
		return new ArrayList<PageContent>(contents);
	}

}
