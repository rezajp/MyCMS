package com.mycms.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

		List<SitePage> sitePages =sitePageRepository.findAll();
//		for (SitePage sitePage : sitePages) {
//			sitePage.setPageContentRepository(pageContentRepository);
//		}
		return new ModelAndView("sitePagesList", "sitePages",sitePages
				);

	}

	@RequestMapping(value = "/admin/sitepages/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {

		return new ModelAndView("sitePageEdit", "sitePage",
				sitePageRepository.findOne(id));

	}

	@RequestMapping(value = "/admin/sitepages/edit/{id}", method = RequestMethod.POST)
	public ModelAndView edit(@PathVariable String id, SitePage sitePage) {
		sitePage.setId(id);
		sitePageRepository.save(sitePage);
		return new ModelAndView(new RedirectView("/admin/sitepages/list"));

	}

	@RequestMapping(value = "/admin/sitepages/create", method = RequestMethod.GET)
	public ModelAndView create() {
		SitePage page = new SitePage();
		return new ModelAndView("sitePageForm", "sitePage", page);

	}

	@RequestMapping(value = "/admin/sitepages/create", method = RequestMethod.POST)
	public ModelAndView create(SitePage sitePage) {
		sitePageRepository.save(sitePage);
		return new ModelAndView(new RedirectView("/admin/sitepages/list"));

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/add", method = RequestMethod.GET)
	public ModelAndView addContent(@PathVariable String pageId) {
		SitePage page = sitePageRepository.findOne(pageId);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("contents", contentRepository.findAll());
		modelMap.addAttribute("page", page);
		return new ModelAndView("addPageContent", modelMap);

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/add", method = RequestMethod.POST)
	public ModelAndView addContent(@PathVariable String pageId, @RequestParam("content") String content,
			@RequestParam("cssClass") String cssClass) {
		
		SitePage page = sitePageRepository.findOne(pageId);
		//page.setPageContentRepository(pageContentRepository);
		Set<PageContent> contents = page.getPageContents();
		int maxOrder = contents.size()>0 ? getListOfContents(contents).get(contents.size() - 1).getSortOrder() : 1;
		saveContent(content, cssClass, page, maxOrder+1);
		return new ModelAndView(new RedirectView("/admin/sitepages/list"));

	}

	private void saveContent(String contentId, String cssClass, SitePage page,
			int order) {
		PageContent pageContent = new PageContent();
		pageContent.setContent(contentRepository.findOne(contentId));
		pageContent.setCssClass(cssClass);
		//pageContent.setSitePage(page);
		pageContent.setSortOrder(order);
		//pageContentRepository.save(pageContent);
		page.addContent(pageContent);
		sitePageRepository.save(page);
	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/{pageContentId}/down", method = RequestMethod.GET)
	public ModelAndView moveDown(@PathVariable String pageId,
			@PathVariable String pageContentId) {
		SitePage page = sitePageRepository.findOne(pageId);
		PageContent pageContent = pageContentRepository.findOne(pageContentId);
		List<PageContent> listOfContents = getListOfContents(page.getPageContents());
		int indexOfContent = listOfContents.indexOf(pageContent);

		if (indexOfContent > -1 && indexOfContent < listOfContents.size() - 1) {
			Collections
					.swap(listOfContents, indexOfContent, indexOfContent + 1);
			for (int i = 0; i < listOfContents.size(); i++) {
				listOfContents.get(i).setSortOrder(i + 1);
			}
			pageContentRepository.save(listOfContents);
		}

		return new ModelAndView(new RedirectView("/admin/sitepages/list"));

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/{pageContentId}/remove", method = RequestMethod.GET)
	public ModelAndView removeContent(@PathVariable String pageId,
			@PathVariable String pageContentId) {
		pageContentRepository.delete(pageContentId);
		return new ModelAndView(new RedirectView("/admin/sitepages/list"));

	}

	@RequestMapping(value = "/admin/sitepages/{pageId}/content/{pageContentId}/up", method = RequestMethod.GET)
	public ModelAndView moveUp(@PathVariable String pageId,
			@PathVariable String pageContentId) {
		SitePage page = sitePageRepository.findOne(pageId);
		PageContent pageContent = pageContentRepository.findOne(pageContentId);
		List<PageContent> listOfContents = getListOfContents(page.getPageContents());
		int indexOfContent = listOfContents.indexOf(pageContent);

		if (indexOfContent > -1 && indexOfContent > 0) {
			Collections
					.swap(listOfContents, indexOfContent - 1, indexOfContent);
			for (int i = 0; i < listOfContents.size(); i++) {
				listOfContents.get(i).setSortOrder(i + 1);
			}
			pageContentRepository.save(listOfContents);
		}

		return new ModelAndView(new RedirectView("/admin/sitepages/list"));

	}

	private List<PageContent> getListOfContents(Set<PageContent> contents) {
		return new ArrayList<PageContent>(contents);
	}

}
