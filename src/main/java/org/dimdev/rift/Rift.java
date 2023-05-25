package org.dimdev.rift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.riftloader.listener.InitializationListener;

public class Rift implements InitializationListener {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitialization() {
        LOGGER.info("Loading Rift!");
    }
}
