package org.atom.api.gui.click.component;

import lombok.Getter;
import lombok.Setter;
import org.atom.Client;
import org.atom.api.font.FontManager;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import org.atom.api.gui.click.ClickGuiScreen;
import org.atom.api.gui.click.component.components.Button;

import java.util.ArrayList;


public class Frame {

    @Getter
    public ArrayList<Component> components;
    public Category category;
    @Setter
    @Getter
    private boolean open;
    @Getter
    private final int width;
    @Getter
    @Setter
    private int y;
    @Getter
    @Setter
    private int x;
    private final int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;

    public Frame(Category cat) {
        this.components = new ArrayList<>();
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        for (Module mod : Client.getInstance().getModuleManager().getModulesByCategory(category)) {
            Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public void renderFrame() {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, ClickGuiScreen.color);
        GL11.glPushMatrix();
        FontManager.F18.drawStringWithShadow(this.category.name(), (this.x + 2)  + 5, (this.y + 2.5f) , 0xFFFFFFFF);
        FontManager.F18.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 10)  + 3, (this.y + 2.5f) , -1);
        GL11.glPopMatrix();
        if (this.open) {
            if (!this.components.isEmpty()) {
                for (Component component : components) {
                    component.renderComponent();
                }
            }
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (Component comp : components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public boolean isWithinHeader(int x, int y) {
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
	}

}