package io.github.fabriccompatibilitylayers.fabricatedrift.modmenu;

import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import io.github.fabriccompatibilitylayers.fabricatedrift.RiftCandidateCollector;
import io.github.fabriccompatibilitylayers.fabricatedrift.mixin.modmenu.FabricIconHandlerAccessor;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import org.apache.commons.lang3.Validate;
import org.dimdev.riftloader.ModInfo;

import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

public class ModMenuUtils {
    public static DynamicTexture createIcon(FabricIconHandler handler, RiftMod info) {
        try {
            FabricIconHandlerAccessor accessor = (FabricIconHandlerAccessor) handler;

            Path iconPath = info.rootPath.resolve(info.modInfo.iconPath.replace("/", info.rootPath.getFileSystem().getSeparator()));

            if (!accessor.getCache().containsKey(iconPath)) {
                try (FileSystem fs = RiftCandidateCollector.getJarFileSystem(info.modInfo.source.toPath())) {
                    InputStream inputStream = Files.newInputStream(fs.getPath("/" + info.modInfo.iconPath));

                    DynamicTexture var8;
                    try {
                        NativeImage image = NativeImage.read(Objects.requireNonNull(inputStream));
                        Validate.validState(image.getWidth() == image.getHeight(), "Must be square icon");
                        DynamicTexture tex = new DynamicTexture(image);
                        accessor.getCache().put(iconPath, tex);
                        var8 = tex;
                    } catch (Throwable var10) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable var9) {
                                var10.addSuppressed(var9);
                            }
                        }

                        throw var10;
                    }

                    if (inputStream != null) {
                        inputStream.close();
                    }

                    return var8;
                }
            }

            return accessor.getCache().get(iconPath);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public static void addRiftMods(Collection<ModInfo> modInfos) {
        for (ModInfo modInfo : modInfos) {
            Mod mod = new RiftMod(modInfo);

            ModMenu.MODS.put(mod.getId(), mod);
        }

        ModMenu.clearModCountCache();
    }
}
