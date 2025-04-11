package org.dimdev.riftloader;

import org.dimdev.rift.Rift.RiftTokens;
import org.dimdev.riftloader.listener.InitializationListener;

import org.spongepowered.asm.mixin.Mixins;

public class OptifineLoader implements InitializationListener {
	public static final String OPTIFINE_TRANSFORMER = "optifine.OptiFineClassTransformer";

    @Override
    public void onInitialization() {
        RiftTokens.hasOptifine = true;
        Mixins.addConfiguration("mixins.rift.optifine.json");
    }
}
