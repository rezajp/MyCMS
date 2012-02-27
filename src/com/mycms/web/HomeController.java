package com.mycms.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

import org.apache.tomcat.jni.Time;
import org.dom4j.util.UserDataDocumentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycms.domain.SitePage;
import com.mycms.repository.SitePageRepository;
import com.mycms.web.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping("/")
public class HomeController extends AbstractCmsController {

	@Autowired
	private SitePageRepository sitePageRepository;

	@RequestMapping("/")
	public ModelAndView index() {

		SitePage page = sitePageRepository.readByValidKey("home");
		if (page == null)
			throw new ResourceNotFoundException();
		ModelMap map = new ModelMap();
		map.addAttribute("pageKey", page.getValidKey());
		map.addAttribute("page", page);
		//File path = new File("c:\\junk\\test");
		//boolean createdDir = path.mkdirs();

		//File file = new File(path.getPath() + "\\"
		//		+ Calendar.getInstance().getTimeInMillis() + ".log");

		//Writer output = null;
//		try {
//			output = new BufferedWriter(new FileWriter(file));
//			output.write("damn u ");
//			output.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		map.addAttribute("filePath", createdDir + " " + file.getPath());
		return new ModelAndView("home", map);

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
