package net.tschrock.minecraft.gui.events;

import net.tschrock.minecraft.gui.touch.MCGuiTouchPad;
import net.tschrock.minecraft.gui.touch.TrackedTouchEvent;
import net.tschrock.minecraft.touchmanager.TouchEvent;

public class TouchPadEvent {
	
	public TouchPadEvent(MCGuiTouchPad touchPad, TrackedTouchEvent touchEvent, Type type) {
		this.touchPad = touchPad;
		this.touchEvent = touchEvent;
		this.type = type;
	}
	public enum Type{
		TAP, HOLD_START, HOLD_END, DRAG_START, DRAG_UPDATE, DRAG_END
	}
	protected MCGuiTouchPad touchPad;
	protected TrackedTouchEvent touchEvent;
	protected Type type;
	public TrackedTouchEvent getTouchEvent() {
		return touchEvent;
	}
	public Type getType() {
		return type;
	}
	public MCGuiTouchPad getTouchPad() {
		return touchPad;
	}
	
}
