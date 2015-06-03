package net.tschrock.minecraft.touchmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.tschrock.minecraft.touchcontrols.DebugHelper;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
import net.tschrock.minecraft.touchmanager.TouchEvent.Type;


public abstract class GenericTouchDriver extends Thread implements ITouchDriver {

	protected boolean queueEnabled = false;
	
	protected ConcurrentLinkedQueue<TouchEvent> eventQueue = new ConcurrentLinkedQueue<TouchEvent>();
	
	protected List<TouchEvent> currentTouchState = new ArrayList<TouchEvent>();
	
	protected int findIndexByTouchId(int id){
		for(int i = 0; i < currentTouchState.size(); ++i){
			if(currentTouchState.get(i).touchId == id){
				return i;
			}
		}
		return -1;
	}
	
	protected void onNewEvent(TouchEvent event){
		
		if(queueEnabled){
			eventQueue.add(event);
		}
		
		if (event.touchType == Type.TOUCH_START) {
			DebugHelper.log(LogLevel.DEBUG1, "Got 'TOUCH_START' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
			currentTouchState.add(event);
		}
		else if (event.touchType == Type.TOUCH_UPDATE){
			DebugHelper.log(LogLevel.DEBUG2, "Got 'TOUCH_UPDATE' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
			int index = this.findIndexByTouchId(event.touchId);
			if(index != -1){
				currentTouchState.set(index, event);
			}
			else {
				DebugHelper.log(LogLevel.WARNING, "'TOUCH_UPDATE' with id=" + event.getId() + " has no matching start event!");
			}
		}
		else if (event.touchType == Type.TOUCH_END){
				DebugHelper.log(LogLevel.DEBUG1, "Got 'TOUCH_END' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
			int index = this.findIndexByTouchId(event.touchId);
			if(index != -1){
				currentTouchState.remove(index);
			}
			else {
				DebugHelper.log(LogLevel.WARNING, "'TOUCH_END' with id=" + event.getId() + " has no matching start event!");
			}
		}
		
	}
	
	//
	//  Event Queue functions
	//

	public TouchEvent getNextTouchEvent(){
		return eventQueue.poll();
	}
	
	public TouchEvent glanceNextTouchEvent(){
		return eventQueue.peek();
	}
	
	public void clearEventQueue(){
		eventQueue.clear();
	}
	
	public void enableEventQueue(){
		this.clearEventQueue();
		this.queueEnabled = true;
		System.out.println("Queue Enabled");
	}
	
	public void disableEventQueue(){
		this.queueEnabled = false;
		this.clearEventQueue();
		System.out.println("Queue Disabled");
	}
	
	public boolean isEventQueueEnabled(){
		return this.queueEnabled;
	}
	
	//
	// State Polling functions
	//
	
	public void resetTouchState(){
		currentTouchState.clear();
	}
	
	public List<TouchEvent> pollTouchState(){
		return new ArrayList<TouchEvent>(currentTouchState);
	}

	
	
}
