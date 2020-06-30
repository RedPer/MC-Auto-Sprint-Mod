package redper.minecraft.autosprint116;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
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
    public void func_231158_b_(Minecraft p_init_1_, int p_init_2_, int p_init_3_) {
        super.func_231158_b_(p_init_1_, p_init_2_, p_init_3_);
        func_230480_a_(new Button(field_230708_k_ / 2 - 75,  60, 150, 20, new StringTextComponent(I18n.format("gui.autosprintconfig.openConfigFile")), btn -> Util.getOSType().openFile(configFile)));
        func_230480_a_(new Button(field_230708_k_ / 2 - 75,  90, 150, 20, new StringTextComponent(I18n.format("gui.autosprintconfig.openConfigDir")), btn -> Util.getOSType().openFile(configDir)));
        func_230480_a_(new Button(field_230708_k_ / 2 - 156, field_230709_l_ - 48, 300, 20, new StringTextComponent(I18n.format("gui.done")), btn -> {
            field_230706_i_.displayGuiScreen(parentScreen);
        }));
    }

    @Override
    public void func_230430_a_(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        func_231165_f_(0);
        func_238471_a_(matrixStack, field_230712_o_, I18n.format("gui.autosprintconfig.text1"), field_230708_k_ / 2, 10, Color.WHITE.getRGB());
        func_238471_a_(matrixStack, field_230712_o_, I18n.format("gui.autosprintconfig.text2"), field_230708_k_ / 2, 20, Color.WHITE.getRGB());
        super.func_230430_a_(matrixStack, p_render_1_, p_render_2_, p_render_3_);
    }
}
