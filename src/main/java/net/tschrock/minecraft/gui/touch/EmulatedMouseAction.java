package net.tschrock.minecraft.gui.touch;

import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.touchmanager.TouchEvent;

public class EmulatedMouseAction {
	protected TouchEvent startTouchEvent;
	protected Long startTime;
	protected MCGuiComponent startComponent;
	protected MCGuiComponent overComponent;
	
	public enum ActionType {
		UNDETERMINED, CLICK, DRAG
	}

	public enum ClickType {
		UNDETERMINED, LEFT, RIGHT
	}
	
	protected ActionType actionType = ActionType.UNDETERMINED;
	protected ClickType clickType = ClickType.UNDETERMINED;

	public TouchEvent getStartTouchEvent() {
		return startTouchEvent;
	}

	public MCGuiComponent getOverComponent() {
		return overComponent;
	}

	public void setOverComponent(MCGuiComponent overComponent) {
		this.overComponent = overComponent;
	}

	public Long getStartTime() {
		return startTime;
	}

	public MCGuiComponent getStartComponent() {
		return startComponent;
	}
	
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public ClickType getClickType() {
		return clickType;
	}

	public void setClickType(ClickType clickType) {
		this.clickType = clickType;
	}

	public EmulatedMouseAction(TouchEvent startTouchEvent, Long startTime, MCGuiComponent startComponent, MCGuiComponent overComponent) {
		this.startTouchEvent = startTouchEvent;
		this.startTime = startTime;
		this.startComponent = startComponent;
		this.overComponent = overComponent;
	}
}
