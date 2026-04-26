package me.cortex.voxy.client.mixin;

import net.fabricmc.loader.api.FabricLoader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import me.cortex.voxy.common.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientVoxyMixinPlugin implements IMixinConfigPlugin {
    private static boolean sodiumLegacy = true;
    private boolean valkyrienSkiesInstalled = false;
    private boolean nvidiumInstalled = false;
    private boolean connectorInstalled = false;

    @Override
    public void onLoad(String mixinPackage) {
        valkyrienSkiesInstalled = FabricLoader.getInstance().isModLoaded("valkyrienskies");
        nvidiumInstalled = FabricLoader.getInstance().isModLoaded("nvidium");
        connectorInstalled = FabricLoader.getInstance().isModLoaded("connector");
        try (InputStream stream = getClass().getClassLoader()
                .getResourceAsStream("me/jellysquid/mods/sodium/client/render/SodiumWorldRenderer.class")) {

            if (stream != null) {
                ClassReader reader = new ClassReader(stream);
                ClassNode node = new ClassNode();
                reader.accept(node, 0);

                for (MethodNode method : node.methods) {
                    if (method.name.equals("drawChunkLayer") && method.desc.contains("ChunkRenderMatrices")) {
                        sodiumLegacy = false;
                        break;
                    }
                }
            } else {
                Logger.error("SodiumWorldRenderer class not found");
            }
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return true; }

    @Override public List<String> getMixins() {
        List<String> mixins = new ArrayList<>();
        if (valkyrienSkiesInstalled && !nvidiumInstalled) {
            mixins.add(sodiumLegacy ? "sodium.MixinSodiumWorldRendererVSLegacy" : "sodium.MixinSodiumWorldRendererVS");
        } else {
            mixins.add("sodium.MixinDefaultChunkRenderer");
        }

        return mixins;
    }

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
