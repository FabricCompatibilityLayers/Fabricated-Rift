package io.github.fabriccompatibilitylayers.fabricatedrift.optifabric;

import me.modmuss50.optifabric.mod.OptifabricSetup;
import me.modmuss50.optifabric.mod.OptifineVersion;
import org.dimdev.riftloader.ModInfo;

import java.util.Map;

public class OptifabricUtils {
    public static void registerBuiltinMods(Map<String, ModInfo> modInfos) {
        ModInfo mod = new ModInfo();
        mod.source = OptifabricSetup.optifineRuntimeJar.toFile();
        mod.id = "optifine";
        mod.name = "OptiFine";
        mod.authors.add("sp614x");
        mod.listeners.add(new ModInfo.Listener("org.dimdev.riftloader.OptifineLoader"));
        mod.version = OptifineVersion.version;
        mod.description = "A mod for Rift.";
        mod.url = "https://www.optifine.net";
        modInfos.put("optifine", mod);
    }
}
