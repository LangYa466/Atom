package org.atom.api.value.impl;

import org.atom.api.value.AbstractValue;

public class BooleanValue extends AbstractValue<Boolean> {

    public BooleanValue(String name, boolean enabled) {
        super(name);
        this.setValue(enabled);
    }

}