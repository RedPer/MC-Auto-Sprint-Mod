package redper.minecraft.autosprint.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.Mod;
import redper.minecraft.autosprint.AutoSprint;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig {

    @Mod.Instance
    private static AutoSprint asInstance;

    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), AutoSprint.MODID, false, false, I18n.format("configgui.title"));
    }

    private static List<IConfigElement> getConfigElements() {
        return new ArrayList<>(new ConfigElement(asInstance.getClientConfig()).getChildElements());
    }
}
