package cutefulmod;

import cutefulmod.utils.CommandUtils;
import cutefulmod.config.ModConfig;
import cutefulmod.render.CutefulRenderController;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;

public class CutefulMod implements ModInitializer {

    public static ModConfig config;
    public static final String MOD_ID = "cutefulmod";

    @Override
    public void onInitialize() {
        CutefulRenderController.getInstance();
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
