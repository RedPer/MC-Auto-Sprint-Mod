package redper.minecraft.autosprint.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;

public class KeyHandler {

    private KeyBinding toggleSprintKey;
    private boolean toggled = false;
    private Minecraft mc;
    private int sprintKey;

    public KeyHandler() {

        toggleSprintKey = new KeyBinding("key.autosprint.toggle", Keyboard.KEY_R, "key.categories.movement");
        ClientRegistry.registerKeyBinding(toggleSprintKey);

        mc = Minecraft.getMinecraft();
        sprintKey = mc.gameSettings.keyBindSprint.getKeyCode();

    }

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {

        sprintKey = mc.gameSettings.keyBindSprint.getKeyCode();

        if(toggleSprintKey.isPressed()) {

            toggled = !toggled;

            if(!toggled) {
                KeyBinding.setKeyBindState(sprintKey, false);
            }

        }

        if(toggled) {
            KeyBinding.setKeyBindState(sprintKey, true);
        }

    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {

        if(toggled && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {

            int color = Integer.parseInt("aaffaa", 16);
            mc.fontRendererObj.drawString("Sprint [Toggled]", 5, 5, color);

        }

    }

}
