package redper.minecraft.autosprint.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class RenderUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void renderString(String text, int color, int backgroundColor, int x, int y, boolean background) {

        int h = mc.fontRendererObj.FONT_HEIGHT;
        int sw = mc.fontRendererObj.getStringWidth(text);

        if(background)
            Gui.drawRect(x - 1, y - 1, sw + 5, h + 5, backgroundColor);
        mc.fontRendererObj.drawString(text, x, y, color);

    }

    public static void renderString(String text, Color color, Color backgroundColor, int x, int y, boolean background) {

        int h = mc.fontRendererObj.FONT_HEIGHT;
        int sw = mc.fontRendererObj.getStringWidth(text);

        if(background)
            Gui.drawRect(x - 1, y - 1, sw + 5, h + 5, backgroundColor.getRGB());
        mc.fontRendererObj.drawString(text, x, y, color.getRGB());

    }

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(mc);
    }

}
