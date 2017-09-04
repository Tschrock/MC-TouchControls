package net.tschrock.minecraft.touchcontrols;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.ReportedException;

import org.lwjgl.input.Keyboard;

import scala.collection.parallel.ParIterableLike.Min;

public class KeyHelper {

	public static void showInGameMenu(Minecraft mc) {
		mc.displayInGameMenu();
		mc.setIngameNotInFocus();
	}

	public static void clearChat(Minecraft mc) {
		if (mc.ingameGUI != null) {
			mc.ingameGUI.getChatGUI().clearChatMessages();
		}
	}

	public static void reloadResources(Minecraft mc) {
		mc.refreshResources();
	}

	public static void incrementRenderDistance(Minecraft mc) {
		mc.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, 1);
	}

	public static void decrementRenderDistance(Minecraft mc) {
		mc.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, -1);
	}

	public static void reloadRenderers(Minecraft mc) {
		mc.renderGlobal.loadRenderers();
	}

	public static void toggleAdvancedTooltips(Minecraft mc) {
		mc.gameSettings.advancedItemTooltips = !mc.gameSettings.advancedItemTooltips;
		mc.gameSettings.saveOptions();
	}

	public static void toggleDebugBoundingBoxes(Minecraft mc) {
		mc.getRenderManager().setDebugBoundingBox(!mc.getRenderManager().isDebugBoundingBox());
	}

	public static void togglePauseOnLostFocus(Minecraft mc) {
		mc.gameSettings.pauseOnLostFocus = !mc.gameSettings.pauseOnLostFocus;
		mc.gameSettings.saveOptions();
	}

	public static void hideGUI(Minecraft mc) {
		mc.gameSettings.hideGUI = !mc.gameSettings.hideGUI;
	}

	public static void toggleDebugScreen(Minecraft mc) {
		toggleDebugScreen(mc, false);
	}

	public static void toggleDebugScreen(Minecraft mc, boolean showProfiler) {
		mc.gameSettings.showDebugInfo = !mc.gameSettings.showDebugInfo;
		mc.gameSettings.showDebugProfilerChart = showProfiler;
	}

	public static void cycleThirdPersonView(Minecraft mc) {
		++mc.gameSettings.thirdPersonView;

		if (mc.gameSettings.thirdPersonView > 2) {
			mc.gameSettings.thirdPersonView = 0;
		}

		if (mc.gameSettings.thirdPersonView == 0) {
			mc.entityRenderer.loadEntityShader(mc.getRenderViewEntity());
		} else if (mc.gameSettings.thirdPersonView == 1) {
			mc.entityRenderer.loadEntityShader((Entity) null);
		}
	}

	public static void setThirdPersonView(Minecraft mc, int view) {
		if (view > 2 || view < 0) {
			view = 0;
		}

		mc.gameSettings.thirdPersonView = view;

		if (mc.gameSettings.thirdPersonView == 0) {
			mc.entityRenderer.loadEntityShader(mc.getRenderViewEntity());
		} else if (mc.gameSettings.thirdPersonView == 1) {
			mc.entityRenderer.loadEntityShader((Entity) null);
		}
	}

	public static void toggleSmoothCamera(Minecraft mc) {
		mc.gameSettings.smoothCamera = !mc.gameSettings.smoothCamera;
	}

	public static void selectHotbarSlot(Minecraft mc, int slot) {

		slot = slot < 9 ? slot : 0;
		slot = slot > 0 ? slot : 0;

		if (mc.thePlayer.isSpectator()) {
			mc.ingameGUI.func_175187_g().func_175260_a(slot);
		} else {
			mc.thePlayer.inventory.currentItem = slot;
		}
	}

	public static void showInventoryGui(Minecraft mc) {
		if (mc.playerController.isRidingHorse()) {
			mc.thePlayer.sendHorseInventory();
		} else {
			mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
			mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
		}
	}

	public static void dropHeldItem(Minecraft mc) {
		dropHeldItem(mc, false);
	}

	public static void dropHeldItem(Minecraft mc, boolean dropAll) {
		if (!mc.thePlayer.isSpectator()) {
			mc.thePlayer.dropOneItem(dropAll);
		}
	}

	public static void showChatGui(Minecraft mc) {
		mc.displayGuiScreen(new GuiChat());
	}

	public static void showCommandGui(Minecraft mc) {
		mc.displayGuiScreen(new GuiChat("/"));
	}

	// Kinda guessing at these, hope they work :P

	// These directly modify key binding states instead of using method calls
	// because I'm not actualy sure how the methods work...

	// Use Item
	// mc.rightClickMouse() ?
	public static void startUseItem(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
	}

	public static void stopUseItem(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
	}

	// Attack
	// mc.clickMouse() ?
	public static void startAttack(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
	}

	public static void stopAttack(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
	}

	// PickBlock
	// mc.middleClickMouse() ?
	public static void startPickBlock(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
	}

	public static void stopPickBlock(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
	}

	// These directly modify key binding states instead of using method calls
	// because I don't want to have to extend MovementInput and overwrite EntityPlayerSP.movementInput

	// Move forward
	// MovementInput.moveForward++
	public static void startMoveForward(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
	}

	public static void stopMoveForward(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
	}

	// Move backward
	// MovementInput.moveForward--
	public static void startMoveBackward(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
	}

	public static void stopMoveBackward(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
	}

	// Move Left
	// MovementInput.moveStrafe++
	public static void startMoveLeft(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
	}

	public static void stopMoveLeft(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
	}

	// Move Right
	// MovementInput.moveStrafe--
	public static void startMoveRight(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
	}

	public static void stopMoveRight(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
	}

	// Jump
	// MovementInput.jump
	public static void startJump(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
	}

	public static void stopJump(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
	}

	// Sneak
	// MovementInput.sneak
	public static void startSneak(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
	}

	public static void stopSneak(Minecraft mc) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
	}

	// Does reflection even work once it's compiled/obfuscated?

	public static void rightClick(Minecraft mc) {
		tryRunMethods(mc, new String[]{"rightClickMouse", "func_147121_ag"});
	}

	public static void click(Minecraft mc) {
		tryRunMethods(mc, new String[]{"clickMouse", "func_147116_af"});
	}
	
	
	public static Object tryRunMethods(Object o, String[] methodNames){
		Method method = null;
		for (String name : methodNames) {
			if ((method = tryGetMethod(o, name)) != null) break;
		}
		if (method != null) {
			try {
				method.setAccessible(true);
				return method.invoke(o);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Method tryGetMethod(Object o, String methodName) {
		try {
			return o.getClass().getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}