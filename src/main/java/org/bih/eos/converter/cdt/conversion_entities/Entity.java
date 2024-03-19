package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
          //  generateLog();
            // return extractVisitOccurrence(jpaEntity);
            return Optional.empty();
        } else {
            return Optional.of(jpaEntity);
        }
    }

    private Optional<JPABaseEntity> extractVisitOccurrence(B jpaEntity){
        try {
            Method visitOccurrence = getVisitOccurrenceMethod(jpaEntity);
           return Optional.of((JPABaseEntity)visitOccurrence.invoke(jpaEntity));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Reflection on getVisitOccurrence failed, check your code!");
        }
    }

    private static Method getVisitOccurrenceMethod(Object evaluationObject) throws NoSuchMethodException {
        if (evaluationObject.getClass() == Measurement.class) {
            return Measurement.class.getDeclaredMethod("getVisitOccurrence");
        } else if (evaluationObject.getClass() == ProcedureOccurrence.class) {
            return ProcedureOccurrence.class.getDeclaredMethod("getVisitOccurrence");
        } else if (evaluationObject.getClass() == DrugExposure.class) {
            return DrugExposure.class.getDeclaredMethod("getVisitOccurrence");
        } else if (evaluationObject.getClass() == Observation.class) {
            return Observation.class.getDeclaredMethod("getVisitOccurrence");
        } else if (evaluationObject.getClass() == ConditionOccurrence.class) {
            return ConditionOccurrence.class.getDeclaredMethod("getVisitOccurrence");
        } else if (evaluationObject.getClass() == DeviceExposure.class) {
            return DeviceExposure.class.getDeclaredMethod("getVisitOccurrence");
        }else if (evaluationObject.getClass() == Person.class) {
            return Person.class.getDeclaredMethod("getVisitOccurrence");
        } else {
           throw new NoSuchMethodException();
        }
    }

 /*   private void generateLog() { //TODO add property for display
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            LOG.warn("ignore conversion since field is missing. " + mapper.writeValueAsString(jpaEntity));
        } catch (JsonProcessingException e) {
            //ignore
        }
    }*/

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
