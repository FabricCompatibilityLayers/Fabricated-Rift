package io.github.fabriccompatibilitylayers.fabricatedrift;

import io.github.fabriccompatibilitylayers.fabricatedrift.modmenu.ModMenuUtils;
import net.fabricmc.loader.api.FabricLoader;
import org.dimdev.riftloader.ModInfo;

import java.util.Collection;
import java.util.Map;

public class CompatibilityHelper {
    public static void addToModList(Collection<ModInfo> modInfos) {
        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            ModMenuUtils.addRiftMods(modInfos);
        }
    }

    public static void registerBuiltinMods(Map<String, ModInfo> modInfos) {
        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            ModInfo info = new ModInfo();
            info.id = "riftmodlist";
            info.name = "Rift Mod List";
            info.authors.add("Danielshe");
            info.version = "2.1.0b";
            info.description = "Adds a mod list to your client.";
            info.url = "https://modrinth.com/mod/rift-mod-list";
            info.source = modInfos.get("rift").source;

            modInfos.put(info.id, info);
        }
    }
}
