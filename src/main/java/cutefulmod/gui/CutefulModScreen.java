package cutefulmod.gui;

import cutefulmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
//import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.io.IOException;

public class CutefulModScreen extends Screen {

    private ButtonListWidget list;
    private final Configs configs;

    public CutefulModScreen() {
        super(new LiteralText("CutefulMod Options"));
        configs = Configs.getInstance();
    }


    public void init() {
        //super.init(client, width, height);
        list = new ButtonListWidget(client, width, height, 32, this.height - 32, 25);
        list.addAll(configs.allBooleanConfigs);



        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, Text.of("Done"), (buttonWidget) -> {
            this.client.setScreen(null); //minecraft.openScreen(null);
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, Text.of("Reset Config"), (buttonWidget) -> {
            for (CyclingOption<Boolean> config : configs.allBooleanConfigs) {
                {
                   //also borked
                }
            }
            this.client.setScreen(new CutefulModScreen());
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

    public void render(MatrixStack matrices, int mousex, int mousey, float delta) {
        this.renderBackgroundTexture(0); //.renderDirtBackground(0);
        this.list.render(matrices,mousex, mousey, delta);
        assert client != null;
        drawCenteredText(matrices,client.textRenderer, this.getNarratedTitle(), this.width / 2, 15, 16777215);
        //drawCenteredText();
        super.render(matrices,mousex, mousey, delta);
    }

    public boolean isPauseScreen() {return false;}
}