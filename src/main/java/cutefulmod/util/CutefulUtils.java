package cutefulmod.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class CutefulUtils {
    public static BlockPos getBlockPosFromStrings(String x, String y, String z) {
        BlockPos pos;
        int posX;
        int posY;
        int posZ;

        assert MinecraftClient.getInstance().player != null;
        BlockPos playerPos = MinecraftClient.getInstance().player.getBlockPos();

        try {
            if (x.startsWith("~")) {
                x = x.substring(1);
                int offset = 0;
                if (!x.isEmpty()) {
                    offset = Integer.parseInt(x);
                }
                posX = playerPos.getX() + offset;
            } else {
                posX = Integer.parseInt(x);
            }
            if (y.startsWith("~")) {
                y = y.substring(1);
                int offset = 0;
                if (!y.isEmpty()) {
                    offset = Integer.parseInt(y);
                }
                posY = playerPos.getY() + offset;
            } else {
                posY = Integer.parseInt(y);
            }
            if (z.startsWith("~")) {
                z = z.substring(1);
                int offset = 0;
                if (!z.isEmpty()) {
                    offset = Integer.parseInt(z);
                }
                posZ = playerPos.getZ() + offset;
            } else {
                posZ = Integer.parseInt(z);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        pos = new BlockPos(posX, posY, posZ);
        return pos;
    }
}
