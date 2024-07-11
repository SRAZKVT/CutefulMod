package cutefulmod.mixin;

import cutefulmod.IChatScreen;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin implements IChatScreen {

    @Unique
    private String message = "";

    @Shadow protected TextFieldWidget chatField;

    @Inject(
            method = "onChatFieldUpdate",
            at = @At("RETURN")
    )
    private void updateMessageOnChatFieldUpdate(CallbackInfo ci) {
        message = this.chatField.getText().trim();
    }

    @Override
    public String cutefulMod$getMessage() {
        return message;
    }
}
