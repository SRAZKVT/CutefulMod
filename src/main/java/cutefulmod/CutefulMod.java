package cutefulmod;

import cutefulmod.config.Configs;
import cutefulmod.render.CutefulRenderController;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;

public class CutefulMod implements ModInitializer {

    public static final String MOD_ID = "cutefulmod";

    @Override
    public void onInitialize() {
        try {
            new Configs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CutefulRenderController.getInstance();
    }
}
