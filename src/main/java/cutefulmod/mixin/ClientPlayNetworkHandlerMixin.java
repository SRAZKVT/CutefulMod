package cutefulmod.mixin;

import com.mojang.brigadier.CommandDispatcher;
import cutefulmod.commands.BackCommand;
import cutefulmod.commands.RayCountCommand;
import cutefulmod.commands.StoneCommand;
import cutefulmod.config.Configs;
import cutefulmod.util.CutefulUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandException;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.server.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    private CommandDispatcher<CommandSource> commandDispatcher;

    @Shadow private MinecraftClient client;

    // taken from CutelessMod https://github.com/Nessiesson/CutelessMod but added case for /execute
    @Inject(
            method = "sendPacket",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void onSendPackets(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ChatMessageC2SPacket) {
            String message = ((ChatMessageC2SPacket) packet).getChatMessage();
            String[] words = message.split(" ");
            try {
                switch (words[0]) {
                    case "/tp":
                        Configs.updateLastPosDim();
                        break;
                    case "/execute":
                        for (int i = 1; i < words.length - 2; i++) {
                            if (words[i].equals("run") && words[i + 1].equals("tp")) {
                                Configs.updateLastPosDim();
                                break;
                            }
                        }
                        break;
                    case "/raycount":
                        if (words.length > 3) {
                            BlockPos pos = CutefulUtils.getBlockPosFromStrings(words[1], words[2], words[3]);
                            if (pos != null) {
                                RayCountCommand.execute(pos);
                            }else {
                                throw new CommandException(new LiteralText("Please enter a valid position"));
                            }
                        } else {
                            throw new CommandException(new LiteralText("Please enter a valid position"));
                        }
                        ci.cancel();
                        break;
                    case "/back":
                        BackCommand.execute();
                        ci.cancel();
                        break;
                    case "/stone":
                        StoneCommand.execute();
                        ci.cancel();
                        break;
                }
            } catch (CommandException e) {
                String error = e.getMessage();
                assert this.client.player != null;
                this.client.player.sendMessage((new LiteralText("")).append(error).formatted(Formatting.RED));
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "onCommandTree",
            at = @At("TAIL")
    )
    private void registerCommandsOnClient(CommandTreeS2CPacket packet, CallbackInfo ci) {
        BackCommand.registerCommand(this.commandDispatcher);
        RayCountCommand.register(this.commandDispatcher);
        StoneCommand.registerCommand(this.commandDispatcher);
    }
}
