package com.mycms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycms.domain.Content;
import com.mycms.repository.ContentRepository;

@Controller
public class ContentController extends AbstractCmsController {

	@Autowired
	private ContentRepository contentRepository;

	@RequestMapping("/admin/contents/list")
	public ModelAndView list()
	{

		return new ModelAndView("contentsList", "contents", contentRepository.findAll());

	}
	@RequestMapping(value="/admin/contents/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id)
	{

		return new ModelAndView("contentEdit", "content", contentRepository.findOne(id));

	}
	@RequestMapping(value="/admin/contents/edit/{id}",method=RequestMethod.POST)
	public String edit(@PathVariable String id,Content content)
	{
		content.setId(id);
		contentRepository.save(content);
		return "redirect:/admin/contents/list";

	}

	@RequestMapping(value="/admin/contents/create",method=RequestMethod.GET)
	public ModelAndView create()
	{

		return new ModelAndView("contentForm", "content", new Content());

	}
	@RequestMapping(value="/admin/contents/create",method=RequestMethod.POST)
	public String create(Content content)
	{
		contentRepository.save(content);
		return "redirect:/admin/contents/list";

	}

}
