package com.mycms.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class PageContent {

	@Id
	private String id= UUID.randomUUID().toString();

//	@DBRef
//	private SitePage sitePage;
//
//	public SitePage getSitePage() {
//		return sitePage;
//	}
//
//	public void setSitePage(SitePage sitePage) {
//		this.sitePage = sitePage;
//	}
	@DBRef
	private Content content;

	private int sortOrder;

	private String cssClass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	


	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getCombinedId(){
		return String.format("ct%s_%s", this.content.getId());//,this.sitePage.getId());
	}

}
