package org.dimdev.riftloader;

import io.github.fabriccompatibiltylayers.modremappingapi.api.v1.ClassTransformer;

public class RiftAccessTransformer implements ClassTransformer {
    @Override
    public boolean handlesClass(String s, String s1) {
        return s.startsWith("net.minecraft");
    }

    @Override
    public byte[] transformClass(String name, String transformedName, byte[] basicClass) {
        return RiftLoader.instance.accessTransformer.transformClass(name, basicClass);
    }
}
