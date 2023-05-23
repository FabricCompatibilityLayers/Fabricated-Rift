package org.dimdev.riftloader;

import fr.catcore.modremapperapi.api.IClassTransformer;
//import net.minecraft.launchwrapper.IClassTransformer;

public class RiftAccessTransformer implements IClassTransformer {
    @Override
    public boolean handlesClass(String s, String s1) {
        return s.startsWith("net.minecraft");
    }

    @Override
    public byte[] transformClass(String name, String transformedName, byte[] basicClass) {
        return RiftLoader.instance.accessTransformer.transformClass(name, basicClass);
    }
}
