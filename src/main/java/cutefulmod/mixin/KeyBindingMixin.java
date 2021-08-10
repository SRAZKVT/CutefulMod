package cutefulmod.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {

    @Shadow
    @Final
    private static Map<String, Integer> categoryOrderMap;

    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "RETURN"
            )
    )
    private static void onInitInjectAtReturn(CallbackInfo ci) {
        categoryOrderMap.put("CutefulMod", categoryOrderMap.size() + 1);
    }
}