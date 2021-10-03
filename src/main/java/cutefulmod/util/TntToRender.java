package cutefulmod.util;

import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Objects;

public class TntToRender {
    public TntEntity tnt;
    public HashSet<BlockPos> minimumExplosion;
    public HashSet<BlockPos> maximumExplosion;
    public int lastTimer;

    public TntToRender(TntEntity tnt) {
        this.tnt = tnt;
        this.lastTimer = tnt.getFuseTimer();
        minimumExplosion = (HashSet<BlockPos>) CutefulUtils.simulateExplosion(0F, this.tnt);
        maximumExplosion = (HashSet<BlockPos>) CutefulUtils.simulateExplosion(1F, this.tnt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TntToRender that = (TntToRender) o;
        return Objects.equals(tnt, that.tnt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tnt);
    }
}
