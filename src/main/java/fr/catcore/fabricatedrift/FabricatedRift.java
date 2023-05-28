package fr.catcore.fabricatedrift;

import fr.catcore.modremapperapi.api.ModRemapper;
import fr.catcore.modremapperapi.api.RemapLibrary;
import fr.catcore.modremapperapi.remapping.RemapUtil;
import fr.catcore.modremapperapi.remapping.VisitorInfos;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FabricatedRift implements ModRemapper {
    public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer("fabricated-rift").orElseThrow(RuntimeException::new);
    @Override
    public String[] getJarFolders() {
        return new String[0];
    }

    @Override
    public Map<String, List<String>> getExclusions() {
        return new HashMap<>();
    }

    @Override
    public void getMappingList(RemapUtil.MappingList mappingList) {}

    @Override
    public void addRemapLibraries(List<RemapLibrary> libraries, EnvType environment) {
        libraries.add(new RemapLibrary(
                MOD_CONTAINER.findPath("./libs/Rift-1.0.4-77.jar").orElseThrow(RuntimeException::new),
                "rift.jar"
        ));
    }

    @Override
    public void registerVisitors(VisitorInfos visitorInfos) {
        visitorInfos.registerMethodMethodIns(
                new VisitorInfos.MethodNamed("org/spongepowered/asm/launch/MixinBootstrap", "init"),
                new VisitorInfos.MethodNamed("fr/catcore/fabricatedrift/RemapUtils", "initMixins")
        );
    }

    @Override
    public Optional<String> getDefaultPackage() {
        return Optional.of("net/minecraft/");
    }
}
