package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import cutefulmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;

public class BackCommand {
    public static void registerCommand (CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("back").executes((commandContext) -> execute()));
    }

    private static int execute() {
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