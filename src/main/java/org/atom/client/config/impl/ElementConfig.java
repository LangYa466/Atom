package org.atom.client.config.impl;

import com.google.gson.JsonObject;
import org.atom.Client;
import org.atom.client.config.Config;

public class ElementConfig extends Config {
    public ElementConfig(String name) {
        super(name);
    }

    @Override
    public JsonObject save() {
        JsonObject object = new JsonObject();
        Client.getInstance().getElementManager().getElements().forEach(element -> {
            JsonObject elementObj = new JsonObject();
            elementObj.addProperty("X", element.getX());
            elementObj.addProperty("Y", element.getY());
            elementObj.addProperty("Width", element.getWidth());
            elementObj.addProperty("Height", element.getHeight());
            elementObj.addProperty("MoveX", element.getMoveX());
            elementObj.addProperty("MoveY", element.getMoveY());

            object.add(element.getName(), elementObj);
        });

        return object;
    }

    @Override
    public void load(JsonObject object) {
        Client.getInstance().getElementManager().getElements().forEach(element -> {
            if (object.has(element.getName())) {
                JsonObject elementObj = object.get(element.getName()).getAsJsonObject();
                if (elementObj.has("X")) element.setX(elementObj.get("X").getAsFloat());
                if (elementObj.has("Y")) element.setX(elementObj.get("Y").getAsFloat());
                if (elementObj.has("Width")) element.setX(elementObj.get("Width").getAsFloat());
                if (elementObj.has("Height")) element.setX(elementObj.get("Height").getAsFloat());
                if (elementObj.has("MoveX")) element.setX(elementObj.get("MoveX").getAsFloat());
                if (elementObj.has("MoveY")) element.setX(elementObj.get("MoveY").getAsFloat());
            }
        });

    }

}
