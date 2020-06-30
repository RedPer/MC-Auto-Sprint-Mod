package redper.minecraft.autosprint116;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import redper.minecraft.autosprint116.AutoSprint;

public class SprintHolder {

    private AutoSprint asInstance;
    private InputMappings.Input sprintKeyBind;

    public SprintHolder(AutoSprint asInstance) {
        this.asInstance = asInstance;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        sprintKeyBind = Minecraft.getInstance().gameSettings.keyBindSprint.getKey();
        KeyBinding.setKeyBindState(sprintKeyBind, asInstance.getState());
    }

    public void disable() {
        KeyBinding.setKeyBindState(sprintKeyBind, false);
    }

}
