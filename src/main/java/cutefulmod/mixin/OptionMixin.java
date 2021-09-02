package cutefulmod.mixin;

import cutefulmod.IOption;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Option.class)
public class OptionMixin implements IOption {

    @Final
    @Shadow private String key;

    @Override
    public String getKey() {
        return key;
    }
}
