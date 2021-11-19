package cutefulmod.mixin;

import cutefulmod.CutefulMod;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpellParticle.EntityFactory.class)
public class SpellParticleMixin {

    @Inject(method = "createParticle", at = @At("HEAD"), cancellable = true)
    private void removePotionParticleOnEntity(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, CallbackInfoReturnable<Particle> cir) {
        if (CutefulMod.config.DISABLE_POTION_EFFECT_PARTICLES) {
            cir.setReturnValue(null);
        }
    }

}
