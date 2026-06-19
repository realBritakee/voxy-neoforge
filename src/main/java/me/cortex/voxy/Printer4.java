package me.cortex.voxy;
import net.caffeinemc.mods.sodium.client.gui.SodiumConfigBuilder;
import java.lang.reflect.Method;
public class Printer4 {
    public static void test() {
        for(Method m : SodiumConfigBuilder.class.getDeclaredMethods()) {
            System.out.println(m.getName() + " -> " + m.getReturnType().getName());
        }
    }
}
