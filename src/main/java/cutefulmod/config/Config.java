package cutefulmod.config;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;

public abstract class Config extends Option {

    public static final CyclingOption<Boolean> DISABLE_FOG;
    public static final CyclingOption<Boolean> BYPASS_ITEM_FRAME_ENTITY;
    public static final CyclingOption<Boolean> FILL_CLONE_BOUNDING_BOX;
    public static final CyclingOption<Boolean>  DISABLE_BLOCK_BREAKING_PARTICLES;
    public static final CyclingOption<Boolean>  DISABLE_POTION_EFFECT_PARTICLES;

    static {
        DISABLE_FOG =  CyclingOption.create("renderNoFog",
                config -> Configs.getRenderNoFog(),
                (gameOptions,config,renderNoFog) -> Configs.setRenderNoFog(renderNoFog)
        );

        BYPASS_ITEM_FRAME_ENTITY = CyclingOption.create("bypassItemFrameEntity",
                config -> Configs.getBypassItemFrameEntity(),
                (gameOptions,config,bypassItemFrameEntity) -> Configs.setBypassItemFrameEntity(bypassItemFrameEntity)
        );

        FILL_CLONE_BOUNDING_BOX = CyclingOption.create("fillCloneBoundingBox",
                config -> Configs.getFillCloneBoundingBox(),
                (gameOptions,config, fillCloneBoundingBox) -> Configs.setFillCloneBoundingBox(fillCloneBoundingBox)
        );

        DISABLE_BLOCK_BREAKING_PARTICLES = CyclingOption.create("disableBlockBreakingParticles",
                config -> Configs.getDisableBlockBreakingParticles(),
                (gameOptions,config, disableBlockBreakingParticles) -> Configs.setDisableBlockBreakingParticles(disableBlockBreakingParticles)
        );
        DISABLE_POTION_EFFECT_PARTICLES =  CyclingOption.create("disablePotionEffectParticles",
                config -> Configs.getDisablePotionEffectParticles(),
                (gameOptions,config, disablePotionEffectParticles) -> Configs.setDisablePotionEffectParticles(disablePotionEffectParticles)
        );
    }

    public Config(String key) {
        super(key);
    }

    @Override
    public ButtonWidget createButton(GameOptions options, int x, int y, int width) {
        return null;
    }
}