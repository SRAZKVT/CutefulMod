package cutefulmod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import cutefulmod.CutefulMod;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At(value = "HEAD"), cancellable = true)
    private static void renderNoFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        if (CutefulMod.config.DISABLE_FOG) {
            RenderSystem.setShaderFogStart(viewDistance * viewDistance);
            RenderSystem.setShaderFogEnd(viewDistance * viewDistance + 1);
            ci.cancel();
        }
    }
}
