package redper.minecraft.autosprint.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

public class GuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ConfigGui(parentScreen);
    }

    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}
