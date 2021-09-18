package cutefulmod.compat.modmenu;

import cutefulmod.gui.CutefulModScreen;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.gui.screen.Screen;

public class CutefulModScreenFactory implements ConfigScreenFactory {
    @Override
    public Screen create(Screen parent) {
        return new CutefulModScreen(parent);
    }
}
