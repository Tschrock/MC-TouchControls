package net.tschrock.minecraft.gui.touch;

import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ActionType;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ClickType;
import net.tschrock.minecraft.touchmanager.TouchEvent;

public class TrackedTouchEvent {
	public TrackedTouchEvent(TouchEvent startEvent) {
		this.startEvent = startEvent;
		this.lastEvent = startEvent;
		this.dX = 0;
		this.dY = 0;
	}
	
	protected TouchEvent startEvent;
	protected TouchEvent lastEvent;
	protected int dX;
	protected int dY;
	
	
	public enum TapType {
		UNDETERMINED, TAP, HOLD, DRAG, HOLDDRAG
	}

	protected TapType tapType = TapType.UNDETERMINED;
	
	public TapType getClickType() {
		return tapType;
	}

	public void setClickType(TapType tapType) {
		this.tapType = tapType;
	}
	
	
	
	public TouchEvent getStartEvent() {
		return startEvent;
	}
	public TouchEvent getLastEvent() {
		return lastEvent;
	}
	
	public void addNewEvent(TouchEvent event){
		if(event.getId() == startEvent.getId()){
			dX += event.getX() - lastEvent.getX(); 
			dY += event.getY() - lastEvent.getY(); 
			lastEvent = event;
		}
	}

	public int getDX(){
		int tmpDX = dX;
		dX = 0;
		return tmpDX;
	}

	public int getDY(){
		int tmpDY = dY;
		dY = 0;
		return tmpDY;
	}
}
