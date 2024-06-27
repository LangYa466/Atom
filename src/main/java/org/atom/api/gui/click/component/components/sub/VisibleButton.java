package org.atom.api.gui.click.component.components.sub;

import org.atom.api.font.FontManager;
import org.atom.client.module.Module;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import org.atom.api.gui.click.component.Component;
import org.atom.api.gui.click.component.components.Button;

public class VisibleButton extends Component {

    private boolean hovered;
    private final Button parent;
    private int offset;
    private int x;
    private int y;
    private final Module mod;

    public VisibleButton(Button button, Module mod, int offset) {
        this.parent = button;
        this.mod = mod;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 12, this.hovered ? 0xFF222222 : 0xFF111111);
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
        GL11.glPushMatrix();

        FontManager.F18.drawStringWithShadow("Visible: " + mod.isArray(), (parent.parent.getX() + 7) , (parent.parent.getY() + offset + 2)  +1, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            mod.setArray(!mod.isArray());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
	}
}
