package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class StoneCommand {
    public static void registerCommand (CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("stone").executes((commandContext) -> execute()));
    }

    private static int execute() {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendChatMessage("/setblock ~ ~-1 ~ stone");
        return 1;
    }
}
