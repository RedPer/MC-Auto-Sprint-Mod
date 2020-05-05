package redper.minecraft.autosprint114;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.loading.FMLPaths;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class AutoSprintConfigGui extends Screen {

    File configDir;
    File configFile;
    Screen parentScreen;

    protected AutoSprintConfigGui(Screen parentScreen) {
        super(new TranslationTextComponent("gui.autosprintconfig.title"));

        Path configDirPath = FMLPaths.CONFIGDIR.get();
        configDir = configDirPath.toFile();
        configFile = new File(configDir, "redperautosprintmod-client.toml");
        this.parentScreen = parentScreen;
    }

    @Override
    public void init(Minecraft p_init_1_, int p_init_2_, int p_init_3_) {
        super.init(p_init_1_, p_init_2_, p_init_3_);
        addButton(new Button(width / 2 - 75,  60, 150, 20, I18n.format("gui.autosprintconfig.openConfigFile"), btn -> Util.getOSType().openFile(configFile)));
        addButton(new Button(width / 2 - 75,  90, 150, 20, I18n.format("gui.autosprintconfig.openConfigDir"), btn -> Util.getOSType().openFile(configDir)));
        addButton(new Button(width / 2 - 156, height - 48, 300, 20, I18n.format("gui.done"), btn -> {
            minecraft.displayGuiScreen(parentScreen);
        }));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        renderDirtBackground(0);
        drawCenteredString(font, I18n.format("gui.autosprintconfig.text1"), width / 2, 10, Color.WHITE.getRGB());
        drawCenteredString(font, I18n.format("gui.autosprintconfig.text2"), width / 2, 20, Color.WHITE.getRGB());
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }
}
