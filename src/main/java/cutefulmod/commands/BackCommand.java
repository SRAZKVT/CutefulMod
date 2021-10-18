package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cutefulmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;

public class BackCommand {
    public static void registerCommand (CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal("back");
        dispatcher.register(builder.executes((commandContext) -> execute()));
    }

    public static int execute() {
        Vec3d lastPos = Configs.getLastPos();
        MinecraftClient mc = MinecraftClient.getInstance();
        assert mc.player != null;
        if (lastPos != null) {
            mc.player.sendChatMessage("/execute in minecraft:" + Configs.getLastDim() + " run tp " + lastPos.x + " " + lastPos.y + " " + lastPos.z);
            Configs.setLastDim("");
            Configs.setLastPos(null);
            return 1;
        } else {
            throw new CommandException(new LiteralText("There is no position to go back to"));
        }
    }
}