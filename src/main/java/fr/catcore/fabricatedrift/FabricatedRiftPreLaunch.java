package fr.catcore.fabricatedrift;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class FabricatedRiftPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        try {
            Class<?> clazz = Class.forName("org.dimdev.riftloader.RiftLoader");
            clazz.getMethod("load", boolean.class).invoke(clazz.getField("instance").get(null), FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
