package net.tschrock.minecraft.touchcontrols;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.tschrock.minecraft.gui.MCGuiTestScreen;

import org.lwjgl.input.Keyboard;

public class KeyHandler {


	/** Key index for easy handling */
	public static final int TOUCH_TOGGLE = 0;
	public static final int TOUCH_SETTING = 1;
	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = { "key.tut_inventory.desc" , "key.tut_sett.desc"};
	/** Default key values */
	private static final int[] keyValues = { Keyboard.KEY_P, Keyboard.KEY_O};
	private final KeyBinding[] keys;

	TouchControlsMod homeMod;

	public KeyHandler(TouchControlsMod mod) {
		homeMod = mod;
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i],
					"key.tutorial.category");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}

	/**
	 * KeyInputEvent is in the FML package, so we must register to the FML event
	 * bus
	 */
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		// FMLClientHandler.instance().getClient().inGameHasFocus
		if (keys[TOUCH_TOGGLE].isPressed()) {
			homeMod.toggleTouchMode();
			//Minecraft.getMinecraft().displayGuiScreen(new MCGuiTestScreen());
		}
		if (keys[TOUCH_SETTING].isPressed()) {
			//homeMod.toggleTouchMode();
			Minecraft.getMinecraft().displayGuiScreen(new MCGuiTestScreen());
		}
	}
}