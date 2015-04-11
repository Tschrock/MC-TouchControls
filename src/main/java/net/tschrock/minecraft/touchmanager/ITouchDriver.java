package net.tschrock.minecraft.touchmanager;

import java.util.List;

public interface ITouchDriver {
	
	public boolean isNative();
	public boolean hasGlobalFocus();

	//
	//  Event Queue functions
	//

	public TouchEvent getNextTouchEvent();
	public TouchEvent glanceNextTouchEvent();
	public void clearEventQueue();
	public void enableEventQueue();
	public void disableEventQueue();
	public boolean isEventQueueEnabled();
	
	//
	// State Polling functions
	//
	public void resetTouchState();
	public List<TouchEvent> pollTouchState();
}
