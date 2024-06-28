package org.atom;

import de.florianmichael.viamcp.ViaMCP;
import org.atom.api.event.EventManager;
import lombok.Getter;
import org.atom.api.font.FontManager;
import org.atom.api.notification.NotificationManager;
import org.atom.client.command.CommandManager;
import org.atom.client.config.ConfigManager;
import org.atom.client.element.ElementManager;
import org.atom.client.module.ModuleManager;

import java.awt.*;

/**
 * @author LangYa
 * @since 2024/06/19/下午6:54
 */

@Getter
public class Client {

    public static int color = new Color(100, 105, 255).getRGB();
    public static Color colorC = new Color(100, 105, 255);

    public static String name = "Atom";
    public static String author = "LangYa";

    private EventManager eventManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private ElementManager elementManager;
    private ConfigManager configManager;
    private NotificationManager notificationManager;

    @Getter
    private static final Client instance = new Client();

    public void init() {
        eventManager = new EventManager();
        notificationManager = new NotificationManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        elementManager = new ElementManager();
        configManager = new ConfigManager();
        FontManager.init();

        try {
            ViaMCP.create();

            // In case you want a version slider like in the Minecraft options, you can use this code here, please choose one of those:

            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
            ViaMCP.INSTANCE.initAsyncSlider(100, 100, 110, 20); // For custom position and size slider
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
