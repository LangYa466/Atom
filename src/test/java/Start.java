import java.io.File;
import java.util.Arrays;

import net.minecraft.client.main.Main;

/**
 * Welcome to MCP 1.8.9 for Maven
 * This repository has been created to make working with MCP 1.8.9 easier and cleaner.
 * You can view the MCP 1.8.9 repo here: https://github.com/Marcelektro/MCP-919
 * If you have any questions regarding this, feel free to contact me here: https://marcloud.net/discord
 *
 * Have fun with the MC development ^^
 * Marcelektro
 */

public class Start {
    public static void main(String[] args) {
        // Provide natives
        // Currently supported Linux and Windows
        System.setProperty("org.lwjgl.librarypath", new File("../test_natives/" + (System.getProperty("os.name").startsWith("Windows") ? "windows" : "linux")).getAbsolutePath());

        Main.main(concat(new String[]{"--version", "MavenMCP","--username","Lang7a", "--accessToken", "eyJraWQiOiJhYzg0YSIsImFsZyI6IkhTMjU2In0.eyJ4dWlkIjoiMjUzNTQwNTg0MDM3NTU4MyIsImFnZyI6IkFkdWx0Iiwic3ViIjoiNzFkZmUyODYtZjAyMi00ZjE2LTliMzUtMGFhNTE2MTgzOGY0IiwiYXV0aCI6IlhCT1giLCJucyI6ImRlZmF1bHQiLCJyb2xlcyI6W10sImlzcyI6ImF1dGhlbnRpY2F0aW9uIiwiZmxhZ3MiOlsidHdvZmFjdG9yYXV0aCIsIm1zYW1pZ3JhdGlvbl9zdGFnZTQiLCJvcmRlcnNfMjAyMiIsIm11bHRpcGxheWVyIl0sInByb2ZpbGVzIjp7Im1jIjoiNzlhMDFlZDctY2QwMS00ZTI1LWEyYzQtZjRlOWUyYmQ5NmJlIn0sInBsYXRmb3JtIjoiVU5LTk9XTiIsIm5iZiI6MTcxODk1OTIxNCwiZXhwIjoxNzE5MDQ1NjE0LCJpYXQiOjE3MTg5NTkyMTR9.J3U0rtDvsHrgYXCnbrMIhZFqUPbhmbFqJrXQMNV0C3U",
                "--uuid","79a01ed7cd014e25a2c4f4e9e2bd96be",
                "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
