package cutefulmod.utils;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.TntEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
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
    public static Set<BlockPos> simulateExplosion(float raySizeMultiplier, TntEntity tnt){
        return simulateExplosion(raySizeMultiplier, tnt,false);
    }

    public static Set<BlockPos> simulateExplosion(float raySizeMultiplier, TntEntity tnt,boolean countRaysHittingBlockPos) {
        Set<BlockPos> toExplode = new HashSet<>();
        double explosionHeight = tnt.getBodyY(0.0625);
        ArrayList<Double> probabilityOfRayBreakingBlock = new ArrayList<>();
        int raysHittingBlockPos = 0;
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
                        double y = explosionHeight;
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
                                if (countRaysHittingBlockPos && blockPos.equals(CommandUtils.getBlockToCheckRaysOn())) {
                                    raysHittingBlockPos++;

                                    // unwrap ray strength to get min nextFloat value that would break block
                                    double rayStrengthCopy = rayStrength;
                                    rayStrengthCopy = 5.2D - rayStrengthCopy;
                                    rayStrengthCopy /= 4.0D;
                                    rayStrengthCopy -= 0.7D;
                                    rayStrengthCopy /= 0.6D;
                                    // here raystrength needed to blow up block is below minimal of ray (2.8) and so block will be blown up 100% of the time
                                    if (rayStrengthCopy <= 0) {
                                        probabilityOfRayBreakingBlock.add(1.0D);
                                    } else {
                                        probabilityOfRayBreakingBlock.add(rayStrengthCopy);
                                    }
                                    // prevents code from counting a single ray as multiple succesful ones
                                    rayStrength = 0.0F;
                                }
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
        if (countRaysHittingBlockPos) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(new LiteralText("The block pos at " + cutePositionFromPos(CommandUtils.getBlockToCheckRaysOn()) + " has been struck by " + raysHittingBlockPos + " rays."), false);
            double probabilityOfBlockBeingBlownUp = 1D;
            if (!probabilityOfRayBreakingBlock.contains(1D)) {
                for (double probabilityOfRay : probabilityOfRayBreakingBlock) {
                    probabilityOfRay = 1D - probabilityOfRay;
                    probabilityOfBlockBeingBlownUp *= probabilityOfRay;
                }
                probabilityOfBlockBeingBlownUp = 1D - probabilityOfBlockBeingBlownUp;
            }
            MinecraftClient.getInstance().player.sendMessage(new LiteralText("The probability of the block being blown up is " + probabilityOfBlockBeingBlownUp + " or " + probabilityOfBlockBeingBlownUp * 100 + "%."),false);
        }
        return toExplode;
    }
    public static String cutePositionFromPos(BlockPos pos) {
        return "x : " + pos.getX() + ", y : " + pos.getY() + ", z : " + pos.getZ();
    }
}
