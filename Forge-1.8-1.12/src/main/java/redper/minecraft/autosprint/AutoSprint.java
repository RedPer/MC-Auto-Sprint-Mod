package redper.minecraft.autosprint;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.lang.reflect.Field;

@Mod(modid = AutoSprint.MODID, name = AutoSprint.NAME, version = AutoSprint.VERSION, clientSideOnly = true, acceptedMinecraftVersions = AutoSprint.MCVERSIONS, guiFactory = "redper.minecraft.autosprint.gui.GuiFactory")
public class AutoSprint {
    public static final String MODID = "redperautosprintmod";
    public static final String NAME = "AutoSprint";
    public static final String VERSION = "@VERSION@";
    public static final String MCVERSIONS = "[1.8, 1.12.2]";

    private Configuration config;
    private ConfigCategory clientConfig;
    private KeybindHandler keybindHandler;
    private SprintHolder sprintHolder;
    private IndicatorRenderer indicatorRenderer;
    private Field configChangedEventModIDField;

    private boolean state = false;

    public AutoSprint() {
        File configFile = new File(Loader.instance().getConfigDir(), "autosprint.cfg");

        try {
            //More ugly workarounds to get minecraft 1.8 to work with the same jar
            configChangedEventModIDField = ConfigChangedEvent.class.getDeclaredField("modID");
            configChangedEventModIDField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Cannot find field", e);
        }

        config = new Configuration(configFile);
        initConfig();

        clientConfig = config.getCategory(Configuration.CATEGORY_CLIENT);
        if(clientConfig.get("rememberAutoSprintState").getBoolean()) state = clientConfig.get("autoSprintState").getBoolean();
    }

    private void initConfig() {
        config.load();

        Property prop;
        prop = config.get(Configuration.CATEGORY_CLIENT, "autoSprintState", false, "Stores the last state of AutoSprint.");
        prop.setShowInGui(false);

        prop = config.get(Configuration.CATEGORY_CLIENT, "rememberAutoSprintState", true, "Determines whether the AutoSprint state will be restored upon game startup.");
        prop.setLanguageKey("config.rememberState");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "showIndicator", true, "Determines whether an indicator with the mod state will be displayed.");
        prop.setLanguageKey("config.showIndicator");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "showDisabledIndicator", false, "Determines whether to show the indicator even if the mod is disabled.");
        prop.setLanguageKey("config.showDisabledIndicator");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "renderIndicatorBackground", true, "Determines whether to render a rectangular background behind the indicator.");
        prop.setLanguageKey("config.renderIndicatorBackground");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorText", "AutoSprint Enabled", "The text that will be shown on the indicator when the mod is enabled");
        prop.setLanguageKey("config.indicatorText");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorDisabledText", "AutoSprint Disabled", "The text that will be shown on the indicator when the mod is disabled");
        prop.setLanguageKey("config.indicatorDisabledText");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorTextColor", "ffffffff", "Color of the text on the indicator when the mod is enabled (stored as a hex value)");
        prop.setLanguageKey("config.indicatorTextColor");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorDisabledTextColor", "ff0000ff", "Color of the text on the indicator when the mod is disabled (stored as a hex value)");
        prop.setLanguageKey("config.indicatorDisabledTextColor");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorBackgroundColor", "17233150", "Color of the indicator background (stored as a hex value)");
        prop.setLanguageKey("config.indicatorBackgroundColor");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorOffsetAnchor", "Top left", "Specifies on which side of the screen the indicator displays", new String[] {"Top left", "Top right", "Bottom left", "Bottom right"});
        prop.setLanguageKey("config.indicatorOffsetAnchor");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorOffsetX", 5, "The offset from the corner of the screen (Axis X).");
        prop.setLanguageKey("config.indicatorOffsetX");
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "indicatorOffsetY", 5, "The offset from the corner of the screen (Axis Y).");
        prop.setLanguageKey("config.indicatorOffsetY");
        prop.setShowInGui(true);

        config.save();
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent event) throws IllegalAccessException {
        if(configChangedEventModIDField.get(event).equals(MODID)) {
            config.save();
        }
    }

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent event) {
        keybindHandler = new KeybindHandler(this);
        sprintHolder = new SprintHolder(this);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(keybindHandler);
        if(state) MinecraftForge.EVENT_BUS.register(sprintHolder);
    }

    @Mod.EventHandler
    public void postInitialization(FMLPostInitializationEvent event) {
        indicatorRenderer = new IndicatorRenderer(this);
        MinecraftForge.EVENT_BUS.register(indicatorRenderer);
    }

    public ConfigCategory getClientConfig() {
        return clientConfig;
    }

    public void setState(boolean state) {
        clientConfig.get("autoSprintState").set(state);
        config.save();
        if(state) MinecraftForge.EVENT_BUS.register(sprintHolder);
        else {
            MinecraftForge.EVENT_BUS.unregister(sprintHolder);
            sprintHolder.disable();
        }
        this.state = state;
    }

    public boolean getState() {
        return state;
    }
}
