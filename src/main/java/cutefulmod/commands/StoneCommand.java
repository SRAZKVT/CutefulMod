package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class StoneCommand {
    public static void registerCommand (CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("stone").executes((commandContext) -> execute()));
    }

    public static int execute() {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.literal("/setblock ~ ~-1 ~ stone"),false);
        return 1;
    }
}
