package me.cortex.voxy.client.mixin.sodium;

import net.caffeinemc.mods.sodium.client.config.structure.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.SodiumConfigBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;
import me.cortex.voxy.client.config.VoxyConfigScreenPages;
import me.cortex.voxy.commonImpl.VoxyCommon;
import net.caffeinemc.mods.sodium.api.config.ConfigState;

@Mixin(value = SodiumConfigBuilder.class, remap = false)
public class MixinSodiumGameOptionPages {
    @Inject(method = "registerConfigLate", at = @At("RETURN"))
    private void injectVoxyOptions(net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder builder, CallbackInfo ci) {
        me.cortex.voxy.client.config.VoxyConfigScreenPages.register(builder);
    }
}
