package cutefulmod.mixin;

import cutefulmod.IGameOptions;
import cutefulmod.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Final
    @Shadow
    public
    GameOptions options;

    @Shadow public abstract void setScreen(Screen screen);

    @Shadow @Nullable public Screen currentScreen;

    @Inject(
            method = "handleInputEvents",
            at = @At(
                    value = "HEAD"
            )
    )
    private void handleCutefulModMenuKeybind(CallbackInfo ci) {
        while (((IGameOptions) options).getCutefulModMenu().wasPressed()) {
            setScreen(AutoConfig.getConfigScreen(ModConfig.class, this.currentScreen).get());
        }
    }
}