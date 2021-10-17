package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import cutefulmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

public class RayCountCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("raycount")
                .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                .executes((commandContext) -> execute(BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos")))));
    }

    private static int execute(BlockPos pos) {
        Configs.setBlockToCheckRaysOn(pos);
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.addChatMessage(new LiteralText("Succesfully set check position at " + pos.toString()), false);
        return 1;
    }
}
