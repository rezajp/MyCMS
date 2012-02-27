package com.mycms.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pagecontents")
public class PageContent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(optional=false)
	private SitePage sitePage;

	@ManyToOne(optional=false)
	private Content content;

	@Column
	private int sortOrder;

	@Column
	private String cssClass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SitePage getPage() {
		return sitePage;
	}

	public void setPage(SitePage sitePage) {
		this.sitePage = sitePage;
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

}
