package com.mycms.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="sitepages")
public class SitePage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, unique = true)
	private String validKey;

	@Column(nullable=false)
	private String title;

	@OneToMany(mappedBy = "sitePage", fetch = FetchType.EAGER)
	@OrderBy(value = "sortOrder")
	private Set<PageContent> contents;

	@Column
	private String cssClass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValidKey() {
		return validKey;
	}

	public void setValidKey(String validKey) {
		this.validKey = validKey;
	}

	public Set<PageContent> getContents() {
		return contents;
	}

	public void setContents(Set<PageContent> contents) {
		this.contents = contents;
	}

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

}
