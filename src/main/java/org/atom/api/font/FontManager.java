package org.atom.api.font;

import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * @author cubk
 */
public class FontManager {
    public static CFontRenderer F14 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 14));
    public static CFontRenderer F16 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 16));
    public static CFontRenderer F18 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 18));
    public static CFontRenderer F30 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 30));
    public static CFontRenderer F40 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 40));

    private static Font getFont(ResourceLocation resourceLocation, float fontSize) {
        try {
            Font output = Font.createFont(Font.PLAIN, Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream());
            output = output.deriveFont(fontSize);
            return output;
        } catch (Exception e) {
            return null;
        }
    }
}