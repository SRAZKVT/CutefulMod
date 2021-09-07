package cutefulmod.gui;

import cutefulmod.IOption;
import cutefulmod.config.Configs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.options.Option;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OptionListWidget extends ElementListWidget<OptionListWidget.OptionEntry> {

    public OptionListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
        this.centerListVertically = true;
    }

    public void addAll(Option[] options) {
        for (Option option : options) {
            this.addEntry(OptionEntry.create(option));
        }
    }

    public int getRowWidth() {
        return 400;
    }

    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 32;
    }

    @Environment(EnvType.CLIENT)
    public static class OptionEntry extends ElementListWidget.Entry<OptionListWidget.OptionEntry> {
        private final AbstractButtonWidget button;
        private final String name;
        private final int nameWidth;

        public OptionEntry(AbstractButtonWidget button, String name) {
            this.button = button;
            this.name = name;
            this.nameWidth = MinecraftClient.getInstance().textRenderer.getStringWidth(name);
        }

        public static OptionListWidget.OptionEntry create(Option option) {
            return new OptionListWidget.OptionEntry(option.createButton(Configs.getInstance(), 0, 0, 75), ((IOption)option).getKey());
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            super.mouseClicked(mouseX, mouseY, button);
            return this.button.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            button.y = y + 5;
            button.x = x + 275;
            String[] string = button.getMessage().split(" ");
            button.setMessage(string[string.length - 1]);
            button.render(mouseX, mouseY, delta);

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            float xOfString = (float)(MinecraftClient.getInstance().currentScreen.width / 2 - 100);
            int yOfString = y + height / 2;
            textRenderer.draw(name, xOfString, (float)yOfString, 16777215);
        }
    }
}
