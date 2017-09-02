package redper.minecraft.autosprint.render;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import redper.minecraft.autosprint.AutoSprintMod;
import redper.minecraft.autosprint.handler.KeyHandler;

public class SprintRenderer {

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
            color = Integer.parseInt("aaffaa", 16);
        }

        else {

            try {
                color = Integer.parseInt(hexColor, 16);
            }

            catch (NumberFormatException e) {
                color = Integer.parseInt("aaffaa", 16);
            }

        }

        mc.fontRendererObj.drawString("Sprint [Toggled]", 5, 5, color);

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRender(RenderGameOverlayEvent event) {
        if(KeyHandler.getInstance().isToggled() && event.getType() == RenderGameOverlayEvent.ElementType.TEXT && !mc.gameSettings.showDebugInfo) {
            render();
        }
    }

}
