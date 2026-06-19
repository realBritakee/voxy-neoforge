package me.cortex.voxy;
import net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen;
import java.lang.reflect.Constructor;
public class Printer2 {
    public static void test() {
        for(Constructor<?> c : VideoSettingsScreen.class.getConstructors()) {
            System.out.println(c);
        }
    }
}
