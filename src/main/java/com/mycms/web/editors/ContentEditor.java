package com.mycms.web.editors;

import java.beans.PropertyEditorSupport;

import com.mycms.domain.Content;
import com.mycms.repository.ContentRepository;

public class ContentEditor extends PropertyEditorSupport {

	private final ContentRepository contentRepository;
	public ContentEditor(ContentRepository contentRepository){
		this.contentRepository=contentRepository;
	}
	@Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(contentRepository.findOne(text));
    }

    @Override
    public String getAsText() {
        Content c = (Content) getValue();
        if (c == null) {
            return null;
        } else {
            return c.getId();
        }
    }
}