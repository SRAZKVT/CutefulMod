package cutefulmod.mixin;

import cutefulmod.IOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Option.class)
public class OptionMixin implements IOption {

    @Final
    @Shadow private Text key;

    @Override
    public Text getKey() {
        return key;
    }
}