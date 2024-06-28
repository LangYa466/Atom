package org.atom.api.font;

import org.atom.Client;
import org.atom.api.font.impl.UFontRenderer;
import org.atom.api.util.ChatUtil;
import org.atom.api.util.HTTPUtil;

import java.io.File;
import java.io.IOException;

public class FontManager {
    public static UFontRenderer F14;
    public static UFontRenderer F16;
    public static UFontRenderer F18;
    public static UFontRenderer F30;
    public static UFontRenderer F40;

    private static File outputFile;
    public static void init() {
        downloadFont();
        F14 = getFont(14);
        F16 = getFont(16);
        F18 = getFont(18);
        F30 = getFont(30);
        F40 = getFont(40);
    }

    private static void downloadFont() {
        try {
            outputFile = new File(Client.getInstance().getConfigManager().fontsDir, "MiSans-Normal.ttf");

            if (!outputFile.exists()) {
                ChatUtil.logger.info("Downloading font...");
                HTTPUtil.download("https://fs-im-kefu.7moor-fs1.com/ly/4d2c3f00-7d4c-11e5-af15-41bf63ae4ea0/1719542161462/MiSans-Normal.ttf", outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static UFontRenderer getFont(int size) {
        return new UFontRenderer(outputFile,size);
    }

}