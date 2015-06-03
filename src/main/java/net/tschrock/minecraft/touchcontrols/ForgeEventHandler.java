package net.tschrock.minecraft.touchcontrols;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenHack;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.tschrock.minecraft.gui.MCGuiButton;
import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.gui.MCGuiTestScreen;
import net.tschrock.minecraft.gui.events.IMCGuiButtonPushListener;
import net.tschrock.minecraft.gui.events.MCGuiButtonPushEvent;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction;
import net.tschrock.minecraft.gui.touch.MCGuiTouchComponent;
import net.tschrock.minecraft.gui.touch.MCGuiTouchDPad;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ActionType;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ClickType;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.TouchManager;
import net.tschrock.minecraft.touchmanager.TouchEvent.Type;

public class ForgeEventHandler implements IMCGuiButtonPushListener {

	private TouchControlsMod homeMod;
	private boolean oldCanDraw = false;
	private boolean oldTouchMode = false;
	protected GuiTouchOverlay overlay;

	public static int width;
	public static int height;

	MCGuiButton escapeBtn;

	public ForgeEventHandler(TouchControlsMod mod) {
		homeMod = mod;
		overlay = new GuiTouchOverlay(Minecraft.getMinecraft());
		overlay.initGui();

		escapeBtn = new MCGuiButton(0, "Esc", 0, 0, 50, 20);
		escapeBtn.registerButtonPushListener(this);
	}

	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		if (event.type == ElementType.ALL) {

			width = event.resolution.getScaledWidth();
			height = event.resolution.getScaledHeight();

			overlay.width = width;
			overlay.height = height;

			Minecraft mc = Minecraft.getMinecraft();
			boolean canDraw = homeMod.getTouchMode() && mc.currentScreen == null;
			if (!oldCanDraw && canDraw) {
				overlay.resumeGui();
				DebugHelper.log(LogLevel.INFO, "Showing touch screen gui.");
			} else if (oldCanDraw && !canDraw) {
				overlay.pauseGui();
				DebugHelper.log(LogLevel.INFO, "Hiding touch screen gui.");
			}
			oldCanDraw = canDraw;
			
			if(!oldTouchMode && homeMod.getTouchMode())
				DebugHelper.logTouchAndWindowInfo();
			oldTouchMode = homeMod.getTouchMode();

			if (canDraw) {
				try {
					overlay.handleInput();
					Mouse.setGrabbed(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
				overlay.drawScreen(0, 0, event.partialTicks);
			}
		}
	}

	@SubscribeEvent
	public void onMouse(MouseEvent event) {
		if (homeMod.getTouchMode()) {
			event.setCanceled(true);
		}
	}

	boolean escapePressed = false;
	
	@SubscribeEvent
	public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
		if(escapePressed){
			escapePressed = false;
			try {
				GuiScreenHack.callKeyTyped(Minecraft.getMinecraft().currentScreen, '\0', 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (homeMod.getTouchMode()) {
			escapeBtn.draw(Minecraft.getMinecraft());
			if (escapeBtn.checkBounds(event.mouseX, event.mouseY)) {
				if (!escapeBtn.isMouseOver()) {
					escapeBtn.onMouseOver(new MCGuiMouseEvent(event.mouseX, event.mouseY));
				}

				if (Mouse.isButtonDown(0)) {

					if (!escapeBtn.isMouseDown()) {
						escapeBtn.onMouseDown(new MCGuiMouseEvent(0, event.mouseX, event.mouseY));
					}

				} else {
					if (escapeBtn.isMouseDown()) {
						escapeBtn.onMouseUp(new MCGuiMouseEvent(0, event.mouseX, event.mouseY));
						escapeBtn.onMouseClick(new MCGuiMouseEvent(0, event.mouseX, event.mouseY));
					}
				}

			} else {
				if (escapeBtn.isMouseOver()) {
					escapeBtn.onMouseOut(new MCGuiMouseEvent(event.mouseX, event.mouseY));
				}
			}

			if (!Mouse.isButtonDown(0)) {
				if (escapeBtn.isMouseDown()) {
					escapeBtn.onMouseUp(new MCGuiMouseEvent(0, event.mouseX, event.mouseY));
				}
			}
		}
	}

	@Override
	public void onButtonPush(MCGuiButtonPushEvent event) {
		System.out.println("onButtonPush");
		if (event.getButton() == escapeBtn) {
			escapePressed = true;
		}
	}
}
