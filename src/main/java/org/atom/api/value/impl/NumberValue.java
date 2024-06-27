package org.atom.api.value.impl;

import lombok.Getter;
import lombok.Setter;
import org.atom.api.value.AbstractValue;

// https://github.com/union4dev/ClientBase/blob/master/src/org/union4dev/base/value/impl/NumberValue.java
@Getter
@Setter
public class NumberValue extends AbstractValue<Double> {

    private Double minimum, maximum, increment;

    public NumberValue(String name, Double value, Double minimum, Double maximum, Double increment) {
        super(name);
        this.setValue(value);
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }

    public NumberValue(String name, Number value, Number minimum, Number maximum, Number increment) {
        super(name);
        this.setValue(value.doubleValue());
        this.minimum = minimum.doubleValue();
        this.maximum = maximum.doubleValue();
        this.increment = increment.doubleValue();
    }
}