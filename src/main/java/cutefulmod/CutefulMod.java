package cutefulmod;

import cutefulmod.config.Configs;
import cutefulmod.render.CutefulRenderController;
import net.fabricmc.api.ModInitializer;

public class CutefulMod implements ModInitializer {

    public static final String MOD_ID = "cutefulmod";

    @Override
    public void onInitialize() {
        Configs.getInstance();
        CutefulRenderController.getInstance();
    }
}
