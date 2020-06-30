package redper.minecraft.autosprint115;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {

    AutoSprint asInstance;
    KeyBinding toggleKeybind;

    public KeybindHandler(AutoSprint asInstance) {
        this.asInstance = asInstance;
        toggleKeybind = new KeyBinding("keybind.toggleAutoSprint", GLFW.GLFW_KEY_R, "key.categories.movement");
        ClientRegistry.registerKeyBinding(toggleKeybind);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(toggleKeybind.isPressed()) {
            asInstance.setState(!asInstance.getState());
        }
    }

}
