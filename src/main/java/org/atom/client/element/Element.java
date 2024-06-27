package org.atom.client.element;

import lombok.Getter;
import lombok.Setter;
import org.atom.Wrapper;

/**
 * @author LangYa466
 * @since 2024/4/11 18:16
 */

@Getter
@Setter
public class Element implements Wrapper {
    private float mouseX, mouseY;
    protected float x, y, moveX, moveY, width, height;
    private boolean dragging;

    private String name;

    public void setXY(float x,float y) {
        this.x = x;
        this.y = y;
    }

    public void setWH(float width,float height) {
        this.width = width;
        this.height = height;
    }

    public void update(boolean shader) {
        if (dragging) {
            this.setX(this.mouseX - this.moveX);
            this.setY(this.mouseY - this.moveY);
        }

        if (!shader) this.draw();

        this.draw(shader);
    }

    public void draw(boolean shader) { }
    public void draw() { }

    public void updateMousePos(int mouseX, int mouseY) {
        if (dragging) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
    }
}