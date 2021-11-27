package cutefulmod.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;

public class CutefulRenderUtils {
    public static void drawBoxWithOutline(MatrixStack matrices, BlockPos pos1, BlockPos pos2, float fillred, float fillgreen, float fillblue, float fillalpha, float outlinered, float outlinegreen, float outlineblue) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        BlockPos posOrigin = new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();

        Vec3d cameraPos = camera.getPos();

        matrices.push();
        matrices.translate(posOrigin.getX() - cameraPos.getX(), posOrigin.getY() - cameraPos.getY(), posOrigin.getZ() - cameraPos.getZ());

        //Matrix4f model = matrices.peek().getModel();
        Matrix4f model = matrices.peek().getPositionMatrix();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        drawBox(bufferBuilder, model, pos1, pos2, outlinered, outlinegreen, outlineblue, 1, true);
        tessellator.draw();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        drawBox(bufferBuilder, model, pos1, pos2, fillred, fillgreen, fillblue, fillalpha, false);
        tessellator.draw();

        matrices.pop();

        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawBox(BufferBuilder bufferBuilder, Matrix4f model, BlockPos pos1, BlockPos pos2, float red, float green, float blue, float alpha, boolean outline) {
        float x1 = Math.abs(pos1.getX() - pos2.getX()) + 1.002F;
        float y1 = Math.abs(pos1.getY() - pos2.getY()) + 1.002F;
        float z1 = Math.abs(pos1.getZ() - pos2.getZ()) + 1.002F;

        float c0 = -0.002F;
        // is slightly outside to avoid z-fighting

        // Back Face
        bufferBuilder.vertex(model, c0, c0, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, c0, c0, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, c0, y1, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, c0, y1, c0).color(red, green, blue, alpha).next();
        if (outline) {
            bufferBuilder.vertex(model, c0, c0, c0).color(red, green, blue, alpha).next();
        }

        // Front Face
        bufferBuilder.vertex(model, x1, c0, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, y1, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, y1, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, c0, z1).color(red, green, blue, alpha).next();
        if(outline) {
            bufferBuilder.vertex(model, x1, c0, c0).color(red, green, blue, alpha).next();
        }

        // Right Face
        bufferBuilder.vertex(model, c0, c0, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, c0, y1, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, y1, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, c0, c0).color(red, green, blue, alpha).next();
        if (outline) {
            bufferBuilder.vertex(model, c0, c0, c0).color(red, green, blue, alpha).next();
        }

        // Left Face
        bufferBuilder.vertex(model, c0, c0, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, c0, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, y1, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, c0, y1, z1).color(red, green, blue, alpha).next();
        if (outline) {
            bufferBuilder.vertex(model, c0, c0, z1).color(red, green, blue, alpha).next();
        }

        // Bottom Face
        bufferBuilder.vertex(model, c0, c0, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, c0, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, c0, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, c0, c0, z1).color(red, green, blue, alpha).next();
        if (outline) {
            bufferBuilder.vertex(model, c0, c0, c0).color(red, green, blue, alpha).next();
        }

        // Top Face
        bufferBuilder.vertex(model, c0, y1, c0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, c0, y1, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, y1, z1).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, y1, c0).color(red, green, blue, alpha).next();
        if (outline) {
            bufferBuilder.vertex(model, c0, y1, c0).color(red, green, blue, alpha).next();
        }
    }
    public static void drawFacesOfBlocksInHashset(MatrixStack matrices, HashSet<BlockPos> set, float fillred, float fillgreen, float fillblue, float fillalpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        int x = 0, y = 0, z = 0;

        boolean awa = true;

        for (BlockPos pos : set) {
            if (awa) {
                x = pos.getX();
                y = pos.getY();
                z = pos.getZ();
                awa = false;
            }
            x = Math.min(x, pos.getX());
            y = Math.min(y, pos.getY());
            z = Math.min(z, pos.getZ());
        }

        BlockPos posOrigin = new BlockPos(x, y, z);

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();

        Vec3d cameraPos = camera.getPos();

        matrices.push();
        matrices.translate(posOrigin.getX() - cameraPos.getX(), posOrigin.getY() - cameraPos.getY(), posOrigin.getZ() - cameraPos.getZ());

        Matrix4f model = matrices.peek().getPositionMatrix(); //.getModel();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        //RenderSystem.disableLighting();
        RenderSystem.disableCull();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        renderFacesOfBlockposInHashset(bufferBuilder, model, posOrigin, set, fillred, fillgreen, fillblue, fillalpha);
        tessellator.draw();

        matrices.pop();

        RenderSystem.enableCull();
        //RenderSystem.enableLighting();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
    public static void renderFacesOfBlockposInHashset(BufferBuilder bufferBuilder, Matrix4f model, BlockPos origin, HashSet<BlockPos> positions, float red, float green, float blue, float alpha) {
        for (BlockPos pos : positions) {
            int x = pos.getX() - origin.getX(), y = pos.getY() - origin.getY(), z = pos.getZ() - origin.getZ();

            // is slightly outside to avoid z-fighting
            float x0 = -0.002F + x;
            float y0 = -0.002F + y;
            float z0 = -0.002F + z;
            float x1 = 1.002F + x;
            float y1 = 1.002F + y;
            float z1 = 1.002F + z;

            // west Face
            if (!positions.contains(pos.west())) {
                bufferBuilder.vertex(model, x0, y0, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x0, y0, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x0, y1, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x0, y1, z0).color(red, green, blue, alpha).next();
            }


            // east Face
            if (!positions.contains(pos.east())) {
                bufferBuilder.vertex(model, x1, y0, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y1, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y1, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y0, z1).color(red, green, blue, alpha).next();
            }


            // north Face
            if (!positions.contains(pos.north())) {
                bufferBuilder.vertex(model, x0, y0, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x0, y1, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y1, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y0, z0).color(red, green, blue, alpha).next();
            }

            // south Face
            if (!positions.contains(pos.south())) {
                bufferBuilder.vertex(model, x0, y0, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y0, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y1, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x0, y1, z1).color(red, green, blue, alpha).next();
            }

            // down Face
            if (!positions.contains(pos.down())) {
                bufferBuilder.vertex(model, x0, y0, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y0, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y0, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x0, y0, z1).color(red, green, blue, alpha).next();
            }

            // up Face
            if (!positions.contains(pos.up())) {
                bufferBuilder.vertex(model, x0, y1, z0).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x0, y1, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y1, z1).color(red, green, blue, alpha).next();
                bufferBuilder.vertex(model, x1, y1, z0).color(red, green, blue, alpha).next();
            }
        }
    }

}
