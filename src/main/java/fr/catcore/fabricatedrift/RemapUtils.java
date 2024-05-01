package fr.catcore.fabricatedrift;

import io.github.fabriccompatibiltylayers.modremappingapi.api.MappingUtils;

public class RemapUtils {
    public static void initMixins() {}

    public static String remapClass(String name) {
        return MappingUtils.mapClass(name.replace(".", "/"));
    }

    public static String[] remapField(String owner, String fieldName, String desc) {
        owner = remapClass(owner);

        MappingUtils.ClassMember member = MappingUtils.mapFieldFromRemappedClass(owner, fieldName, desc);

        return new String[] {owner, member.name, MappingUtils.mapDescriptor(member.desc)};
    }

    public static String[] remapMethod(String owner, String methodName, String desc) {
        owner = remapClass(owner);

        MappingUtils.ClassMember member = MappingUtils.mapMethodFromRemappedClass(owner, methodName, desc);

        return new String[] {owner, member.name, MappingUtils.mapDescriptor(member.desc)};
    }
}
