package cutefulmod.mixin;

import cutefulmod.IChatScreen;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin implements IChatScreen {

    private String message = "";

    @Shadow protected TextFieldWidget chatField;

    @Inject(
            method = "onChatFieldUpdate",
            at = @At("RETURN"),
            cancellable = true)
    private void updateMessageOnChatFieldUpdate(CallbackInfo ci) {
        message = this.chatField.getText().trim();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
