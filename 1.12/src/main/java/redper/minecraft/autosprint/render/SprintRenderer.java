package redper.minecraft.autosprint.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import redper.minecraft.autosprint.AutoSprintMod;
import redper.minecraft.autosprint.handler.KeyHandler;

import java.awt.*;

public class SprintRenderer extends Gui {

    private static SprintRenderer instance = new SprintRenderer();

    private Minecraft mc;
    private Configuration config;
    private ConfigCategory configCategory;

    @SideOnly(Side.CLIENT)
    public SprintRenderer() {

        mc = Minecraft.getMinecraft();

        config = AutoSprintMod.getInstance().getConfig();
        configCategory = config.getCategory(Configuration.CATEGORY_CLIENT);

    }

    public static SprintRenderer getInstance() {
        return instance;
    }

    @SideOnly(Side.CLIENT)
    public void render() {

        Property colorProperty = configCategory.get("hexTextColor");
        String hexColor = colorProperty.getString();
        int color;

        if(hexColor.length() != 6) {

            color = Color.decode("0xAAFFAA").getRGB();
        }

        else {

            try {
                color = Color.decode("0x" + hexColor).getRGB();
            } catch (NumberFormatException e) {
                color = Color.decode("0xAAFFAA").getRGB();
            }

        }

        String s = "Sprint [Toggled]";

        Property backgroundProperty = configCategory.get("useTextBgr");
        Property backgroundColorProperty = configCategory.get("backgroundColor");

        int backgroundColorWA = Color.decode("0x" + backgroundColorProperty.getString()).getRGB();
        int backgroundColor = (backgroundColorWA & 0x00ffffff) | (50 << 24);
        RenderUtils.renderString(s, color, backgroundColor, 5, 5, backgroundProperty.getBoolean());

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRender(RenderGameOverlayEvent event) {
        if(KeyHandler.getInstance().isToggled() && event.getType() == RenderGameOverlayEvent.ElementType.TEXT && !mc.gameSettings.showDebugInfo) {
            render();
        }
    }

}
