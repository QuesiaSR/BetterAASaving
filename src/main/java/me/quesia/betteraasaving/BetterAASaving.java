package me.quesia.betteraasaving;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import org.apache.logging.log4j.*;

public class BetterAASaving {
    public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer("betteraasaving").orElseThrow(RuntimeException::new);
    public static final String MOD_NAME = MOD_CONTAINER.getMetadata().getName();
    public static Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static void log(Object msg) {
        LOGGER.log(Level.INFO, msg);
    }
}
