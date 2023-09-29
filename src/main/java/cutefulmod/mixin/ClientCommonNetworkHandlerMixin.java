package cutefulmod.mixin;

import cutefulmod.commands.BackCommand;
import cutefulmod.commands.RayCountCommand;
import cutefulmod.commands.StoneCommand;
import cutefulmod.utils.CommandUtils;
import cutefulmod.utils.CutefulUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.command.CommandException;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
abstract class ClientCommonNetworkHandlerMixin {
    @Shadow @Final protected MinecraftClient client;
    // taken from CutelessMod https://github.com/Nessiesson/CutelessMod but added case for /execute
    @Inject(
            method = "sendPacket",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void onSendPackets(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ChatMessageC2SPacket) {
            String message = ((ChatMessageC2SPacket) packet).chatMessage();
            String[] words = message.split(" ");
            try {
                switch (words[0]) {
                    case "/tp":
                        CommandUtils.updateLastPosDim();
                        break;
                    case "/execute":
                        for (int i = 1; i < words.length - 2; i++) {
                            if (words[i].equals("run") && words[i + 1].equals("tp")) {
                                CommandUtils.updateLastPosDim();
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
                                throw new CommandException(Text.literal("Please enter a valid position"));
                            }
                        } else {
                            throw new CommandException(Text.literal("Please enter a valid position"));
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
                this.client.player.sendMessage(Text.literal("").append(error).formatted(Formatting.RED),false);
                ci.cancel();
            }
        }
    }
}
