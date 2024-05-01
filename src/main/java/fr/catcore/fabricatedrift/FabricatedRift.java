package fr.catcore.fabricatedrift;

import fr.catcore.modremapperapi.ClassTransformer;
import io.github.fabriccompatibiltylayers.modremappingapi.api.v1.MappingBuilder;
import io.github.fabriccompatibiltylayers.modremappingapi.api.v1.ModRemapper;
import io.github.fabriccompatibiltylayers.modremappingapi.api.v1.RemapLibrary;
import io.github.fabriccompatibiltylayers.modremappingapi.api.v1.VisitorInfos;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.List;
import java.util.Optional;

public class FabricatedRift implements ModRemapper {
    public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer("fabricated-rift").orElseThrow(RuntimeException::new);
    @Override
    public String[] getJarFolders() {
        return new String[0];
    }

    @Override
    public void addRemapLibraries(List<RemapLibrary> list, EnvType envType) {
        list.add(new RemapLibrary(
                MOD_CONTAINER.findPath("./libs/Rift-FINAL.jar").orElseThrow(RuntimeException::new),
                "rift.jar"
        ));
    }

    @Override
    public void registerMappings(MappingBuilder mappingBuilder) {

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
                new VisitorInfos.FullClassMember(
                        "fr/catcore/fabricatedrift/RemapUtils",
                        "initMixins",
                        "",
                        null
                )
        );
    }

    @Override
    public void afterRemap() {
    }

    @Override
    public Optional<String> getDefaultPackage() {
        return Optional.of("net/minecraft/");
    }
}
