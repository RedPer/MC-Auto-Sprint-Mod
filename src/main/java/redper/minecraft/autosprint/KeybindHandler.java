package redper.minecraft.autosprint;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeybindHandler {

    AutoSprint asInstance;
    KeyBinding toggleKeybind;

    public KeybindHandler(AutoSprint asInstance) {
        this.asInstance = asInstance;
        toggleKeybind = new KeyBinding("keybind.toggleAutoSprint", Keyboard.KEY_R, "key.categories.movement");
        ClientRegistry.registerKeyBinding(toggleKeybind);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(toggleKeybind.isPressed()) {
            asInstance.setState(!asInstance.getState());
        }
    }

}
