package cutefulmod.mixin;

import cutefulmod.IGameOptions;
import cutefulmod.gui.CutefulModScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    GameOptions options;

    @Shadow public abstract void openScreen(Screen screen);

    @Shadow
    public Screen currentScreen;

    @Inject(
            method = "handleInputEvents",
            at = @At(
                    value = "HEAD"
            )
    )
    private void handleCutefulModMenuKeybind(CallbackInfo ci) {
        while (((IGameOptions) options).getCutefulModMenu().wasPressed()) {
            openScreen(new CutefulModScreen(this.currentScreen));
        }
    }
}