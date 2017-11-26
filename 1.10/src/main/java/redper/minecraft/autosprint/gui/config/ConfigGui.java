package redper.minecraft.autosprint.gui.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import redper.minecraft.autosprint.AutoSprintMod;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig {

    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), "redperautosprintmod", false, false, I18n.format("config.configgui.title"));
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> elements = new ArrayList<IConfigElement>();

        elements.addAll(new ConfigElement(AutoSprintMod.getInstance().getConfig().getCategory(Configuration.CATEGORY_CLIENT)).getChildElements());

        return elements;
    }


}
