package cutefulmod.mixin;

import cutefulmod.IOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/*@Mixin(GameOptions.class)
public class OptionMixin implements IOption {

    @Final
    @Shadow private Text key;

    @Override
    public String getKey() {
        return key.getString();
    }
}*/