package org.bih.eos.converter.dao;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;

public class ConversionTrack {
    private final Locatable inputContentItem;
    private final JPABaseEntity conversionResult;
    private Boolean persisted;

    public ConversionTrack(Locatable inputContentItem, JPABaseEntity conversionResult) {
        this.inputContentItem = inputContentItem;
        this.conversionResult = conversionResult;
        this.persisted = false;
    }

    public Locatable getInputContentItem() {
        return inputContentItem;
    }

    public JPABaseEntity getConversionResult() {
        return conversionResult;
    }

    public Boolean isPersisted() {
        return persisted;
    }

    public void setPersistedTrue() {
        this.persisted = true;
    }

}
