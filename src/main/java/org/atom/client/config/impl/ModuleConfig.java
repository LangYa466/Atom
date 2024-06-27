package org.atom.client.config.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.atom.Client;
import org.atom.api.value.AbstractValue;
import org.atom.api.value.impl.BooleanValue;
import org.atom.api.value.impl.NumberValue;
import org.atom.api.value.impl.StringValue;
import org.atom.client.config.Config;

public class ModuleConfig extends Config {
    public ModuleConfig(String name) {
        super(name);
    }

    @Override
    public JsonObject save() {
        JsonObject object = new JsonObject();
        Client.getInstance().getModuleManager().getModules().forEach(module -> {
            JsonObject moduleObj = new JsonObject();
            moduleObj.addProperty("State", module.isState());
            moduleObj.addProperty("KeyCode", module.getKeyCode());

            JsonObject valueObj = new JsonObject();
            moduleObj.add("设置", valueObj);
            for (AbstractValue<?> value : module.getValues()) {
                if (value instanceof NumberValue) {
                    valueObj.addProperty(value.getName(), (value).getValue().toString());
                } else if (value instanceof BooleanValue) {
                    valueObj.addProperty(value.getName(), ((BooleanValue) value).getValue());
                } else if (value instanceof StringValue) {
                    valueObj.addProperty(value.getName(), ((StringValue) value).getValue());
                }
            }
            object.add(module.getName(), moduleObj);
        });

        return object;
    }

    @Override
    public void load(JsonObject object) {
        Client.getInstance().getModuleManager().getModules().forEach(module -> {
            if (object.has(module.getName())) {
                JsonObject moduleObject = object.get(module.getName()).getAsJsonObject();
                if (moduleObject.has("开启状态")) module.setSilentState(moduleObject.get("开启状态").getAsBoolean());
                if (moduleObject.has("绑定按键")) module.setKeyCode(moduleObject.get("绑定按键").getAsInt());
                if (moduleObject.has("设置")) {
                    JsonObject valuesObject = moduleObject.get("设置").getAsJsonObject();
                    for (AbstractValue<?> value : module.getValues()) {
                        if (valuesObject.has(value.getName())) {
                            JsonElement theValue = valuesObject.get(value.getName());
                            if (value instanceof NumberValue) {
                                ((NumberValue) value).setValue(theValue.getAsDouble());
                            } else if (value instanceof BooleanValue) {
                                ((BooleanValue) value).setValue(theValue.getAsBoolean());
                            } else if (value instanceof StringValue) {
                                ((StringValue) value).setValue(theValue.getAsString());
                            }
                        }
                    }
                }
            }
        });
    }
}
