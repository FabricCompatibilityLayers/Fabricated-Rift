package io.github.fabriccompatibilitylayers.fabricatedrift;

import io.github.fabriccompatibilitylayers.modremappingapi.api.v2.MappingUtils;

public class RemapUtils {
    public static void initMixins() {}

    public static String remapClass(String name) {
        return MappingUtils.mapClass("rift", name.replace(".", "/"));
    }

    public static String[] remapField(String owner, String fieldName, String desc) {
        owner = remapClass(owner);

        MappingUtils.ClassMember member = MappingUtils.mapFieldFromRemappedClass("rift", owner, fieldName, desc);

        return new String[] {owner, member.getName(), MappingUtils.mapDescriptor("rift", member.getDesc())};
    }

    public static String[] remapMethod(String owner, String methodName, String desc) {
        owner = remapClass(owner);

        MappingUtils.ClassMember member = MappingUtils.mapMethodFromRemappedClass("rift", owner, methodName, desc);

        return new String[] {owner, member.getName(), MappingUtils.mapDescriptor("rift", member.getDesc())};
    }
}
