package cutefulmod.mixin;

import com.mojang.brigadier.CommandDispatcher;
import cutefulmod.commands.BackCommand;
import cutefulmod.commands.RayCountCommand;
import cutefulmod.commands.StoneCommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Shadow @Final private CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void injectCommands(boolean isDedicatedServer, CallbackInfo ci) {
        StoneCommand.registerCommand(this.dispatcher);
        BackCommand.registerCommand(this.dispatcher);
        RayCountCommand.register(this.dispatcher);
    }
}
