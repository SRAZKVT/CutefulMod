package cutefulmod.config;

import cutefulmod.IOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Configs extends GameOptions {
    private static BlockPos blockToCheckRaysOn = null;

    private static Vec3d lastPos = null;
    private static String lastDim = "";

    public static Configs instance;
    private final File configFile = new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt");
    public LinkedHashMap<String, BooleanOption> allBooleanConfigs;

    public static boolean bypassItemFrameEntity = false;
    public static boolean renderNoFog = false;
    public static boolean fillCloneBoundingBox = false;
    public static boolean disableBlockBreakingParticles = false;
    public static boolean disablePotionEffectParticles = false;
    public static boolean tntRangeVisualizer = false;
    public static boolean tntRaysCount = false;

    private Configs() throws IOException {
        super(MinecraftClient.getInstance(), new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt"));
        instance = this;
        allBooleanConfigs = new LinkedHashMap<>();

        // add all boolean options in Configs to allBooleanConfigs
        for (Field f : Config.class.getDeclaredFields()) if (f.getType().equals(BooleanOption.class)) {try {allBooleanConfigs.put(((IOption)f.get(null)).getKey(), (BooleanOption) f.get(null));
        } catch (IllegalAccessException e) {e.printStackTrace();}
        }
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
                    BooleanOption option = allBooleanConfigs.get(configWord[0]);
                    if (option != null) option.set(this, configWord[1]);
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
        for (BooleanOption config : allBooleanConfigs.values()) {
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
        int dimId = player.dimension.getRawId();
        String dimName = "";
        switch (dimId) {
            case -1:
                dimName = "the_nether";
                break;
            case 0:
                dimName = "overworld";
                break;
            case 1:
                dimName = "the_end";
                break;
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
