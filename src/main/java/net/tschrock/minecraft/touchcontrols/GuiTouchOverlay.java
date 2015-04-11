package net.tschrock.minecraft.touchcontrols;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.tschrock.minecraft.gui.MCGui;
import net.tschrock.minecraft.gui.MCGuiButton;
import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.gui.MCGuiLabel;
import net.tschrock.minecraft.gui.MCGuiToggleButton;
import net.tschrock.minecraft.gui.events.IMCGuiButtonPushListener;
import net.tschrock.minecraft.gui.events.IMCGuiTouchPadListener;
import net.tschrock.minecraft.gui.events.MCGuiButtonPushEvent;
import net.tschrock.minecraft.gui.events.TouchPadEvent;
import net.tschrock.minecraft.gui.touch.MCGuiTouchDPad;
import net.tschrock.minecraft.gui.touch.MCGuiTouchPad;
import net.tschrock.minecraft.gui.touch.MCGuiTouchScreen;
import net.tschrock.minecraft.gui.touch.TrackedTouchEvent;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.TouchManager;

public class GuiTouchOverlay extends MCGuiTouchScreen implements IMCGuiButtonPushListener, IMCGuiTouchPadListener {

	MCGuiTouchPad touchPad;
	MCGuiTouchDPad dPad;
	MCGuiButton menuBtn;
	MCGuiButton invBtn;
	MCGuiButton dropBtn;
	MCGuiTouchPad hotbarPad;
	MCGuiButton interactToggleBtn;
	boolean interactToggle = false;

	public GuiTouchOverlay(Minecraft mc) {
		this.mc = mc;
	}

	@Override
	public void initGui() {
		int dPadSize = (TouchControlsMod.config_dPadSize * 3) + 6;

		addComponent(touchPad = new MCGuiTouchPad(0, 0, 0, width, height));
		addComponent(dPad = new MCGuiTouchDPad(2, 4, height - (4 + dPadSize), dPadSize));
		addComponent(menuBtn = new MCGuiButton(3, "Menu", 0, 0, 50, 20));
		addComponent(invBtn = new MCGuiButton(4, "Inventory", 4, dPad.getPreferedY() - 20, dPad.getPreferedWidth(), 20));
		addComponent(dropBtn = new MCGuiButton(5, "Drop Item", 4, dPad.getPreferedY() - 40, dPad.getPreferedWidth(), 20));
		addComponent(hotbarPad = new MCGuiTouchPad(6, (width / 2) - 91, height - 22, 182, 22, 0, MCGui.argbToColorInt(255, 225, 225, 225)));
		addComponent(interactToggleBtn = new MCGuiButton(7, "Attack / Eat", width - 100, height - 20, 100, 20));

		touchPad.registerTouchPadListener(this);
		menuBtn.registerButtonPushListener(this);
		invBtn.registerButtonPushListener(this);
		dropBtn.registerButtonPushListener(this);
		hotbarPad.registerTouchPadListener(this);
		interactToggleBtn.registerButtonPushListener(this);

		hotbarPad.setTouchIndicatorSize(10);

		touchPad.setMultitouch(false);
		hotbarPad.setMultitouch(false);
	}

	public void pauseGui() {
		TouchManager.disableEventQueue();
		touchPad.onClearTouchEvents();
	}

	public void resumeGui() {
		TouchManager.enableEventQueue();
	}

	int oldDPadState;
	boolean showMenu = false;

	@Override
	public void handleInput() throws IOException {

		if (showMenu) {
			showMenu = false;
			KeyHelper.showInGameMenu(mc);
		}

		// Grab touch events and pass them along to handleTouchInput();

		TouchEvent nextEvent;
		while ((nextEvent = TouchManager.getNextTouchEvent()) != null) {
			handleTouchInput(nextEvent);
		}

		// Handle D-pad movements

		int dPadState = dPad.getCurrentState();
		boolean jump = false;

		if (dPadState == -1) {
			dPadState = oldDPadState;
		} else if (dPadState == 4) {
			dPadState = oldDPadState;
			jump = true;
		} else {
			oldDPadState = dPadState;
		}

		if (dPadState == 0 || dPadState == 1 || dPadState == 2) {
			KeyHelper.startMoveForward(mc);
		} else {
			KeyHelper.stopMoveForward(mc);
		}

		if (dPadState == 6 || dPadState == 7 || dPadState == 8) {
			KeyHelper.startMoveBackward(mc);
		} else {
			KeyHelper.stopMoveBackward(mc);
		}

		if (dPadState == 0 || dPadState == 3 || dPadState == 6) {
			KeyHelper.startMoveLeft(mc);
		} else {
			KeyHelper.stopMoveLeft(mc);
		}

		if (dPadState == 2 || dPadState == 5 || dPadState == 8) {
			KeyHelper.startMoveRight(mc);
		} else {
			KeyHelper.stopMoveRight(mc);
		}

		if (dPadState == 4 || jump) {
			KeyHelper.startJump(mc);
		} else {
			KeyHelper.stopJump(mc);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		recalcComponentPos();
		drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Touch Mode", width / 2, height / 2, 0xffff55aa);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void recalcComponentPos() {
		int dPadSize = (TouchControlsMod.config_dPadSize * 3) + 6;
		dPad.setPreferedWidth(dPadSize);
		dPad.setPreferedY(height - (4 + dPadSize));
		dPad.setTransparency(TouchControlsMod.config_dPadOpacity / 100.0F);
		touchPad.setPreferedWidth(width);
		touchPad.setPreferedHeight(height);
		invBtn.setPreferedY(dPad.getPreferedY() - 20);
		invBtn.setPreferedWidth(dPad.getPreferedWidth());
		dropBtn.setPreferedY(dPad.getPreferedY() - 40);
		dropBtn.setPreferedWidth(dPad.getPreferedWidth());
		hotbarPad.setPreferedX((width / 2) - 91);
		hotbarPad.setPreferedY(height - 22);
		interactToggleBtn.setPreferedX(width - 100);
		interactToggleBtn.setPreferedY(height - 20);
		interactToggleBtn.setText(interactToggle ? "Attack / Eat" : "Interact / Break");
	}

	@Override
	public void onButtonPush(MCGuiButtonPushEvent event) {
		switch (event.getButton().getId()) {
		case 3:
			showMenu = true;
			break;
		case 4:
			KeyHelper.showInventoryGui(mc);
			break;
		case 5:
			KeyHelper.dropHeldItem(mc);
			break;
		case 7:
			interactToggle = !interactToggle;
			break;
		default:
			break;
		}

	}

	boolean holdInteractToggle = false;
	
	@Override
	public void onTouchPadEvent(TouchPadEvent e) {
		if (e.getTouchPad() == touchPad) {
			TrackedTouchEvent evt = e.getTouchEvent();
			switch (e.getType()) {
			case TAP:
				if (interactToggle) {
					KeyHelper.click(mc);
				} else {
					KeyHelper.rightClick(mc);
				}
				break;
			case HOLD_START:
				holdInteractToggle = interactToggle;
				if (!holdInteractToggle) {
					KeyHelper.startAttack(mc);
				} else {
					KeyHelper.startUseItem(mc);
				}
				break;
			case HOLD_END:
				if (!holdInteractToggle) {
					KeyHelper.stopAttack(mc);
				} else {
					KeyHelper.stopUseItem(mc);
				}
				break;
			case DRAG_START:
			case DRAG_UPDATE:
			case DRAG_END:
				float dx = evt.getDX();
				float dy = evt.getDY();

				float f1 = (TouchControlsMod.config_sensitivity / 100.0F) * 0.6F + 0.5F;
				float sensitivity = f1 * f1 * f1 * 8.0F;

				dx = (float) dx * sensitivity;
				dy = (float) dy * sensitivity;

				if (TouchControlsMod.config_invertX) {
					dx = -dx;
				}
				if (!TouchControlsMod.config_invertY) {
					dy = -dy;
				}

				mc.thePlayer.setAngles(dx, dy);
				break;
			default:
				break;
			}
		} else if (e.getTouchPad() == hotbarPad) {
			TrackedTouchEvent evt = e.getTouchEvent();

			int eX = evt.getLastEvent().getAdjustedX(width);

			eX -= hotbarPad.getPreferedX();

			if (eX < 0) {
				eX = 0;
			}
			if (eX > 182) {
				eX = 182;
			}

			int eXIndex = (eX + 2) / (182 / 9);

			switch (e.getType()) {
			case TAP:
				KeyHelper.selectHotbarSlot(mc, eXIndex);
				break;
			case HOLD_START:
				break;
			case HOLD_END:
				break;
			case DRAG_START:
			case DRAG_UPDATE:
			case DRAG_END:
				if (mc.thePlayer.inventory.currentItem != eXIndex) {
					KeyHelper.selectHotbarSlot(mc, eXIndex);
				}
				break;
			default:
				break;
			}
		}

	}
}
