package me.cortex.voxy.client.config;

import com.google.common.collect.ImmutableList;
import me.cortex.voxy.client.ClientSessionEvents;
import me.cortex.voxy.client.core.IGetVoxyRenderSystem;
import me.cortex.voxy.client.core.SSAO;
import me.cortex.voxy.client.core.util.IrisUtil;
import me.cortex.voxy.common.util.cpu.CpuLayout;
import me.cortex.voxy.commonImpl.VoxyCommon;

import net.caffeinemc.mods.sodium.api.config.StorageEventHandler;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.option.OptionImpact;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionPageBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionGroupBuilder;
import net.caffeinemc.mods.sodium.client.config.structure.OptionPage;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class VoxyConfigScreenPages {
    private VoxyConfigScreenPages(){}

    public static OptionPage voxyOptionPage = null;

    public static void register(net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder builder) {
        StorageEventHandler storage = () -> VoxyConfig.CONFIG.save();

        OptionPageBuilder pageBuilder = builder.createOptionPage()
            .setName(Component.translatable("voxy.config.title"));

        OptionGroupBuilder generalGroup = builder.createOptionGroup();

        generalGroup.addOption(builder.createBooleanOption(ResourceLocation.parse("voxy:enabled"))
                .setName(Component.translatable("voxy.config.general.enabled"))
                .setTooltip(Component.translatable("voxy.config.general.enabled.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue(true)
                .setBinding((v)->{
                    VoxyConfig s = VoxyConfig.CONFIG;
                    s.enabled = v;
                    if (v && ClientSessionEvents.inSession) {
                        VoxyCommon.createInstance();
                    }

                    if (!v) {
                        var vrsh = (IGetVoxyRenderSystem) Minecraft.getInstance().levelRenderer;
                        if (vrsh != null) {
                            vrsh.voxy$shutdownRenderer();
                        }
                        VoxyCommon.shutdownInstance();
                    }

                    try { IrisUtil.reload(); } catch (Throwable ignored) {}
                }, () -> VoxyConfig.CONFIG.enabled)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        );

        generalGroup.addOption(builder.createBooleanOption(ResourceLocation.parse("voxy:enable_rendering"))
                .setName(Component.translatable("voxy.config.general.rendering"))
                .setTooltip(Component.translatable("voxy.config.general.rendering.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue(true)
                .setBinding((v) -> {
                    VoxyConfig.CONFIG.enableRendering = v;
                    var vrsh = (IGetVoxyRenderSystem) Minecraft.getInstance().levelRenderer;
                    if (vrsh != null) {
                        if (v) {
                            vrsh.voxy$createRenderer();
                        } else {
                            vrsh.voxy$shutdownRenderer();
                        }
                    }
                    try { IrisUtil.reload(); } catch (Throwable ignored) {}
                }, () -> VoxyConfig.CONFIG.enableRendering)
                .setImpact(OptionImpact.HIGH)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        );

        generalGroup.addOption(builder.createIntegerOption(ResourceLocation.parse("voxy:sub_division_size"))
                .setName(Component.translatable("voxy.config.general.subDivisionSize"))
                .setTooltip(Component.translatable("voxy.config.general.subDivisionSize.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue(subDiv2ln(64f))
                .setValueFormatter(v -> Component.literal(String.valueOf(v)))
                .setRange(0, SUBDIV_IN_MAX, 1)
                .setBinding((v) -> VoxyConfig.CONFIG.subDivisionSize = ln2subDiv(v), () -> subDiv2ln(VoxyConfig.CONFIG.subDivisionSize))
                .setImpact(OptionImpact.HIGH)
        );

        generalGroup.addOption(builder.createIntegerOption(ResourceLocation.parse("voxy:render_distance"))
                .setName(Component.translatable("voxy.config.general.renderDistance"))
                .setTooltip(Component.translatable("voxy.config.general.renderDistance.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue(16 * 16)
                .setValueFormatter(v -> Component.literal(String.valueOf(v)))
                .setRange(10, 64 * 16, 1)
                .setBinding((v) -> {
                    VoxyConfig.CONFIG.sectionRenderDistance = ((float)v) / 16.0f;
                    var vrsh = (IGetVoxyRenderSystem) Minecraft.getInstance().levelRenderer;
                    if (vrsh != null) {
                        var vrs = vrsh.voxy$getRenderSystem();
                        if (vrs != null) {
                            vrs.setRenderDistance(VoxyConfig.CONFIG.sectionRenderDistance);
                        }
                    }
                }, () -> Math.round(VoxyConfig.CONFIG.sectionRenderDistance * 16))
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        );

        pageBuilder.addOptionGroup(generalGroup);

        OptionGroupBuilder threadGroup = builder.createOptionGroup();

        threadGroup.addOption(builder.createIntegerOption(ResourceLocation.parse("voxy:service_threads"))
                .setName(Component.translatable("voxy.config.general.serviceThreads"))
                .setTooltip(Component.translatable("voxy.config.general.serviceThreads.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue((int) Math.max(CpuLayout.getCoreCount()/1.5, 1))
                .setValueFormatter(v -> Component.literal(String.valueOf(v)))
                .setRange(1, CpuLayout.getCoreCount(), 1)
                .setBinding((v) -> {
                    VoxyConfig.CONFIG.serviceThreads = v;
                    var instance = VoxyCommon.getInstance();
                    if (instance != null) {
                        instance.updateDedicatedThreads();
                    }
                }, () -> VoxyConfig.CONFIG.serviceThreads)
                .setImpact(OptionImpact.HIGH)
        );

        threadGroup.addOption(builder.createBooleanOption(ResourceLocation.parse("voxy:use_sodium_builder"))
                .setName(Component.translatable("voxy.config.general.useSodiumBuilder"))
                .setTooltip(Component.translatable("voxy.config.general.useSodiumBuilder.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue(true)
                .setImpact(OptionImpact.VARIES)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .setBinding((v) -> {
                    VoxyConfig.CONFIG.dontUseSodiumBuilderThreads = !v;
                    var instance = VoxyCommon.getInstance();
                    if (instance != null) {
                        instance.updateDedicatedThreads();
                    }
                }, ()->!VoxyConfig.CONFIG.dontUseSodiumBuilderThreads)
        );

        threadGroup.addOption(builder.createBooleanOption(ResourceLocation.parse("voxy:ingest"))
                .setName(Component.translatable("voxy.config.general.ingest"))
                .setTooltip(Component.translatable("voxy.config.general.ingest.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue(true)
                .setBinding((v) -> VoxyConfig.CONFIG.ingestEnabled = v, () -> VoxyConfig.CONFIG.ingestEnabled)
                .setImpact(OptionImpact.HIGH)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        );

        pageBuilder.addOptionGroup(threadGroup);

        OptionGroupBuilder fogGroup = builder.createOptionGroup();

        fogGroup.addOption(builder.createBooleanOption(ResourceLocation.parse("voxy:render_fog"))
                .setName(Component.translatable("voxy.config.general.render_fog"))
                .setTooltip(Component.translatable("voxy.config.general.render_fog.tooltip"))
                .setStorageHandler(storage)
                .setDefaultValue(true)
                .setBinding((v) -> VoxyConfig.CONFIG.renderVanillaFog = v, () -> VoxyConfig.CONFIG.renderVanillaFog)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        );

        fogGroup.addOption(builder.createIntegerOption(ResourceLocation.parse("voxy:sky_fog_distance"))
                .setName(Component.literal("Sky fog distance"))
                .setTooltip(Component.literal("Higher distance, sharper sky fog"))
                .setStorageHandler(storage)
                .setDefaultValue(96)
                .setValueFormatter(v -> Component.literal(String.valueOf(v)))
                .setRange(16, 512, 16)
                .setBinding((v) -> VoxyConfig.CONFIG.skyFogDistance = v, () -> VoxyConfig.CONFIG.skyFogDistance)
        );

        net.caffeinemc.mods.sodium.api.config.structure.EnumOptionBuilder<SSAO.SSAOMode> ssaoBuilder = builder.createEnumOption(ResourceLocation.parse("voxy:ssao_mode"), SSAO.SSAOMode.class);
        ssaoBuilder.setName(Component.translatable("voxy.config.general.ssao_mode"));
        ssaoBuilder.setTooltip(Component.translatable("voxy.config.general.ssao_mode.tooltip"));
        ssaoBuilder.setDefaultValue(SSAO.SSAOMode.AUTO);
        ssaoBuilder.setElementNameProvider(mode -> Component.literal(mode.name()));
        ssaoBuilder.setBinding((v) -> {
            VoxyConfig.CONFIG.setSSAOMode(v);
            reloadActiveRenderer();
        }, VoxyConfig.CONFIG::getSSAOMode);
        ssaoBuilder.setStorageHandler(storage);
        ssaoBuilder.setImpact(OptionImpact.HIGH);
        ssaoBuilder.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);

        fogGroup.addOption(ssaoBuilder);

        pageBuilder.addOptionGroup(fogGroup);
        
        var modBuilder = builder.registerModOptions("voxy", "Voxy", "0.2.15-NeoForge-1.21.1");
        modBuilder.setNonTintedIcon(ResourceLocation.parse("voxy:icon.png"));
        modBuilder.addPage(pageBuilder);
    }

    private static void reloadActiveRenderer() {
        try {
            var minecraft = Minecraft.getInstance();
            var renderer = (IGetVoxyRenderSystem) minecraft.levelRenderer;
            if (renderer != null && minecraft.level != null && VoxyConfig.CONFIG.isRenderingEnabled()) {
                renderer.voxy$shutdownRenderer();
                renderer.voxy$createRenderer();
            }
        } catch (Throwable ignored) {}

        try { IrisUtil.reload(); } catch (Throwable ignored) {}
    }

    private static final int SUBDIV_IN_MAX = 100;
    private static final double SUBDIV_MIN = 28;
    private static final double SUBDIV_MAX = 256;
    private static final double SUBDIV_CONST = Math.log(SUBDIV_MAX/SUBDIV_MIN)/Math.log(2);

    private static float ln2subDiv(int in) {
        return (float) (SUBDIV_MIN*Math.pow(2, SUBDIV_CONST*((double)in/SUBDIV_IN_MAX)));
    }

    private static int subDiv2ln(float in) {
        return (int) (((Math.log(((double)in)/SUBDIV_MIN)/Math.log(2))/SUBDIV_CONST)*SUBDIV_IN_MAX);
    }
}
