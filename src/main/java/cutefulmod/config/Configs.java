package cutefulmod.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import cutefulmod.IOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class Configs extends GameOptions {
    private static BlockPos blockToCheckRaysOn = null;

    private static Vec3d lastPos = null;
    private static String lastDim = "";

    public static Configs instance;
    private final File configFile = new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt");
    public BooleanOption[] allBooleanConfigs;

    private boolean bypassItemFrameEntity = false;
    private boolean renderNoFog = false;
    private boolean fillCloneBoundingBox = false;
    private boolean disableBlockBreakingParticles = false;
    private boolean disablePotionEffectParticles = false;
    private boolean tntRangeVisualizer = false;
    private boolean tntRaysCount = false;

    private Configs() throws IOException {
        super(MinecraftClient.getInstance(), new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt"));
        instance = this;
        allBooleanConfigs = new BooleanOption[]{
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
                    for (BooleanOption option : allBooleanConfigs) {
                        if (((IOption) option).getKey().equals(configWord[0])) {
                            option.set(this, configWord[1]);
                        }
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
        for (BooleanOption config : allBooleanConfigs) {
            fw.write(((IOption)config).getKey() + " " + config.get(this) + "\n");
        }
        fw.close();
    }

    public static Configs getInstance() {
        if (instance == null) {
            try {
                instance = new Configs();
            } catch (IOException ignored) {}
        }
        return instance;
    }



    public static void setRenderNoFog(boolean value) {
        Configs.getInstance().renderNoFog = value;
        System.out.println("setRenderNoFog Used");
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

    public static Vec3d getLastPos() {
        return lastPos;
    }

    public static void setLastPos(Vec3d pos) {
        lastPos = pos;
    }

    public static String getLastDim() {
        return lastDim;
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