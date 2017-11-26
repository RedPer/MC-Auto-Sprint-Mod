package redper.minecraft.autosprint.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import redper.minecraft.autosprint.AutoSprintMod;

public class KeyHandler {

    private static KeyHandler instance = new KeyHandler();

    private KeyBinding toggleSprintKey;
    private boolean toggled = false;
    private Minecraft mc;
    private int sprintKey;
    private Configuration config;
    private Property sprintProperty;

    @SideOnly(Side.CLIENT)
    private KeyHandler() {

        config = AutoSprintMod.getInstance().getConfig();
        ConfigCategory cc = config.getCategory(Configuration.CATEGORY_CLIENT);

        if(cc.get("doRememberSprint").getBoolean()) {
            sprintProperty = cc.get("isSprintToggled");
            toggled = sprintProperty.getBoolean();
        }

        toggleSprintKey = new KeyBinding("key.autosprint.toggle", Keyboard.KEY_R, "key.categories.movement");
        ClientRegistry.registerKeyBinding(toggleSprintKey);

        mc = Minecraft.getMinecraft();
        sprintKey = mc.gameSettings.keyBindSprint.getKeyCode();

    }

    public static KeyHandler getInstance() {
        return instance;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTick(ClientTickEvent event) {

        sprintKey = mc.gameSettings.keyBindSprint.getKeyCode();

        if(toggleSprintKey.isPressed()) {

            toggled = !toggled;

            sprintProperty.set(toggled);
            config.save();

            if(!toggled) {
                KeyBinding.setKeyBindState(sprintKey, false);
            }

        }

        if(toggled) {
            KeyBinding.setKeyBindState(sprintKey, true);
        }

    }

    public boolean isToggled() { return toggled; }

}
