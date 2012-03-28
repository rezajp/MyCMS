package com.mycms.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mycms.repository.PageContentRepository;

//@Configurable
@Document
public class SitePage {


	
	//@Transient
	//@Autowired
	//private PageContentRepository pageContentRepository;
	


	
//	public void setPageContentRepository(PageContentRepository pageContentRepository) {
//		this.pageContentRepository = pageContentRepository;
//	}

	@Id
	private String id= UUID.randomUUID().toString();

	private String validKey;

	private String title;

	private Set<PageContent> pageContents= new HashSet<PageContent>();

	private String cssClass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValidKey() {
		return validKey;
	}

	public void setValidKey(String validKey) {
		this.validKey = validKey;
	}

//	public List<PageContent> getContents() {
//		return pageContentRepository.findBySitePage(this);
//	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<PageContent> getPageContents() {
		return pageContents;
	}

	public void setPageContents(Set<PageContent> pageContents) {
		this.pageContents = pageContents;
	}

	public void addContent(PageContent pageContent) {
		pageContents.add(pageContent);
	}

}
