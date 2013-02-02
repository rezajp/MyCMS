package com.mycms.web;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycms.domain.Contact;
import com.mycms.repository.ContactRepository;
import com.mycms.service.MailDeliveryService;

@Controller
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;


	//@Autowired
	private MailDeliveryService mailDeliveryService;

	@RequestMapping("/admin/contacts/list")
	public ModelAndView list() {
		List<Contact> contacts=contactRepository.findAll();
		return new ModelAndView("contactsList","contacts",contacts) ;
	}

	@RequestMapping(value = "/contact/thank-you", method = RequestMethod.GET)
	public String contactSucess(Model model) {
		model.addAttribute("pageKey", "contact");
		return "contactSuccess";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contactUs(Model model) {
		model.addAttribute("pageKey", "contact");
		return "contactForm";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public ModelAndView contactUs(HttpServletRequest request,
			@RequestParam String name, @RequestParam String email,
			@RequestParam String message) {
		try {
			Contact contact = saveContact(name, email, message,
					request.getRemoteAddr());
			//notifyOwners(contact);

		} catch (Exception ex) {
			return new ModelAndView("redirect:contactSucess", new ModelMap(
					"error", "An error occured. Please try again."));
		}
		return new ModelAndView("redirect:/contact/thank-you");
	}

	private void notifyOwners(Contact contact) {
//		SiteSettings siteSettings = siteSettingsRepository.findByActive(true)
//				.get(0);
//		mailDeliveryService.sendMail(
//				siteSettings.getNotifyEmail(),
//				"New message from " + contact.getName() + " - "
//						+ contact.getEmail(), contact.getMessage());

	}

	private Contact saveContact(String name, String email, String message,
			String ip) {
		Contact contact = new Contact();
		contact.setEmail(email);
		contact.setMessage(message);
		contact.setName(name);
		contact.setSentAt(Calendar.getInstance().getTime());
		contact.setIp(ip);
		contactRepository.save(contact);
		return contact;
	}
}
