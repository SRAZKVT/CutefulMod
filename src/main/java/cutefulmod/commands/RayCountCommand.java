package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cutefulmod.config.Configs;
import cutefulmod.util.CutefulUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

public class RayCountCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal("raycount");
        dispatcher.register(builder.then(RequiredArgumentBuilder.argument("pos", BlockPosArgumentType.blockPos()))
                .executes((commandContext) -> {
                    String command = commandContext.getInput();
                    String[] words = command.split(" ");
                    BlockPos pos = CutefulUtils.getBlockPosFromStrings(words[1], words[2], words[3]);
                    return execute(pos);
                }));
    }

    public static int execute(BlockPos pos) {
        Configs.setBlockToCheckRaysOn(pos);
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.addChatMessage(new LiteralText("Succesfully set check position at " + CutefulUtils.cutePositionFromPos(pos)), false);
        return 1;
    }
}
