package io.github.fabriccompatibilitylayers.fabricatedrift.modmenu;

import com.terraformersmc.modmenu.api.UpdateChecker;
import com.terraformersmc.modmenu.api.UpdateInfo;
import com.terraformersmc.modmenu.util.VersionUtil;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import io.github.fabriccompatibilitylayers.fabricatedrift.FileUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.dimdev.riftloader.ModInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.*;

public class RiftMod implements Mod {
    public final ModInfo modInfo;
    public final Path rootPath;

    public RiftMod(ModInfo modInfo) {
        Path path;
        this.modInfo = modInfo;

        try {
            FileSystem fs = FileUtils.getJarFileSystem(modInfo.source.toPath());
            path = fs.getRootDirectories().iterator().next();
        } catch (IOException e) {
            path = null;
        }
        this.rootPath = path;
    }

    @Override
    public @NotNull String getId() {
        return "rift_" + this.modInfo.id;
    }

    @Override
    public @NotNull String getName() {
        return this.modInfo.name;
    }

    @Override
    public @NotNull DynamicTexture getIcon(FabricIconHandler fabricIconHandler, int i) {
        DynamicTexture found = this.modInfo.iconPath != null ? ModMenuUtils.createIcon(fabricIconHandler, this) : null;

        if (found == null) {
            return fabricIconHandler.createIcon(FabricLoader.getInstance().getModContainer("modmenu").orElseThrow(() -> new RuntimeException("Cannot get ModContainer for Fabric mod with id modmenu")), "assets/modmenu/unknown_icon.png");
        }

        return found;
    }

    @Override
    public @NotNull String getDescription() {
        return this.modInfo.description;
    }

    @Override
    public @NotNull String getVersion() {
        return this.modInfo.version;
    }

    @Override
    public @NotNull String getPrefixedVersion() {
        return VersionUtil.getPrefixedVersion(this.modInfo.version);
    }

    @Override
    public @NotNull List<String> getAuthors() {
        return this.modInfo.authors;
    }

    @Override
    public @NotNull Map<String, Collection<String>> getContributors() {
        return Collections.emptyMap();
    }

    @Override
    public @NotNull SortedMap<String, Set<String>> getCredits() {
        SortedMap<String, Set<String>> credits = new TreeMap<>();
        List<String> authors = this.getAuthors();
        Map<String, Collection<String>> contributors = new HashMap<>();

        for(String author : authors) {
            contributors.put(author, Collections.singletonList("Author"));
        }

        for(Map.Entry<String, Collection<String>> contributor : contributors.entrySet()) {
            for(String role : contributor.getValue()) {
                credits.computeIfAbsent(role, (key) -> new LinkedHashSet<>());
                credits.get(role).add((String)contributor.getKey());
            }
        }

        return credits;
    }

    @Override
    public @NotNull Set<Badge> getBadges() {
        return Collections.emptySet();
    }

    @Override
    public @Nullable String getWebsite() {
        return this.modInfo.url;
    }

    @Override
    public @Nullable String getIssueTracker() {
        return null;
    }

    @Override
    public @Nullable String getSource() {
        return null;
    }

    @Override
    public @Nullable String getParent() {
        String parent = null;

        if ((FabricLoader.getInstance().isModLoaded("modmenu") && modInfo.id.equals("riftmodlist")) ||
                modInfo.id.equals("rift")) {
            parent = "fabricated-rift";
        }

        return parent;
    }

    @Override
    public @NotNull Set<String> getLicense() {
        return Collections.emptySet();
    }

    @Override
    public @NotNull Map<String, String> getLinks() {
        return Collections.emptyMap();
    }

    @Override
    public boolean isReal() {
        return true;
    }

    @Override
    public boolean allowsUpdateChecks() {
        return false;
    }

    @Override
    public @Nullable UpdateChecker getUpdateChecker() {
        return null;
    }

    @Override
    public void setUpdateChecker(@Nullable UpdateChecker updateChecker) {

    }

    @Override
    public @Nullable UpdateInfo getUpdateInfo() {
        return null;
    }

    @Override
    public void setUpdateInfo(@Nullable UpdateInfo updateInfo) {

    }

    @Override
    public void setChildHasUpdate() {

    }

    @Override
    public boolean getChildHasUpdate() {
        return false;
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}
