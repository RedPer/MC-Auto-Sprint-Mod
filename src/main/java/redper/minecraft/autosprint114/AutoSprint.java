package redper.minecraft.autosprint114;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.reflect.Field;

@Mod(AutoSprint.MODID)
public class AutoSprint {

    public static final String MODID = "redperautosprintmod";

    private AutoSprintConfig config;
    private KeybindHandler keybindHandler;
    private IndicatorRenderer indicatorRenderer;
    private SprintHolder sprintHolder;

    private boolean state = false;

    public AutoSprint() {
        config = new AutoSprintConfig();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, config.getConfigSpec());
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> this::openConfigGui);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        if(config.rememberAutoSprintState.get()) state = config.autoSprintState.get();

        keybindHandler = new KeybindHandler(this);
        indicatorRenderer = new IndicatorRenderer(this);
        sprintHolder = new SprintHolder(this);

        MinecraftForge.EVENT_BUS.register(keybindHandler);
        MinecraftForge.EVENT_BUS.register(indicatorRenderer);
        if(state) MinecraftForge.EVENT_BUS.register(sprintHolder);
    }

    public Screen openConfigGui(Minecraft mc, Screen parentScreen) {
        return new AutoSprintConfigGui(parentScreen);
    }

    public void setState(boolean state) {
        config.autoSprintState.set(state);
        if(state) MinecraftForge.EVENT_BUS.register(sprintHolder);
        else {
            MinecraftForge.EVENT_BUS.unregister(sprintHolder);
            sprintHolder.disable();
        }
        this.state = state;
    }

    public AutoSprintConfig getConfig() {
        return config;
    }

    public boolean getState() {
        return state;
    }
}
