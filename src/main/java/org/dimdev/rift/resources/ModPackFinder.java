package org.dimdev.rift.resources;

import net.fabricmc.loader.impl.util.FileSystemUtil;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.PackMetadataSection;
import org.dimdev.riftloader.ModInfo;
import org.dimdev.riftloader.RiftLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.Path;
import java.util.Map;

public class ModPackFinder implements IPackFinder {
    private final ResourcePackType type;

    public ModPackFinder(ResourcePackType type) {
        this.type = type;
    }

    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
        for (ModInfo mod : RiftLoader.instance.getMods()) {
            if (mod.id.equals("rift")) continue;
            try {
                FileSystemUtil.FileSystemDelegate delegate = FileSystemUtil.getJarFileSystem(mod.source.toPath().toAbsolutePath().normalize(), false);
                FileSystem fs = delegate.get();

                if (fs == null) {
                    throw new RuntimeException("Could not open JAR file " + mod.source + " for NIO reading!");
                }

                Path rootPath = fs.getRootDirectories().iterator().next();

                try (ModPack pack = new ModPack(mod.name != null ? mod.name : mod.id, rootPath)) {
                    PackMetadataSection meta = pack.getMetadata(PackMetadataSection.SERIALIZER);
                    if (meta != null && !pack.getResourceNamespaces(type).isEmpty()) {
                        nameToPackMap.put(mod.id, packInfoFactory.create(mod.id, type == ResourcePackType.field_14188, () -> pack, pack, meta, ResourcePackInfo.Priority.field_14280));
                    }
                } catch (IOException ignored) {
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (FileSystemAlreadyExistsException ignored) {
            }
        }
    }

    public URL getRootUrl(ModInfo mod) {
        File source = mod.source;
        URL root;
        try {
            if (source.isFile()) {
                root = new URL("jar:" + source.toURI().toURL() + "!/");
            } else {
                root = source.toURI().toURL();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return root;
    }
}
