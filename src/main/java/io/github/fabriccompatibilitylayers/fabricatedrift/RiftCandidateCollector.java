package io.github.fabriccompatibilitylayers.fabricatedrift;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.fabriccompatibilitylayers.modremappingapi.api.v2.ModCandidate;
import io.github.fabriccompatibilitylayers.modremappingapi.api.v2.ModDiscovererConfig;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiftCandidateCollector implements ModDiscovererConfig.Collector {
    private static final Gson GSON = new Gson();

    @Override
    public List<ModCandidate> collect(ModDiscovererConfig modDiscovererConfig, Path path, List<String> list) {
        boolean riftFound = false;

        for (String entry : list) {
            if (entry.endsWith("fabric.mod.json") || entry.endsWith("quilt.mod.json") || entry.endsWith("quilt.mod.json5")) {
                return Collections.emptyList();
            }

            if (entry.endsWith("riftmod.json")) {
                riftFound = true;
            }
        }

        if (riftFound) {
            try (FileSystem fs = getJarFileSystem(path)) {
                Path riftModPath = fs.getPath("/riftmod.json");
                JsonObject object = GSON.fromJson(Files.newBufferedReader(riftModPath), JsonObject.class);

                return Collections.singletonList(new RiftCandidate(
                        object.get("id").getAsString(),
                        path,
                        modDiscovererConfig
                ));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

    private static final Map<String, String> ZIP_PROPERTIES = new HashMap<>();

    static {
        ZIP_PROPERTIES.put("create", "false");
        ZIP_PROPERTIES.put("encoding", "UTF-8");
    }

    private static FileSystem getJarFileSystem(Path path) throws IOException {
        return FileSystems.newFileSystem(URI.create("jar:" + path.toUri()), ZIP_PROPERTIES);
    }
}
