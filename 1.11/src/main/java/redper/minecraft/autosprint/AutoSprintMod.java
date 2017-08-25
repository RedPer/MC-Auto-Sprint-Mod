package redper.minecraft.autosprint;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import redper.minecraft.autosprint.handler.KeyHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSIONS, clientSideOnly = true, canBeDeactivated = true)
public class AutoSprintMod {

    @Mod.Instance(Reference.MOD_ID)
    private static AutoSprintMod instance;

    public AutoSprintMod() {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        if(event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new KeyHandler());
        }

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

}
