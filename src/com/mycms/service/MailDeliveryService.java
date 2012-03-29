package com.mycms.service;

public interface MailDeliveryService {

	public void sendMail(String to,String subject, String body);
}
