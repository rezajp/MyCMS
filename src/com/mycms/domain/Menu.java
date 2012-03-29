package com.mycms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.core.annotation.Order;

@Entity
@Table(name="menus")

public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, unique = true)
	private String title;

	@ManyToOne(optional=true)
	private SitePage page;

	@Column
	private String pageKey;

	@Column
	private boolean published;

	@Column(nullable=false)
	private String cssClass;

	@Column
	private int sortOrder;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
