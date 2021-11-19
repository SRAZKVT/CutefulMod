package cutefulmod.mixin;

import cutefulmod.CutefulMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    private void cancelBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CutefulMod.config.DISABLE_BLOCK_BREAKING_PARTICLES) {
            ci.cancel();
        }
    }
}
