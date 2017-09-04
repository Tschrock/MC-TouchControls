package net.tschrock.minecraft.touchcontrols;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class FMLEventHandler {

	private boolean oldInGameHasFocus;
	private boolean wasTouchModeEnabled = false;
	private boolean didIGHFchange = false;
	private TouchControlsMod homeMod;

	public FMLEventHandler(TouchControlsMod mod) {
		homeMod = mod;
	}

	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		boolean touchModeEnabled = homeMod.getTouchMode();
		if (event.phase == Phase.START) { // If we are running before the mouse code
			if (touchModeEnabled) { // If touch mode is enabled

				oldInGameHasFocus = mc.inGameHasFocus; // Store the original inGameHasFocus value
				mc.inGameHasFocus = false; // Set inGameHasFocus to false to trick the game into skipping the mouse code
				didIGHFchange = true; // Set didIGHFchange so we know that we messed with inGameHasFocus

			} else if (wasTouchModeEnabled) { // If touch mode was just disabled

				mc.mouseHelper.mouseXYChange(); // Clear the mouse buffer so dx,dy are 0;

			}
			wasTouchModeEnabled = touchModeEnabled; // Record whether or not touch mode was enabled
		}
		if (event.phase == Phase.END && didIGHFchange) { // If we are running after the mouse code AND we messed with inGameHasFocus

			mc.inGameHasFocus = oldInGameHasFocus; // Reset inGameHasFocus to it's original value
			didIGHFchange = false; // Unset didIGHFchange
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.modID.equals("touchcontrols"))
			homeMod.syncConfig();
	}
}