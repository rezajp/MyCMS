package com.mycms.web.editors;

import java.beans.PropertyEditorSupport;

import com.mycms.domain.SitePage;
import com.mycms.repository.SitePageRepository;

public class SitePageEditor extends PropertyEditorSupport {

	private final SitePageRepository sitePageRepository;
	public SitePageEditor(SitePageRepository sitePageRepository){
		this.sitePageRepository=sitePageRepository;
	}
	@Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(sitePageRepository.findOne(text));
    }

    @Override
    public String getAsText() {
        SitePage s = (SitePage) getValue();
        if (s == null) {
            return null;
        } else {
            return s.getId();
        }
    }
}
