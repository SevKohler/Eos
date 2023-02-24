package org.bih.eos.converter.composition;

import com.nedap.archie.rm.composition.ContentItem;

public class CdtExecutionParameter{
    String archetypeId;
    ContentItem contentItem;

    public String getArchetypeId() {
        return archetypeId;
    }

    public void setArchetypeId(String archetypeId) {
        this.archetypeId = archetypeId;
    }

    public ContentItem getContentItem() {
        return contentItem;
    }

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }
}