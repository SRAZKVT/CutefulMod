package cutefulmod.config;

import cutefulmod.IOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Configs extends GameOptions {
    private static BlockPos blockToCheckRaysOn = null;

    private static Vec3d lastPos = null;
    private static String lastDim = "";

    public static Configs instance;
    private final File configFile = new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt");
    public CyclingOption<Boolean>[] allBooleanConfigs;

    private boolean bypassItemFrameEntity = false;
    private boolean renderNoFog = false;
    private boolean fillCloneBoundingBox = false;
    private boolean disableBlockBreakingParticles = false;
    private boolean disablePotionEffectParticles = false;
    private boolean tntRangeVisualizer = false;
    private boolean tntRaysCount = false;

    public Configs() throws IOException {
        super(MinecraftClient.getInstance(), new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt"));
        instance = this;
        allBooleanConfigs = new CyclingOption[]{
                Config.DISABLE_FOG,
                Config.BYPASS_ITEM_FRAME_ENTITY,
                Config.FILL_CLONE_BOUNDING_BOX,
                Config.DISABLE_BLOCK_BREAKING_PARTICLES,
                Config.DISABLE_POTION_EFFECT_PARTICLES,
                Config.TNT_RANGE_VISUALIZER,
                Config.TNT_RAY_COUNT
        };
        loadFromFile();
    }

    private void loadFromFile() throws IOException {
        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            Scanner reader = new Scanner(configFile);
            while (reader.hasNextLine()) {
                String[] configWord;
                String line = reader.nextLine();
                configWord = line.split(" ");
                if (configWord.length > 1) {
                    switch (configWord[0]) {
                        case "bypassItemFrameEntity":
                            bypassItemFrameEntity = (configWord[1].equals("true"));
                            break;
                        case "renderNoFog":
                            renderNoFog = (configWord[1].equals("true"));
                            break;
                        case "fillCloneBoundingBox":
                            fillCloneBoundingBox = (configWord[1].equals("true"));
                            break;
                        case "disableBlockBreakingParticles":
                            disableBlockBreakingParticles = (configWord[1].equals("true"));
                            break;
                        case "disablePotionEffectParticles":
                            disablePotionEffectParticles = (configWord[1].equals("true"));
                            break;
                        case "tntRangeVisualizer":
                            tntRangeVisualizer = (configWord[1].equals("true"));
                            break;
                        case "tntRaysCount":
                            tntRaysCount = (configWord[1].equals("true"));
                            break;
                    }

                    System.out.println("CutefulMod : Loaded " + configWord[0] + " as " + configWord[1]);
                } else {
                    System.out.println("CutefulMod : The config file is invalid");
                }
            }
        } else {
            System.out.println("CutefulMod : Couldn't find config file, or the file is invalid");
        }
        saveToFile();
    }

    public void saveToFile() throws IOException {
        configFile.delete();
        FileWriter fw = new FileWriter(configFile);
        for (CyclingOption<Boolean> config : allBooleanConfigs) {
            String configKey = ((IOption)config).getKey();
            switch (configKey){
                case "bypassItemFrameEntity":
                    fw.write(((IOption)config).getKey() + " " + Configs.getBypassItemFrameEntity() + "\n");
                    break;
                case "renderNoFog":
                    boolean render = Configs.getRenderNoFog();
                    fw.write(((IOption)config).getKey() + " " + Configs.getRenderNoFog() + "\n");
                    break;
                case "fillCloneBoundingBox":
                    fw.write(((IOption)config).getKey() + " " + Configs.getFillCloneBoundingBox() + "\n");
                    break;
                case "disableBlockBreakingParticles":
                    fw.write(((IOption)config).getKey() + " " + Configs.getDisableBlockBreakingParticles() + "\n");
                    break;
                case "disablePotionEffectParticles":
                    fw.write(((IOption)config).getKey() + " " + Configs.getDisablePotionEffectParticles() + "\n");
                    break;
                case "tntRangeVisualizer":
                    fw.write(((IOption)config).getKey() + " " + Configs.getTntRangeVisualizer() + "\n");
                    break;
                case "tntRaysCount":
                    fw.write(((IOption)config).getKey() + " " + Configs.getTntRayCount() + "\n");
                    break;
            }
        }
        fw.close();
    }

    public static Configs getInstance() {
        return instance;
    }

    public static void setAll(boolean value){
        Configs.setRenderNoFog(value);
        Configs.setFillCloneBoundingBox(value);
        Configs.setBypassItemFrameEntity(value);
        Configs.setDisableBlockBreakingParticles(value);
        Configs.setDisablePotionEffectParticles(value);
    }

    public static void setRenderNoFog(boolean value) {
        Configs.getInstance().renderNoFog = value;
    }
    public static boolean getRenderNoFog() {
        return Configs.getInstance().renderNoFog;
    }
    public static void setBypassItemFrameEntity(boolean value) {
        Configs.getInstance().bypassItemFrameEntity = value;
    }
    public static boolean getBypassItemFrameEntity() {
        return Configs.getInstance().bypassItemFrameEntity;
    }
    public static void setFillCloneBoundingBox(boolean value) {
        Configs.getInstance().fillCloneBoundingBox = value;
    }
    public static boolean getFillCloneBoundingBox() {
        return Configs.getInstance().fillCloneBoundingBox;
    }
    public static void setDisableBlockBreakingParticles(boolean value) {
        Configs.getInstance().disableBlockBreakingParticles = value;
    }
    public static boolean getDisableBlockBreakingParticles() {
        return Configs.getInstance().disableBlockBreakingParticles;
    }
    public static void setDisablePotionEffectParticles(boolean value) {
        Configs.getInstance().disablePotionEffectParticles = value;
    }
    public static boolean getDisablePotionEffectParticles() {
        return Configs.getInstance().disablePotionEffectParticles;
    }
    public static void setTntRangeVisualizer(boolean value) {
        Configs.getInstance().tntRangeVisualizer = value;
    }
    public static boolean getTntRangeVisualizer() {
        return Configs.getInstance().tntRangeVisualizer;
    }
    public static void setTntRayCount(boolean value) {
        Configs.getInstance().tntRaysCount = value;
    }
    public static boolean getTntRayCount() {
        return Configs.getInstance().tntRaysCount;
    }
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
        Configs.blockToCheckRaysOn = blockToCheckRaysOn;
    }
}