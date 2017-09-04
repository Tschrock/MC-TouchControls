package net.tschrock.minecraft.touchmanager;

import java.util.List;

public abstract interface ITouchDriver
{
  public abstract boolean isNative();
  
  public abstract boolean hasGlobalFocus();
  
  public abstract TouchEvent getNextTouchEvent();
  
  public abstract TouchEvent glanceNextTouchEvent();
  
  public abstract void clearEventQueue();
  
  public abstract void enableEventQueue();
  
  public abstract void disableEventQueue();
  
  public abstract boolean isEventQueueEnabled();
  
  public abstract void resetTouchState();
  
  public abstract List<TouchEvent> pollTouchState();
}


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/touchmanager/ITouchDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */