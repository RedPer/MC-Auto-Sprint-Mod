package redper.minecraft.autosprint115;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AutoSprintConfig {

    public final ForgeConfigSpec.ConfigValue<Boolean> autoSprintState;
    public final ForgeConfigSpec.ConfigValue<Boolean> rememberAutoSprintState;
    public final ForgeConfigSpec.ConfigValue<Boolean> showIndicator;
    public final ForgeConfigSpec.ConfigValue<Boolean> showDisabledIndicator;
    public final ForgeConfigSpec.ConfigValue<Boolean> renderIndicatorBackground;
    public final ForgeConfigSpec.ConfigValue<String> indicatorText;
    public final ForgeConfigSpec.ConfigValue<String> indicatorDisabledText;
    public final ForgeConfigSpec.ConfigValue<String> indicatorTextColor;
    public final ForgeConfigSpec.ConfigValue<String> indicatorDisabledTextColor;
    public final ForgeConfigSpec.ConfigValue<String> indicatorBackgroundColor;
    public final ForgeConfigSpec.ConfigValue<String> indicatorOffsetAnchor;
    public final ForgeConfigSpec.ConfigValue<Integer> indicatorOffsetX;
    public final ForgeConfigSpec.ConfigValue<Integer> indicatorOffsetY;

    private final ForgeConfigSpec configSpec;

    public AutoSprintConfig() {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

        configBuilder.push("client");
        autoSprintState = configBuilder.comment("Stores the last state of AutoSprint.").define("autoSprintState", false);
        rememberAutoSprintState = configBuilder.comment("Determines whether the AutoSprint state will be restored upon game startup.").translation("config.rememberState").define("rememberAutoSprintState", true);
        showIndicator = configBuilder.comment("Determines whether an indicator with the mod state will be displayed.").translation("config.showIndicator").define("showIndicator", true);
        showDisabledIndicator = configBuilder.comment("Determines whether to show the indicator even if the mod is disabled.").translation("config.showDisabledIndicator").define("showDisabledIndicator", false);
        renderIndicatorBackground = configBuilder.comment("Determines whether to render a rectangular background behind the indicator.").translation("config.renderIndicatorBackground").define("renderIndicatorBackground", true);
        indicatorText = configBuilder.comment("The text that will be shown on the indicator when the mod is enabled.").translation("config.indicatorText").define("indicatorText", "AutoSprint Enabled");
        indicatorDisabledText = configBuilder.comment("The text that will be shown on the indicator when the mod is disabled.").translation("config.indicatorDisabledText").define("indicatorDisabledText", "AutoSprint Disabled");
        indicatorTextColor = configBuilder.comment("Color of the text on the indicator when the mod is enabled (stored as a hex value).").translation("config.indicatorTextColor").define("indicatorTextColor", "ffffffff");
        indicatorDisabledTextColor = configBuilder.comment("Color of the text on the indicator when the mod is disabled (stored as a hex value).").translation("config.indicatorDisabledTextColor").define("indicatorDisabledTextColor", "ff0000ff");
        indicatorBackgroundColor = configBuilder.comment("Color of the indicator background (stored as a hex value).").translation("config.indicatorBackgroundColor").define("indicatorBackgroundColor", "17233150");
        indicatorOffsetAnchor = configBuilder.comment("Specifies on which side of the screen the indicator displays. Allowed values: Top left, Top right, Bottom left, Bottom right (Case insensitive)").translation("config.indicatorOffsetAnchor").define("indicatorOffsetAnchor", "Top left");
        indicatorOffsetX = configBuilder.comment("The offset from the corner of the screen (Axis X).").translation("config.indicatorOffsetX").define("indicatorOffsetX", 5);
        indicatorOffsetY = configBuilder.comment("The offset from the corner of the screen (Axis Y).").translation("config.indicatorOffsetY").define("indicatorOffsetY", 5);
        configBuilder.pop();

        configSpec = configBuilder.build();
    }

    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }
}
