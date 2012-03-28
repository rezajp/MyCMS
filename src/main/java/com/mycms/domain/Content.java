package com.mycms.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Content {

	@Id
	private String id= UUID.randomUUID().toString();

	private String validKey;

	private String htmlValue;

	

	public String getValidKey() {
		return validKey;
	}

	public void setValidKey(String validKey) {
		this.validKey = validKey;
	}

	public String getHtmlValue() {
		return htmlValue;
	}

	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
