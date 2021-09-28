package cutefulmod.gui;

import cutefulmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.io.IOException;

public class CutefulModScreen extends Screen {

    private OptionListWidget list;
    private final Configs configs;
    private final Screen parent;

    public CutefulModScreen(Screen parent) {
        super(new LiteralText("CutefulMod Options"));
        configs = Configs.getInstance();
        this.parent = parent;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        list = new OptionListWidget(client, width, height, 32, this.height - 32, 25) {
        };
        list.addAll(configs.allBooleanConfigs);



        this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, Text.of("Done"), (buttonWidget) -> {
            this.onClose();
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, Text.of("Reset Config"), (buttonWidget) -> {
            for (BooleanOption config : configs.allBooleanConfigs) {
                {
                    config.set(configs, "false");
                }
            }
            this.client.openScreen(new CutefulModScreen(this));
        }));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        return list.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        try {
            configs.saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert this.client != null;
        this.client.openScreen(this.parent);
    }

    public void render(MatrixStack matrices, int mousex, int mousey, float delta) {
        this.renderBackgroundTexture(0);
        this.list.render(matrices,mousex, mousey, delta);
        assert client != null;
        drawCenteredText(matrices,client.textRenderer, this.getNarrationMessage(), this.width / 2, 15, 16777215);
        super.render(matrices,mousex, mousey, delta);
    }

    public boolean isPauseScreen() {return false;}
}