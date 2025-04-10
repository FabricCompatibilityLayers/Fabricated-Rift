package io.github.fabriccompatibilitylayers.fabricatedrift;

import io.github.fabriccompatibilitylayers.modremappingapi.api.v2.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FabricatedRift implements ModRemapper {
    public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer("fabricated-rift").orElseThrow(RuntimeException::new);

    private Path getLibDevPath(String name) {
        return MOD_CONTAINER.findPath("./../../../libs/" + name).orElseThrow(RuntimeException::new);
    }

    @Override
    public String getContextId() {
        return "rift";
    }

    @Override
    public void init(CacheHandler cacheHandler) {

    }

    @Override
    public List<ModDiscovererConfig> getModDiscoverers() {
        return Collections.singletonList(
                ModDiscovererConfig.builder("mods")
                        .candidateCollector(new RiftCandidateCollector())
                        .build()
        );
    }

    @Override
    public List<ModRemapper> collectSubRemappers(List<ModCandidate> list) {
        return Collections.emptyList();
    }

    @Override
    public MappingsConfig getMappingsConfig() {
        return MappingsConfig.defaultConfig();
    }

    @Override
    public List<RemappingFlags> getRemappingFlags() {
        return Collections.singletonList(RemappingFlags.MIXIN);
    }

    @Override
    public void afterRemapping() {

    }

    @Override
    public void afterAllRemappings() {

    }

    @Override
    public void addRemappingLibraries(List<RemapLibrary> list, EnvType envType) {
        list.add(RemapLibrary.of(
                MOD_CONTAINER.findPath("./libs/Rift-FINAL.jar").orElseGet(() -> getLibDevPath("Rift-FINAL.jar")),
                "rift.jar"
        ));
    }

    @Override
    public void registerAdditionalMappings(MappingBuilder mappingBuilder) {

    }

    @Override
    public void registerPreVisitors(VisitorInfos visitorInfos) {

    }

    @Override
    public void registerPostVisitors(VisitorInfos visitorInfos) {
        visitorInfos.registerMethodInvocation(
                "org/spongepowered/asm/launch/MixinBootstrap",
                "init",
                "",
                VisitorInfos.classMember(
                        "io/github/fabriccompatibilitylayers/fabricatedrift/RemapUtils",
                        "initMixins",
                        "",
                        null
                )
        );
    }
}
