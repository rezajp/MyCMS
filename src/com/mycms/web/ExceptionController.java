package com.mycms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {
	
	@RequestMapping("/error/not-found")
	public String notFound(){
		return "notFound";
	}

}
