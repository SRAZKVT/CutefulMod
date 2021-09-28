package cutefulmod.config;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;

public abstract class Config extends Option {

    public static final BooleanOption DISABLE_FOG;
    public static final BooleanOption BYPASS_ITEM_FRAME_ENTITY;
    public static final BooleanOption FILL_CLONE_BOUNDING_BOX;
    public static final BooleanOption DISABLE_BLOCK_BREAKING_PARTICLES;
    public static final BooleanOption DISABLE_POTION_EFFECT_PARTICLES;

    static {
        DISABLE_FOG = new BooleanOption("renderNoFog",
                config -> Configs.getRenderNoFog(),
                (config, renderNoFog) -> Configs.setRenderNoFog(renderNoFog)
        );

        BYPASS_ITEM_FRAME_ENTITY = new BooleanOption("bypassItemFrameEntity",
                config -> Configs.getBypassItemFrameEntity(),
                (config, bypassItemFrameEntity) -> Configs.setBypassItemFrameEntity(bypassItemFrameEntity)
        );

        FILL_CLONE_BOUNDING_BOX = new BooleanOption("fillCloneBoundingBox",
                config -> Configs.getFillCloneBoundingBox(),
                (config, fillCloneBoundingBox) -> Configs.setFillCloneBoundingBox(fillCloneBoundingBox)
        );

        DISABLE_BLOCK_BREAKING_PARTICLES = new BooleanOption("disableBlockBreakingParticles",
                config -> Configs.getDisableBlockBreakingParticles(),
                (config, disableBlockBreakingParticles) -> Configs.setDisableBlockBreakingParticles(disableBlockBreakingParticles)
        );
        DISABLE_POTION_EFFECT_PARTICLES = new BooleanOption("disablePotionEffectParticles",
                config -> Configs.getDisablePotionEffectParticles(),
                (config, disablePotionEffectParticles) -> Configs.setDisablePotionEffectParticles(disablePotionEffectParticles)
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