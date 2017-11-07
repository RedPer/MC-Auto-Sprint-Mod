package redper.minecraft.autosprint;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import redper.minecraft.autosprint.handler.KeyHandler;
import redper.minecraft.autosprint.render.SprintRenderer;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSIONS, clientSideOnly = true, canBeDeactivated = false, guiFactory = Reference.GUI_FACTORY)
public class AutoSprintMod {

    private Configuration config;

    @Mod.Instance(Reference.MOD_ID)
    private static AutoSprintMod instance;

    public Configuration getConfig() { return config; }

    public static AutoSprintMod getInstance() { return instance; }

    public AutoSprintMod() {

        File confFile = new File(Loader.instance().getConfigDir(), "autosprintmod.cfg");
        config = new Configuration(confFile);

        syncConfig(true);

    }

    private void syncConfig(boolean load) {

        if (!config.isChild && load) {
            config.load();
        }

        Property prop;

        prop = config.get(Configuration.CATEGORY_CLIENT, "isSprintToggled", false);
        prop.setComment("Determines if sprint is toggled.");
        prop.setLanguageKey("config.client.sprinttoggled");
        prop.setRequiresMcRestart(true);
        prop.setRequiresWorldRestart(false);
        prop.setShowInGui(false);

        prop = config.get(Configuration.CATEGORY_CLIENT, "doRememberSprint", true);
        prop.setComment("Do Remember Sprint State after Minecraft exit.");
        prop.setLanguageKey("config.client.remembersprint");
        prop.setRequiresMcRestart(false);
        prop.setRequiresWorldRestart(false);
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "hexTextColor", "aaffaa");
        prop.setComment("The Hex Color Of All The text displayed");
        prop.setLanguageKey("config.client.textcolor");
        prop.setRequiresMcRestart(false);
        prop.setRequiresWorldRestart(false);
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "useTextBgr", true);
        prop.setComment("Determines if some background is shown under the text");
        prop.setLanguageKey("config.client.textbackground");
        prop.setRequiresMcRestart(false);
        prop.setRequiresWorldRestart(false);
        prop.setShowInGui(true);

        prop = config.get(Configuration.CATEGORY_CLIENT, "backgroundColor", "172331");
        prop.setComment("Color of the background under the text");
        prop.setLanguageKey("config.client.backroundcolor");
        prop.setRequiresMcRestart(false);
        prop.setRequiresWorldRestart(false);
        prop.setShowInGui(true);

        if(config.hasChanged()) {
            config.save();
        }

    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equals(Reference.MOD_ID)) {
            syncConfig(false);
        }
    }

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(KeyHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(SprintRenderer.getInstance());

    }

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void postInit(FMLPostInitializationEvent event) {

    }



}
