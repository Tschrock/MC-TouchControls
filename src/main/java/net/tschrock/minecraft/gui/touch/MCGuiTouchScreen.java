package net.tschrock.minecraft.gui.touch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import scala.collection.parallel.ParIterableLike.Min;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.settings.KeyBinding;
import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.gui.MCGuiScreen;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ActionType;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ClickType;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.TouchEvent.Type;
import net.tschrock.minecraft.touchmanager.TouchManager;

public class MCGuiTouchScreen extends MCGuiScreen {

	protected boolean wasBeingTouched = false;
	protected boolean beingTouched = false;
	protected boolean mouseEmulation = true;

	protected int emulatedClickTimeout = 1000; // ?
	protected int emulatedDragStartRadius = 20; // ? but probably right

	protected int touches = 0;

	protected Map<Integer, MCGuiComponent> activeTouchFocuses = new HashMap<Integer, MCGuiComponent>();

	protected Map<Integer, EmulatedMouseAction> emulatedMouseActions = new HashMap<Integer, EmulatedMouseAction>();

	public void enableMouseEmulation() {
		mouseEmulation = true;
	}

	public void disableMouseEmulation() {
		mouseEmulation = false;
	}

	@Override
	public void handleInput() throws IOException {

		TouchEvent nextEvent;
		while ((nextEvent = TouchManager.getNextTouchEvent()) != null) {
			handleTouchInput(nextEvent);
		}
		beingTouched = (touches != 0);
		super.handleInput();
		wasBeingTouched = beingTouched;
	}

	public void handleTouchInput(TouchEvent event) {

		if (mouseEmulation) {

			MCGuiComponent touchedComp = getTopComponentAt(getAdjustedX(event), getAdjustedY(event));
			EmulatedMouseAction ema = emulatedMouseActions.get(event.getId());

			if (ema != null) {

				if (ema.overComponent != ema.startComponent && touchedComp == ema.startComponent) {
					if (touchedComp != null) {
						touchedComp.onMouseOut(new MCGuiMouseEvent(-1, ema.startTouchEvent.getAdjustedX(width), ema.startTouchEvent
								.getAdjustedY(height), true));
					}
					if (ema.startComponent != null) {
						ema.startComponent.onMouseOver(new MCGuiMouseEvent(-1, getAdjustedX(ema.startTouchEvent), getAdjustedY(ema.startTouchEvent),
								true));
					}
				} else if (ema.overComponent == ema.startComponent && touchedComp != ema.startComponent) {
					if (ema.startComponent != null) {
						ema.startComponent.onMouseOut(new MCGuiMouseEvent(-1, getAdjustedX(ema.startTouchEvent), getAdjustedY(ema.startTouchEvent),
								true));
					}
					if (touchedComp != null) {
						touchedComp.onMouseOver(new MCGuiMouseEvent(-1, getAdjustedX(ema.startTouchEvent), getAdjustedY(ema.startTouchEvent), true));
					}
				}

				ema.setOverComponent(touchedComp);
			}

			if (event.getType() == Type.TOUCH_START) {

				emulatedMouseActions.put(event.getId(), ema = new EmulatedMouseAction(event, Minecraft.getSystemTime(), touchedComp, touchedComp));

				if (touchedComp != null) {
					touchedComp.onMouseOver(new MCGuiMouseEvent(-1, getAdjustedX(ema.startTouchEvent), getAdjustedY(ema.startTouchEvent), true));
				}

			} else if (event.getType() == Type.TOUCH_UPDATE && ema != null) {

				// If time > timeout
				// Set clickType = right
				// Start right click

				if ((Minecraft.getSystemTime() - ema.startTime) > emulatedClickTimeout && ema.clickType == ClickType.UNDETERMINED) {
					ema.clickType = ClickType.RIGHT;
					if (ema.startComponent != null) {
						ema.startComponent.onMouseDown(new MCGuiMouseEvent(1, getAdjustedX(ema.startTouchEvent), getAdjustedY(ema.startTouchEvent),
								true));
					}
				}

				// If position >= radius
				// If clickType = unknown
				// Set clickType = left
				// Start left click
				// If actionType = unknown
				// Set actionType = drag

				int x1 = getAdjustedX(ema.startTouchEvent);
				int y1 = getAdjustedY(ema.startTouchEvent);
				int x2 = getAdjustedX(event);
				int y2 = getAdjustedY(event);

				int distance = (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

				if (distance > emulatedDragStartRadius) {
					if (ema.clickType == ClickType.UNDETERMINED) {
						ema.clickType = ClickType.LEFT;
						if (ema.startComponent != null) {
							ema.startComponent.onMouseDown(new MCGuiMouseEvent(0, getAdjustedX(ema.startTouchEvent),
									getAdjustedY(ema.startTouchEvent), true));
						}
					}
					if (ema.actionType == ActionType.UNDETERMINED) {
						ema.actionType = ActionType.DRAG;
					}
				}

				// If actionType = drag
				// Update position

				if (ema.actionType == ActionType.DRAG) {

					if (ema.startComponent != null) {
						ema.startComponent.onMouseMove(new MCGuiMouseEvent(-1, getAdjustedX(event), getAdjustedY(event), true));
					}
				}

			} else if (event.getType() == Type.TOUCH_END && ema != null) {

				// If clickType = unknown
				// Set clickType = left
				// Start left click

				if (ema.clickType == ClickType.UNDETERMINED) {
					ema.clickType = ClickType.LEFT;

					if (ema.startComponent != null) {
						ema.startComponent.onMouseDown(new MCGuiMouseEvent(0, getAdjustedX(ema.startTouchEvent), getAdjustedY(ema.startTouchEvent),
								true));
					}
				}

				// End click

				if (ema.startComponent != null) {
					sendEMouseUpEvent(ema, event);
					if (ema.startComponent == ema.overComponent) {
						sendEMouseClickEvent(ema, event);
					}
					ema.startComponent
							.onMouseOut(new MCGuiMouseEvent(-1, getAdjustedX(ema.startTouchEvent), getAdjustedY(ema.startTouchEvent), true));
				}

				// Remove from list

				emulatedMouseActions.remove(event.getId());

			}

		}

		if (event.getType() == Type.TOUCH_START) {
			touches++;
			// System.out.println("TouchScreen got touchStartEvent");

			int x = getAdjustedX(event);
			int y = getAdjustedY(event);

			if (x < 0 || y < 0 || x > this.width || y > this.height) {
				System.out.println("TouchStart event OutOfBounds: " + x + ", " + y);
				KeyBinding.unPressAllKeys();
				mc.inGameHasFocus = false;
				mc.mouseHelper.ungrabMouseCursor();
			}

			MCGuiComponent touchedComp = getTopComponentAt(x, y);
			activeTouchFocuses.put(event.getId(), touchedComp);
			if (touchedComp != null) {
				// System.out.println("TouchScreen sent touchStartEvent");
				touchedComp.onTouchEvent(event);
			} else {
				activeTouchFocuses.put(event.getId(), null);
			}
		} else if (event.getType() == Type.TOUCH_UPDATE) {
			MCGuiComponent touchFocus = activeTouchFocuses.get(event.getId());
			if (touchFocus != null) {
				touchFocus.onTouchEvent(event);
			}
		} else if (event.getType() == Type.TOUCH_END) {
			touches--;
			MCGuiComponent touchFocus = activeTouchFocuses.get(event.getId());
			if (touchFocus != null) {
				touchFocus.onTouchEvent(event);
			}
		}

	}

	public void sendEMouseUpEvent(EmulatedMouseAction ema, TouchEvent event) {
		if (ema.clickType == ClickType.UNDETERMINED) {
			return;
		}

		int button = 0;
		if (ema.clickType == ClickType.RIGHT) {
			button = 1;
		}
		ema.startComponent.onMouseUp(new MCGuiMouseEvent(button, getAdjustedX(event), getAdjustedY(event), true));
	}

	public void sendEMouseClickEvent(EmulatedMouseAction ema, TouchEvent event) {
		if (ema.clickType == ClickType.UNDETERMINED) {
			return;
		}

		int button = 0;
		if (ema.clickType == ClickType.RIGHT) {
			button = 1;
		}
		ema.startComponent.onMouseClick(new MCGuiMouseEvent(button, getAdjustedX(event), getAdjustedY(event), true));

	}

	int lastMouseX;
	int lastMouseY;

	@Override
	public void handleMouseInput() throws IOException {
		if (!beingTouched && !wasBeingTouched) {
			lastMouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
			lastMouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
			super.handleMouseInput();
		} else {

			if (Mouse.getEventButtonState()) // If button was involved and it is pressed (Button down)
			{
				downedButtons++;
			} else if (Mouse.getEventButton() != -1) // If button was involved and it isn't pressed (Button up)
			{
				downedButtons--;
			}
		}
	}

	@Override
	public void onGuiClosed() {
		for (MCGuiComponent comp : components) {
			if (comp instanceof MCGuiTouchComponent) {
				((MCGuiTouchComponent) comp).onClearTouchEvents();
			}
		}
	}

	public int getAdjustedX(TouchEvent e) {
		return e.getAdjustedX(width);
	}

	public int getAdjustedY(TouchEvent e) {
		return e.getAdjustedY(height);
	}
}