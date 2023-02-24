package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;

public abstract class Entity<B extends JPABaseEntity> {
    protected B jpaEntity;
    Boolean requiredFieldEmpty = false;
    Boolean requiredOptionalFieldNull = false;
    private static final Logger LOG = LoggerFactory.getLogger(Entity.class);

    public Entity(B jpaEntity) {
        this.jpaEntity = jpaEntity;
    }

    protected abstract Boolean validateRequiredOptionalsNotNull();

    public Optional<JPABaseEntity> toJpaEntity() {
        requiredOptionalFieldNull = !validateRequiredOptionalsNotNull();
        if (requiredFieldEmpty || requiredOptionalFieldNull) {
            generateLog();
            return Optional.empty();
        } else {
            return Optional.of(jpaEntity);
        }
    }

    private void generateLog() { //TODO add property for display
/*        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            LOG.warn("ignore conversion since field is missing. " + mapper.writeValueAsString(jpaEntity));
        } catch (JsonProcessingException e) {
            //ignore
        }*/
    }

    public <T> void populateFieldIfPresent(Optional<T> optionalValue, Consumer<T> setterConsumer) {
        requiredFieldEmpty = CDMFieldSetter.setFieldIfPresent(optionalValue, requiredFieldEmpty, setterConsumer);
    }

    public <T> void populateFieldIfPresent(Optional<T> optionalValue, Boolean isOptional, Consumer<T> setterConsumer) {
        requiredFieldEmpty = CDMFieldSetter.setFieldIfPresent(optionalValue, requiredFieldEmpty, isOptional, setterConsumer);
    }

    public static Optional<Double> convertToOptionalDouble(OptionalDouble value) {
        return value.isPresent() ?
                Optional.of(value.getAsDouble()) : Optional.empty();
    }

    public static Optional<Integer> convertToOptionalInteger(OptionalDouble value) {
        if (value.isEmpty()) {
            return Optional.empty();
        } else {
            Double quantityAsDouble = value.getAsDouble();
            return Optional.of(quantityAsDouble.intValue());
        }
    }
}
