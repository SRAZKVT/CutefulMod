package cutefulmod.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class CommandUtils {
    private static BlockPos blockToCheckRaysOn = null;

    private static Vec3d lastPos = null;
    private static String lastDim = "";

    public static String getLastDim() {
        return lastDim;
    }
    public static Vec3d getLastPos() {
        return lastPos;
    }
    public static void setLastPos(Vec3d pos) {
        lastPos = pos;
    }

    public static void setLastDim (String dim){
        lastDim = dim;
    }

    public static void updateLastPosDim() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        setLastPos(player.getPos());
        DimensionType dimId = player.clientWorld.getDimension();
        String dimName = "";
        if (dimId.isBedWorking()) {
            dimName = "overworld";
        } else if (!dimId.isBedWorking() && dimId.isRespawnAnchorWorking()) {
            dimName = "the_nether";
        } else if (!dimId.isBedWorking() && dimId.hasEnderDragonFight()) {
            dimName = "the_end";
        }
        setLastDim(dimName);
    }

    public static BlockPos getBlockToCheckRaysOn() {
        return blockToCheckRaysOn;
    }

    public static void setBlockToCheckRaysOn(BlockPos blockToCheckRaysOn) {
        CommandUtils.blockToCheckRaysOn = blockToCheckRaysOn;
    }
}