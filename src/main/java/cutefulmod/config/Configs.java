package cutefulmod.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;

public class Configs extends GameOptions {
    public static Configs instance;
    private final File configFile = new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt");

    public final Config[] allConfigs;

    public Config bypassItemFrameEntity = new Config("bypassItemFrameEntity", false);
    public Config renderNoFog = new Config("renderNoFog", false);
    public Config fillCloneBoundingBox = new Config("fillCloneBoundingBox", false);
    public Config disableBlockBreakingParticles = new Config("disableBlockBreakingParticles", false);
    public Config disablePotionEffectParticles = new Config("disablePotionEffectParticles", false);

    public Configs() throws IOException {
        super(MinecraftClient.getInstance(), new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "cuteful.txt"));
        instance = this;
        allConfigs = new Config[]{
                bypassItemFrameEntity,
                renderNoFog,
                fillCloneBoundingBox,
                disableBlockBreakingParticles,
                disablePotionEffectParticles
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
                switch (configWord[0]) {
                    case "bypassItemFrameEntity":
                        bypassItemFrameEntity.value = (configWord[1].equals("true"));
                        break;
                    case "renderNoFog":
                        renderNoFog.value = (configWord[1].equals("true"));
                        break;
                    case "fillCloneBoundingBox":
                        fillCloneBoundingBox.value = (configWord[1].equals("true"));
                        break;
                    case "disableBlockBreakingParticles":
                        disableBlockBreakingParticles.value = (configWord[1].equals("true"));
                        break;
                    case "disablePotionEffectParticles":
                        disablePotionEffectParticles.value = (configWord[1].equals("true"));
                        break;
                }
                System.out.println("CutefulMod : Loaded " + configWord[0] + " as " + configWord[1]);
            }
        } else {
            System.out.println("CutefulMod : Couldn't find config file, or the file is invalid");
        }
        saveToFile();
    }

    public void saveToFile() throws IOException {
        configFile.delete();
        FileWriter fw = new FileWriter(configFile);
        for (Config config : allConfigs) {
            fw.write(config.name + " " + config.value + "\n");
        }
        fw.close();
    }

    public static Configs getInstance() {
        return instance;
    }
}
