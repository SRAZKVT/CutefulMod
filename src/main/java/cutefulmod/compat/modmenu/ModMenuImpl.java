package cutefulmod.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import cutefulmod.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //return new CutefulModScreenFactory();
        return parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }
}
