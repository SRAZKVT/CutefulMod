package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cutefulmod.config.Configs;
import cutefulmod.util.CutefulUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class RayCountCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> command = literal("raycount").requires((c) -> Configs.getTntRayCount()).
                then(argument("block position", BlockPosArgumentType.blockPos())).
                then(literal("reset"));
        dispatcher.register(command);
    }

    public static int execute(BlockPos pos) {
        Configs.setBlockToCheckRaysOn(pos);
        assert MinecraftClient.getInstance().player != null;
        if (pos != null) {
            MinecraftClient.getInstance().player.addChatMessage(new LiteralText("Succesfully set check position at " + CutefulUtils.cutePositionFromPos(pos)), false);
        } else {
            MinecraftClient.getInstance().player.addChatMessage(new LiteralText("Succesfully reset check position"), false);
        }
        return 1;
    }

    // this is only temporary, if i ever add more commands that use a require then i'll add a proper command manager, but for now this is good enough
    private static LiteralArgumentBuilder<CommandSource> literal(String string) {
        return LiteralArgumentBuilder.literal(string);
    }
}
