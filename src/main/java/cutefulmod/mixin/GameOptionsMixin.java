package cutefulmod.mixin;

import cutefulmod.IGameOptions;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements IGameOptions {

    @Shadow @Final @Mutable
    public KeyBinding[] keysAll;

    public KeyBinding keyCutefulModMenu;

    @Inject(
            method = "load",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onLoadInjectAtHead(CallbackInfo ci) {
        keyCutefulModMenu = new KeyBinding("Open CutefulMod's menu", GLFW.GLFW_KEY_F7, "CutefulMod");
        keysAll = ArrayUtils.add(keysAll, keyCutefulModMenu);
    }

    @Override
    public KeyBinding getCutefulModMenu() {
        return keyCutefulModMenu;
    }
}