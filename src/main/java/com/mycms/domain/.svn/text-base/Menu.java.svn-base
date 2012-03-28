package com.mycms.domain;

import java.util.UUID;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Menu {

	@Id
	private String id= UUID.randomUUID().toString();

	
	private String title;

	@DBRef
	private SitePage page;

	private String pageKey;

	private boolean published;

	
	private String cssClass;

	private int sortOrder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public SitePage getPage() {
		return page;
	}

	public void setPage(SitePage page) {
		this.page = page;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
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

	public String getPageKey() {
		return pageKey;
	}

	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	public String getKey(){
		if(page==null)
			return pageKey;
		return page.getValidKey();
	}





}
