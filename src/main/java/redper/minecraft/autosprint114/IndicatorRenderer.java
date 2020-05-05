package redper.minecraft.autosprint114;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.awt.*;

public class IndicatorRenderer extends AbstractGui {

    private AutoSprint asInstance;
    private AutoSprintConfig config;
    private Minecraft mc;

    private boolean showIndicator = true;
    private boolean showDisabledIndicator = false;
    private boolean renderIndicatorBackground = true;
    private String indicatorText = "AutoSprint Enabled";
    private String indicatorDisabledText = "AutoSprint Disabled";
    private int indicatorTextColor = getColor("ffffffff");
    private int indicatorDisabledTextColor = getColor("ff0000ff");
    private int indicatorBackgroundColor = getColor("17233188");
    private int indicatorXPos = 5;
    private int indicatorDXPos = 5;
    private int indicatorYPos = 5;

    public IndicatorRenderer(AutoSprint asInstance) {
        this.asInstance = asInstance;
        config = asInstance.getConfig();
        mc = Minecraft.getInstance();
        readConfig();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigChange);
    }

    private void onConfigChange(ModConfig.ConfigReloading event) {
        readConfig();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        boolean state = asInstance.getState();
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT && !mc.gameSettings.showDebugInfo && showIndicator && (state || showDisabledIndicator)) {
            String text = state ? indicatorText : indicatorDisabledText;
            int color = state ? indicatorTextColor : indicatorDisabledTextColor;
            int posX = state ? indicatorXPos : indicatorDXPos;
            int posY = indicatorYPos;

            if(renderIndicatorBackground) {
                int height = mc.fontRenderer.FONT_HEIGHT;
                int width = mc.fontRenderer.getStringWidth(text);
                fill(posX - 1, posY - 1, posX + width + 1, posY + height + 1, indicatorBackgroundColor);
            }

            drawString(mc.fontRenderer, text, posX, posY, color);
        }
    }

    private int getColor(String hexColor) {
        Color colorNoAlpha = Color.decode("0x" + hexColor.substring(0, 6));
        int alpha = Integer.parseInt(hexColor.substring(6, 8), 16);
        return new Color(colorNoAlpha.getRed(), colorNoAlpha.getGreen(), colorNoAlpha.getBlue(), alpha).getRGB();
    }

    private void readConfig() {
        showIndicator = config.showIndicator.get();
        showDisabledIndicator = config.showDisabledIndicator.get();
        renderIndicatorBackground = config.renderIndicatorBackground.get();
        indicatorText = config.indicatorText.get();
        indicatorDisabledText = config.indicatorDisabledText.get();
        indicatorTextColor = getColor(config.indicatorTextColor.get());
        indicatorDisabledTextColor = getColor(config.indicatorDisabledTextColor.get());
        indicatorBackgroundColor = getColor(config.indicatorBackgroundColor.get());

        int offsetX = config.indicatorOffsetX.get();
        int offsetY = config.indicatorOffsetY.get();
        String offsetAnchor = config.indicatorOffsetAnchor.get();
        MainWindow mw = mc.mainWindow;
        switch (offsetAnchor.toLowerCase()) {
            case "top right":
                indicatorYPos = offsetY;
                indicatorXPos = mw.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorText) - offsetX;
                indicatorDXPos = mw.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorDisabledText) - offsetX;
                break;
            case "bottom left":
                indicatorXPos = indicatorDXPos = offsetX;
                indicatorYPos = mw.getScaledHeight() - mc.fontRenderer.FONT_HEIGHT - offsetY;
                break;
            case "bottom right":
                indicatorXPos = mw.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorText) - offsetX;
                indicatorDXPos = mw.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorDisabledText) - offsetX;
                indicatorYPos = mw.getScaledHeight() - mc.fontRenderer.FONT_HEIGHT - offsetY;
                break;
            case "top left":
            default:
                indicatorDXPos = indicatorXPos = offsetX;
                indicatorYPos = offsetY;
        }
    }

}
