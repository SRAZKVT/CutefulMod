package cutefulmod.util;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.TntEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import java.util.HashSet;
import java.util.Set;

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

    public static Set<BlockPos> simulateExplosion(float raySizeMultiplier, TntEntity tnt) {
        Set<BlockPos> toExplode = new HashSet<>();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {

                        // calculates how much the ray test position will need to be offset by
                        double xoffset = (2.0F * i - 15.0F) / 15.0F;
                        double yoffset = (2.0F * j - 15.0F) / 15.0F;
                        double zoffset = (2.0F * k - 15.0F) / 15.0F;
                        double g = Math.sqrt(xoffset * xoffset + yoffset * yoffset + zoffset * zoffset);
                        xoffset /= g;
                        yoffset /= g;
                        zoffset /= g;

                        float rayStrength = (0.7F + raySizeMultiplier * 0.6F) * 4.0F;

                        // first ray test position is at tnt position
                        double x = tnt.getX();
                        double y = tnt.getY();
                        double z = tnt.getZ();

                        while (rayStrength > 0.0F) {
                            BlockPos blockPos = new BlockPos(x, y, z);

                            // degrade ray strength in case of non air block
                            BlockState blockState = tnt.world.getBlockState(blockPos);
                            FluidState fluidState = tnt.world.getFluidState(blockPos);
                            if (!blockState.isAir() || !fluidState.isEmpty()) {
                                rayStrength -= (Math.max(blockState.getBlock().getBlastResistance(), fluidState.getBlastResistance()) + 0.3F) * 0.3F; // raystrength -= (blastresistance + 0.3) * 0.3
                            }

                            // if ray goes through block then it is added to the list of blocks to explode
                            if (rayStrength > 0.0F) {
                                toExplode.add(blockPos);
                            }

                            // reduces ray strength (without this ray going through air only would keep looping)
                            rayStrength -= 0.225F;

                            // moves ray test position to 0.3 blocs further than current along the ray
                            x += xoffset * 0.3D;
                            y += yoffset * 0.3D;
                            z += zoffset * 0.3D;
                        }
                    }
                }
            }
        }
        return toExplode;
    }
}
