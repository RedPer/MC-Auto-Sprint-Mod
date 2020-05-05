package redper.minecraft.autosprint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.lang.reflect.Field;

public class IndicatorRenderer extends Gui {

    private AutoSprint asInstance;
    private Minecraft mc;
    private Field renderEventTypeField;
    private Field configChangedEventModIDField;

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
        mc = Minecraft.getMinecraft();
        try {
            //Ugly workaround to get minecraft 1.8 to work with the same jar
            renderEventTypeField = RenderGameOverlayEvent.class.getDeclaredField("type");
            renderEventTypeField.setAccessible(true);

            configChangedEventModIDField = ConfigChangedEvent.class.getDeclaredField("modID");
            configChangedEventModIDField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Cannot find field", e);
        }

        readConfig();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) throws IllegalAccessException {
        boolean state = asInstance.getState();
        if(renderEventTypeField.get(event) == RenderGameOverlayEvent.ElementType.TEXT && !mc.gameSettings.showDebugInfo && showIndicator && (state || showDisabledIndicator)) {
            String text = state ? indicatorText : indicatorDisabledText;
            int color = state ? indicatorTextColor : indicatorDisabledTextColor;
            int posX = state ? indicatorXPos : indicatorDXPos;
            int posY = indicatorYPos;

            if(renderIndicatorBackground) {
                int height = mc.fontRenderer.FONT_HEIGHT;
                int width = mc.fontRenderer.getStringWidth(text);
                drawRect(posX - 1, posY - 1, posX + width + 1, posY + height + 1, indicatorBackgroundColor);
            }
            drawString(mc.fontRenderer, text, posX, posY, color);
        }
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent event) throws IllegalAccessException {
        if(configChangedEventModIDField.get(event).equals(AutoSprint.MODID)) {
            readConfig();
        }
    }

    private int getColor(String hexColor) {
        Color colorNoAlpha = Color.decode("0x" + hexColor.substring(0, 6));
        int alpha = Integer.parseInt(hexColor.substring(6, 8), 16);
        return new Color(colorNoAlpha.getRed(), colorNoAlpha.getGreen(), colorNoAlpha.getBlue(), alpha).getRGB();
    }

    private void readConfig() {
        ConfigCategory config = asInstance.getClientConfig();
        showIndicator = config.get("showIndicator").getBoolean();
        showDisabledIndicator = config.get("showDisabledIndicator").getBoolean();
        renderIndicatorBackground = config.get("renderIndicatorBackground").getBoolean();
        indicatorText = config.get("indicatorText").getString();
        indicatorDisabledText = config.get("indicatorDisabledText").getString();
        indicatorTextColor = getColor(config.get("indicatorTextColor").getString());
        indicatorDisabledTextColor = getColor(config.get("indicatorDisabledTextColor").getString());
        indicatorBackgroundColor = getColor(config.get("indicatorBackgroundColor").getString());

        int offsetX = config.get("indicatorOffsetX").getInt();
        int offsetY = config.get("indicatorOffsetY").getInt();
        Property offsetAnchor = config.get("indicatorOffsetAnchor");
        ScaledResolution sr = new ScaledResolution(mc);
        switch (offsetAnchor.getString()) {
            case "Top right":
                indicatorYPos = offsetY;
                indicatorXPos = sr.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorText) - offsetX;
                indicatorDXPos = sr.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorDisabledText) - offsetX;
                break;
            case "Bottom left":
                indicatorXPos = indicatorDXPos = offsetX;
                indicatorYPos = sr.getScaledHeight() - mc.fontRenderer.FONT_HEIGHT - offsetY;
                break;
            case "Bottom right":
                indicatorXPos = sr.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorText) - offsetX;
                indicatorDXPos = sr.getScaledWidth() - mc.fontRenderer.getStringWidth(indicatorDisabledText) - offsetX;
                indicatorYPos = sr.getScaledHeight() - mc.fontRenderer.FONT_HEIGHT - offsetY;
                break;
            case "Top left":
            default:
                indicatorDXPos = indicatorXPos = offsetX;
                indicatorYPos = offsetY;
        }
    }

}
