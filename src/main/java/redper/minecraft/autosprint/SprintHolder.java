package redper.minecraft.autosprint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class SprintHolder {

    private AutoSprint asInstance;
    private int sprintKeyBind;

    public SprintHolder(AutoSprint asInstance) {
        this.asInstance = asInstance;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        sprintKeyBind = Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode();
        KeyBinding.setKeyBindState(sprintKeyBind, asInstance.getState());
    }

    public void disable() {
        KeyBinding.setKeyBindState(sprintKeyBind, false);
    }

}
