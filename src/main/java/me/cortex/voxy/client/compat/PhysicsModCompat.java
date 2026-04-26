package me.cortex.voxy.client.compat;

import me.cortex.voxy.common.Logger;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Compatibility layer for Physics Mod (physicsmod).
 *
 * Physics Mod ocean physics works by suppressing water top face rendering in
 * the vanilla FluidBlockRenderer and replacing it with a custom ocean mesh.
 * This causes Voxy's LOD baking to produce water blocks with no top face,
 * resulting in invisible water surfaces in LOD chunks (issue #1101) and
 * double-layer water at the LOD/vanilla boundary (issue #1178).
 *
 * @see <a href="https://github.com/haubna/PhysicsMod/issues/1101">PhysicsMod #1101</a>
 * @see <a href="https://github.com/haubna/PhysicsMod/issues/1178">PhysicsMod #1178</a>
 */
public class PhysicsModCompat {

    /** True if Physics Mod is present in the mod list. */
    public static final boolean PHYSICS_MOD_LOADED = FabricLoader.getInstance().isModLoaded("physicsmod");

    private static Boolean oceanEnabledCache = null;

    private PhysicsModCompat() {}

    /** @return true if Physics Mod is installed. */
    public static boolean isLoaded() {
        return PHYSICS_MOD_LOADED;
    }

    /**
     * @return true if Physics Mod ocean simulation is active.
     *         Falls back to {@code true} (conservative) when the config cannot
     *         be read, so Voxy always applies the fallback baking path when
     *         Physics Mod is present.
     */
    public static boolean isOceanEnabled() {
        if (!PHYSICS_MOD_LOADED) return false;
        if (oceanEnabledCache != null) return oceanEnabledCache;

        oceanEnabledCache = detectOceanEnabled();
        Logger.info("PhysicsModCompat: Physics Mod detected, ocean enabled = " + oceanEnabledCache);
        return oceanEnabledCache;
    }

    private static boolean detectOceanEnabled() {
        // Attempt to read Physics Mod's config via reflection.
        // Known class/field candidates based on Physics Mod source inspection.
        String[] configClasses = {
            "com.haubna.physicsmod.config.PhysicsConfig",
            "com.haubna.physicsmod.PhysicsConfig",
            "com.haubna.physicsmod.config.ModConfig",
        };
        String[] oceanFields = {
            "oceanEnabled", "ocean_enabled", "OCEAN_ENABLED",
            "fluidPhysics", "fluid_physics", "enableOcean",
        };

        for (String className : configClasses) {
            try {
                Class<?> cls = Class.forName(className);
                for (String fieldName : oceanFields) {
                    try {
                        java.lang.reflect.Field field = cls.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object value = field.get(null);
                        if (value instanceof Boolean b) {
                            Logger.info("PhysicsModCompat: found " + className + "." + fieldName + " = " + b);
                            return b;
                        }
                    } catch (NoSuchFieldException | IllegalAccessException ignored) {
                        // Try next field name
                    }
                }
            } catch (ClassNotFoundException ignored) {
                // Try next class name
            } catch (Exception e) {
                Logger.warn("PhysicsModCompat: unexpected error reading config: " + e.getMessage());
            }
        }

        // Could not read config — assume ocean is enabled (conservative).
        Logger.info("PhysicsModCompat: Could not read Physics Mod config, assuming ocean is enabled.");
        return true;
    }
}
