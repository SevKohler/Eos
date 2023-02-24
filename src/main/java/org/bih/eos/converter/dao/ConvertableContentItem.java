package org.bih.eos.converter.dao;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.converter.composition.CdtExecutionParameterMedData;
import org.bih.eos.jpabase.model.entity.Person;

public class ConvertableContentItem {
    private final Locatable contentItem;
    private final Person person;

    public ConvertableContentItem(Locatable contentItem, Person person) {
        this.contentItem = contentItem;
        this.person = person;
    }

    public ConvertableContentItem(CdtExecutionParameterMedData cdtExecutionParameter) {
        this.contentItem = cdtExecutionParameter.getContentItem();
        this.person = cdtExecutionParameter.getPerson();
    }

    public Locatable getContentItem() {
        return contentItem;
    }

    public Person getPerson() {
        return person;
    }

}
