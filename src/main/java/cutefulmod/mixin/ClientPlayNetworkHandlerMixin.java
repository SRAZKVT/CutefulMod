package cutefulmod.mixin;

import com.mojang.brigadier.CommandDispatcher;
import cutefulmod.commands.BackCommand;
import cutefulmod.commands.RayCountCommand;
import cutefulmod.commands.StoneCommand;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    private CommandDispatcher<ServerCommandSource> commandDispatcher;

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
