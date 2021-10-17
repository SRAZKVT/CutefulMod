package cutefulmod.mixin;

import cutefulmod.config.Configs;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    // taken from CutelessMod https://github.com/Nessiesson/CutelessMod but added case for /execute
    @Inject(
            method = "sendPacket",
            at = @At(value = "HEAD")
    )
    private void onSendPackets(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ChatMessageC2SPacket) {
            String message = ((ChatMessageC2SPacket) packet).getChatMessage();
            String[] words = message.split(" ");
            boolean isTpCommand = false;
            if (words[0].equals("/tp")) {
                isTpCommand = true;
            } else if (words[0].equals("/execute")) {
                for (int i = 1; i < words.length - 2; i++) {
                    if (words[i].equals("run") && words[i + 1].equals("tp")) {
                        isTpCommand = true;
                        break;
                    }
                }
            }
            if (isTpCommand) {
                Configs.updateLastPosDim();
            }
        }
    }
}
