package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class OptionalCdmField<JBE extends JPABaseEntity> extends CdmFieldMapping {

    private boolean optional = false;

    public boolean isOptional() {
        return optional;
    }

    private void setOptional(boolean optional) {
        this.optional = optional;
    }

    public boolean isPopulated(){
        return getAlternatives()!=null;
    }

    public boolean validate(JBE jpaEntity){
        if (!isOptional() && isPopulated()) {
            return validateInternal(jpaEntity);
        }
        return true;
    }

    public abstract boolean validateInternal(JBE jpaEntity);
}
