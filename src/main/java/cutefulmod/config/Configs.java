package cutefulmod.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import cutefulmod.IOption;
import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;

public class Configs extends GameOptions {
    public static Configs instance;
    private final File configFile = new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt");
    public CyclingOption<Boolean>[] allBooleanConfigs;

    private boolean bypassItemFrameEntity = false;
    private boolean renderNoFog = false;
    private boolean fillCloneBoundingBox = false;
    private boolean disableBlockBreakingParticles = false;
    private boolean disablePotionEffectParticles = false;

    public Configs() throws IOException {
        super(MinecraftClient.getInstance(), new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt"));
        instance = this;
        allBooleanConfigs = new CyclingOption[]{
                Config.DISABLE_FOG,
                Config.BYPASS_ITEM_FRAME_ENTITY,
                Config.FILL_CLONE_BOUNDING_BOX,
                Config.DISABLE_BLOCK_BREAKING_PARTICLES,
                Config.DISABLE_POTION_EFFECT_PARTICLES
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
           //file write is borked
        }
        fw.close();
    }

    public static Configs getInstance() {
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
}