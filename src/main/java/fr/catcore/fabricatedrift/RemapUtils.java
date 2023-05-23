package fr.catcore.fabricatedrift;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.mapping.tree.ClassDef;
import net.fabricmc.mapping.tree.FieldDef;
import net.fabricmc.mapping.tree.MethodDef;

import static fr.catcore.modremapperapi.remapping.RemapUtil.getNativeNamespace;

public class RemapUtils {
    public static void initMixins() {}

    public static String remapClass(String name) {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();

        for(ClassDef def : FabricLauncherBase.getLauncher().getMappingConfiguration().getMappings().getClasses()) {
            if (def.getName(getNativeNamespace()).equals(name)) {
                return def.getName(resolver.getCurrentRuntimeNamespace());
            }
        }

        return name;
    }

    public static String[] remapField(String owner, String fieldName, String desc) {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();

        for(ClassDef def : FabricLauncherBase.getLauncher().getMappingConfiguration().getMappings().getClasses()) {
            if (def.getName(getNativeNamespace()).equals(owner)) {
                for (FieldDef fieldDef : def.getFields()) {
                    if (fieldDef.getName(getNativeNamespace()).equals(fieldName)
                            && fieldDef.getDescriptor(getNativeNamespace()).equals(desc)) {
                        return new String[]{
                                def.getName(resolver.getCurrentRuntimeNamespace()),
                                fieldDef.getName(resolver.getCurrentRuntimeNamespace()),
                                fieldDef.getDescriptor(resolver.getCurrentRuntimeNamespace())
                        };
                    }
                }
            }
        }

        return new String[] {owner, fieldName, desc};
    }

    public static String[] remapMethod(String owner, String methodName, String desc) {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();

        for(ClassDef def : FabricLauncherBase.getLauncher().getMappingConfiguration().getMappings().getClasses()) {
            if (def.getName(getNativeNamespace()).equals(owner)) {
                for (MethodDef methodDef : def.getMethods()) {
                    if (methodDef.getName(getNativeNamespace()).equals(methodName)
                            && methodDef.getDescriptor(getNativeNamespace()).equals(desc)) {
                        return new String[]{
                                def.getName(resolver.getCurrentRuntimeNamespace()),
                                methodDef.getName(resolver.getCurrentRuntimeNamespace()),
                                methodDef.getDescriptor(resolver.getCurrentRuntimeNamespace())
                        };
                    }
                }
            }
        }

        return new String[] {owner, methodName, desc};
    }
}
