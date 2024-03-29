package cutefulmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cutefulmod.utils.CommandUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class BackCommand {
    public static void registerCommand (CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = LiteralArgumentBuilder.literal("back");
        dispatcher.register(builder.executes((commandContext) -> execute()));
    }

    public static int execute() {
        Vec3d lastPos = CommandUtils.getLastPos();
        MinecraftClient mc = MinecraftClient.getInstance();
        assert mc.player != null;
        if (lastPos != null) {
            mc.player.sendMessage( Text.literal("/execute in minecraft:" + CommandUtils.getLastDim() + " run tp " + lastPos.x + " " + lastPos.y + " " + lastPos.z), false);
            CommandUtils.setLastDim("");
            CommandUtils.setLastPos(null);
            return 1;
        } else {
            throw new CommandException(Text.literal("There is no position to go back to"));
        }
    }
}
