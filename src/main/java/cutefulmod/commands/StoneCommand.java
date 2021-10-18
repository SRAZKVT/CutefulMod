package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandSource;

public class StoneCommand {
    public static void registerCommand (CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal("stone");
        dispatcher.register(builder.executes((commandContext) -> execute()));
    }

    public static int execute() {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendChatMessage("/setblock ~ ~-1 ~ stone");
        return 1;
    }
}
