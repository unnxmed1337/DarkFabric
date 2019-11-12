package uk.co.hexeption.darkforgereborn;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.hexeption.darkforgereborn.util.LogHelper;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("darkforgereborn")
public class DarkForgeReborn {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    // Instance
    public static final DarkForgeReborn INSTANCE = new DarkForgeReborn();

    public void start() {
        LogHelper.section(String.format("Starting %s v%s", ClientInfo.MOD_NAME, ClientInfo.MOD_BUILD));

        LogHelper.endSection();
        Runtime.getRuntime().addShutdownHook(new Thread(this::end));
    }

    private void end() {

    }

}
