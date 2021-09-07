package cutefulmod.gui;

import cutefulmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.text.LiteralText;

import java.io.IOException;

public class CutefulModScreen extends Screen {

    private OptionListWidget list;
    private final Configs configs;

    public CutefulModScreen() {
        super(new LiteralText("CutefulMod Options"));
        configs = Configs.getInstance();
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        list = new OptionListWidget(client, width, height, 32, this.height - 32, 25);
        list.addAll(configs.allBooleanConfigs);



        this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, "Done", (buttonWidget) -> {
            this.minecraft.openScreen(null);
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, "Reset config", (buttonWidget) -> {
            for (BooleanOption config : configs.allBooleanConfigs) {
                {
                    config.set(configs, "false");
                }
            }
            this.minecraft.openScreen(new CutefulModScreen());
        }));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        return list.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        super.onClose();
        try {
            configs.saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(int mousex, int mousey, float delta) {
        this.renderDirtBackground(0);
        this.list.render(mousex, mousey, delta);
        assert minecraft != null;
        drawCenteredString(minecraft.textRenderer, this.getNarrationMessage(), this.width / 2, 15, 16777215);
        super.render(mousex, mousey, delta);
    }

    public boolean isPauseScreen() {return false;}
}
