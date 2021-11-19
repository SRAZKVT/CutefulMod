package cutefulmod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "cutefulmod")
public class ModConfig implements ConfigData {
    public boolean DISABLE_FOG = false;
    public boolean BYPASS_ITEM_FRAME_ENTITY = false;
    public boolean FILL_CLONE_BOUNDING_BOX = false;
    public boolean DISABLE_BLOCK_BREAKING_PARTICLES = false;
    public boolean DISABLE_POTION_EFFECT_PARTICLES = false;
    public boolean TNT_RANGE_VISUALIZER = false;
    public boolean TNT_RAY_COUNT = false;
}
