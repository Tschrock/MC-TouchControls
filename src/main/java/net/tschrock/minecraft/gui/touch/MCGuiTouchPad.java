package net.tschrock.minecraft.gui.touch;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.tschrock.minecraft.gui.MCGuiButton;
import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.gui.events.IMCGuiButtonPushListener;
import net.tschrock.minecraft.gui.events.IMCGuiTouchPadListener;
import net.tschrock.minecraft.gui.events.MCGuiButtonPushEvent;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
import net.tschrock.minecraft.gui.events.TouchPadEvent;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ActionType;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ClickType;
import net.tschrock.minecraft.gui.touch.TrackedTouchEvent.TapType;
import net.tschrock.minecraft.touchcontrols.DebugHelper;
import net.tschrock.minecraft.touchcontrols.TouchControlsMod;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.TouchEvent.Type;

public class MCGuiTouchPad extends MCGuiTouchComponent {

	protected boolean showTouches = true;
	protected boolean multitouch = true;
	protected int touchIndicatorSize = 20;

	protected int topTouch = -1;
	protected Map<Integer, TrackedTouchEvent> activeTouches = new HashMap<Integer, TrackedTouchEvent>();

	private List<IMCGuiTouchPadListener> touchPadListeners = new ArrayList<IMCGuiTouchPadListener>();

	public void registerTouchPadListener(IMCGuiTouchPadListener pushListener) {
		touchPadListeners.add(pushListener);
	}

	public void unregisterTouchPadListener(IMCGuiTouchPadListener pushListener) {
		touchPadListeners.remove(pushListener);
	}

	public boolean isShowTouches() {
		return showTouches;
	}

	public void setShowTouches(boolean showTouches) {
		this.showTouches = showTouches;
	}

	public boolean isMultitouch() {
		return multitouch;
	}

	public void setMultitouch(boolean multitouch) {
		this.multitouch = multitouch;
	}

	public int getTouchIndicatorSize() {
		return touchIndicatorSize;
	}

	public void setTouchIndicatorSize(int touchIndicatorSize) {
		this.touchIndicatorSize = touchIndicatorSize;
	}

	public MCGuiTouchPad(int id, int x, int y) {
		this(id, x, y, 200, 200);
	}

	public MCGuiTouchPad(int id, int x, int y, int width, int height) {
		this(id, x, y, width, height, argbToColorInt(0, 25, 25, 25), argbToColorInt(255, 225, 225, 225));
	}

	public MCGuiTouchPad(int id, int x, int y, int width, int height, int backgroundColor, int foregroundColor) {
		super(id, x, y, width, height, backgroundColor, foregroundColor);
	}

	@Override
	public void onTouchEvent(TouchEvent event) {

		if (event.getType() == Type.TOUCH_START && !(!this.multitouch && activeTouches.size() > 0)) {
			DebugHelper.log(LogLevel.DEBUG, "MCGuiTouchpad with id=" + id + " recieved 'TOUCH_START' at (" + event.getX() + ", " + event.getY() + ")~[" + event.getAdjustedX(parentScreen.width) + ", " + event.getAdjustedY(parentScreen.height) + "]");
			activeTouches.put(event.getId(), new TrackedTouchEvent(event));
			if (topTouch == -1) {
				topTouch = event.getId();
			}
		} else if (event.getType() == Type.TOUCH_UPDATE) {
			DebugHelper.log(LogLevel.DEBUG2, "MCGuiTouchpad with id=" + id + " recieved 'TOUCH_UPDATE' at (" + event.getX() + ", " + event.getY() + ")~[" + event.getAdjustedX(parentScreen.width) + ", " + event.getAdjustedY(parentScreen.height) + "]");
			TrackedTouchEvent evt = activeTouches.get(event.getId());
			if (evt != null) {
				evt.addNewEvent(event);

				// If position >= radius
				// If clickType = unknown
				// Set clickType = left
				// Start left click
				// If actionType = unknown
				// Set actionType = drag

				int x1 = evt.startEvent.getAdjustedX(parentScreen.width);
				int y1 = evt.startEvent.getAdjustedY(parentScreen.height);
				int x2 = event.getAdjustedX(parentScreen.width);
				int y2 = event.getAdjustedY(parentScreen.height);

				int distance = (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

				if (distance > TouchControlsMod.config_dragStartRadius) {
					if (evt.tapType == TapType.UNDETERMINED) {
						evt.tapType = TapType.DRAG;
						fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.DRAG_START));
					} else if (evt.tapType == TapType.HOLD) {
						evt.tapType = TapType.HOLDDRAG;
						fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.DRAG_START));
					}
				}

				fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.DRAG_UPDATE));

			}
		} else if (event.getType() == Type.TOUCH_END) {
			TrackedTouchEvent evt = activeTouches.get(event.getId());
			if (evt != null) {
				DebugHelper.log(LogLevel.DEBUG, "MCGuiTouchpad with id=" + id + " recieved 'TOUCH_END' at (" + event.getX() + ", " + event.getY() + ")~[" + event.getAdjustedX(parentScreen.width) + ", " + event.getAdjustedY(parentScreen.height) + "]");
				evt.addNewEvent(event);

				// If clickType = unknown
				// Set clickType = left
				// Start left click

				if (evt.tapType == TapType.UNDETERMINED && (Minecraft.getSystemTime() - evt.startEvent.getTime()) < TouchControlsMod.config_leftClickTimeout) {
					evt.tapType = TapType.TAP;
					fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.TAP));
				}

				// End click

				if (evt.tapType == TapType.HOLD) {
					fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.HOLD_END));
				} else if (evt.tapType == TapType.DRAG) {
					fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.DRAG_END));
				} else if (evt.tapType == TapType.HOLDDRAG) {
					fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.HOLD_END));
					fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.DRAG_END));
				}
			}

			activeTouches.remove(event.getId());
			if (topTouch == event.getId()) {
				topTouch = -1;
			}
		}

	}

	public List<TrackedTouchEvent> getAllCurrentTouches() {
		return new ArrayList<TrackedTouchEvent>(activeTouches.values());
	}

	public TrackedTouchEvent getTopCurrentTouch() {
		return activeTouches.get(topTouch);
	}

	@Override
	public void draw(Minecraft mc) {
		for (Entry<Integer, TrackedTouchEvent> tTouchEvent : activeTouches.entrySet()) {
			TrackedTouchEvent evt = tTouchEvent.getValue();
			if ((Minecraft.getSystemTime() - evt.startEvent.getTime()) > TouchControlsMod.config_rightClickTimeout
					&& evt.tapType == TapType.UNDETERMINED) {
				evt.tapType = TapType.HOLD;
				fireTouchEvent(new TouchPadEvent(this, evt, TouchPadEvent.Type.HOLD_START));
			}
		}
		
		if (visible) {

			if (showTouches) {
				int x, y;
				for (Map.Entry<Integer, TrackedTouchEvent> activeTouch : activeTouches.entrySet()) {
					x = activeTouch.getValue().getLastEvent().getAdjustedX(parentScreen.width);
					y = activeTouch.getValue().getLastEvent().getAdjustedY(parentScreen.height);
					// int time = (int) (Minecraft.getSystemTime() - activeTouch.getValue().getStartEvent().getTouchTime());

					GlStateManager.enableBlend();
					GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_DST_COLOR);
					GL14.glBlendColor(0, 0, 0, 0.9F);

					drawCircle(x, y, touchIndicatorSize - 1, 20, argbToColorInt(125, 75, 75, 75));
					GlStateManager.disableBlend();
					GL11.glEnable(GL11.GL_LINE_SMOOTH);
					GL11.glLineWidth(3);
					drawCircleOutline(x, y, touchIndicatorSize, 20, foregroundColor);
					GL11.glDisable(GL11.GL_LINE_SMOOTH);
					// drawCircle(x, y, 5.0F + (time/200.0F), 20, foregroundColor);

				}
			}
		}
	}

	@Override
	public void onClearTouchEvents() {
		activeTouches.clear();
	}

	protected void fireTouchEvent(TouchPadEvent touchPadEvent) {
		for (IMCGuiTouchPadListener listener : touchPadListeners) {
			listener.onTouchPadEvent(touchPadEvent);
		}
	}

}
