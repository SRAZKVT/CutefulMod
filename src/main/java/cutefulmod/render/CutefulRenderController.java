package cutefulmod.render;

import cutefulmod.IChatScreen;
import cutefulmod.config.Configs;
import cutefulmod.util.CutefulUtils;
import cutefulmod.util.TntToRender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CutefulRenderController {

    private static ArrayList<TntToRender> tntToRender;
    private static CutefulRenderController instance;

    private CutefulRenderController() {
        tntToRender = new ArrayList<>();
        instance = this;
    }

    public static CutefulRenderController getInstance() {
        if (instance == null) {
            instance = new CutefulRenderController();
        }
        return instance;
    }

    public static ArrayList<TntToRender> getTntToRender() {
        return tntToRender;
    }

    public static void render(MatrixStack matrices) {
        renderFillCloneBoundingBox(matrices);
        renderTntExplosionRange(matrices);
    }

    private static void renderFillCloneBoundingBox(MatrixStack matrices) {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        if (currentScreen instanceof ChatScreen && !((IChatScreen)currentScreen).getMessage().equals("") && Configs.getFillCloneBoundingBox()) {
            String[] args = ((IChatScreen) currentScreen).getMessage().split(" ");
            if ((args[0].equals("/fill") || args[0].equals("/clone")) && args.length >= 7) {
                BlockPos pos1;
                BlockPos pos2;
                pos1 = CutefulUtils.getBlockPosFromStrings(args[1], args[2], args[3]);
                pos2 = CutefulUtils.getBlockPosFromStrings(args[4], args[5], args[6]);
                if (pos1 != null && pos2 != null) {
                    CutefulRenderUtils.drawBoxWithOutline(matrices, pos1, pos2, 1, 1, 1, 0.4F, 0, 0, 0);
                    if (args[0].equals("/clone") && args.length >= 10) {
                        BlockPos pos3;
                        pos3 = CutefulUtils.getBlockPosFromStrings(args[7], args[8], args[9]);
                        if (pos3 != null) {
                            int x1 = Math.abs(pos1.getX() - pos2.getX());
                            int y1 = Math.abs(pos1.getY() - pos2.getY());
                            int z1 = Math.abs(pos1.getZ() - pos2.getZ());
                            BlockPos pos4 = new BlockPos(pos3.getX() + x1, pos3.getY() + y1, pos3.getZ() + z1);

                            CutefulRenderUtils.drawBoxWithOutline(matrices, pos3, pos4, 0.16F, 0.5F, 0.45F, 0.4F, 0, 0, 0);
                        }
                    }
                }
            } else if ((args[0].equals("/setblock")) && args.length >= 4) {
                BlockPos pos;
                pos = CutefulUtils.getBlockPosFromStrings(args[1], args[2], args[3]);
                if (pos != null) {
                    CutefulRenderUtils.drawBoxWithOutline(matrices, pos, pos, 1, 1, 1, 0.4F, 0, 0, 0);
                }
            }
        }
    }

    private static void renderTntExplosionRange(MatrixStack matrices) {
        if (Configs.getTntRangeVisualizer()) {
            Iterator<TntToRender> itr = tntToRender.iterator();
            while (itr.hasNext()) {
                TntToRender tntObjectToRender = itr.next();
                if (tntObjectToRender.tnt.getFuseTimer() != tntObjectToRender.lastTimer) {
                    tntObjectToRender.minimumExplosion = (HashSet<BlockPos>) CutefulUtils.simulateExplosion(0, tntObjectToRender.tnt);
                    tntObjectToRender.maximumExplosion = (HashSet<BlockPos>) CutefulUtils.simulateExplosion(1, tntObjectToRender.tnt);
                }
                CutefulRenderUtils.drawFacesOfBlocksInHashset(matrices, tntObjectToRender.minimumExplosion, 117, 243, 236, 0.3F);
                CutefulRenderUtils.drawFacesOfBlocksInHashset(matrices, tntObjectToRender.maximumExplosion, 238, 72, 72, 0.3F);

                if (tntObjectToRender.tnt.removed) {
                    itr.remove();
                }
            }
        }
    }
}
