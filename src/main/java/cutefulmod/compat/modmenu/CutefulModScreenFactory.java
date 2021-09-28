package cutefulmod.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import cutefulmod.gui.CutefulModScreen;
import net.minecraft.client.gui.screen.Screen;

public class CutefulModScreenFactory implements ConfigScreenFactory {
    @Override
    public Screen create(Screen parent) {
        return new CutefulModScreen(parent);
    }
}
