/*     */ package net.tschrock.minecraft.touchmanager;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import net.tschrock.minecraft.touchcontrols.DebugHelper;
/*     */ import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
/*     */ 
/*     */ public abstract class GenericTouchDriver
/*     */   extends Thread
/*     */   implements ITouchDriver
/*     */ {
/*  14 */   protected boolean queueEnabled = false;
/*     */   
/*  16 */   protected ConcurrentLinkedQueue<TouchEvent> eventQueue = new ConcurrentLinkedQueue();
/*     */   
/*  18 */   protected List<TouchEvent> currentTouchState = new ArrayList();
/*     */   
/*     */   protected int findIndexByTouchId(int id) {
/*  21 */     for (int i = 0; i < this.currentTouchState.size(); i++) {
/*  22 */       if (((TouchEvent)this.currentTouchState.get(i)).touchId == id) {
/*  23 */         return i;
/*     */       }
/*     */     }
/*  26 */     return -1;
/*     */   }
/*     */   
/*     */   protected void onNewEvent(TouchEvent event)
/*     */   {
/*  31 */     if (this.queueEnabled) {
/*  32 */       this.eventQueue.add(event);
/*     */     }
/*     */     
/*  35 */     if (event.touchType == TouchEvent.Type.TOUCH_START) {
/*  36 */       DebugHelper.log(DebugHelper.LogLevel.DEBUG1, "Got 'TOUCH_START' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
/*  37 */       this.currentTouchState.add(event);
/*     */     }
/*  39 */     else if (event.touchType == TouchEvent.Type.TOUCH_UPDATE) {
/*  40 */       DebugHelper.log(DebugHelper.LogLevel.DEBUG2, "Got 'TOUCH_UPDATE' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
/*  41 */       int index = findIndexByTouchId(event.touchId);
/*  42 */       if (index != -1) {
/*  43 */         this.currentTouchState.set(index, event);
/*     */       }
/*     */       else {
/*  46 */         DebugHelper.log(DebugHelper.LogLevel.WARNING, "'TOUCH_UPDATE' with id=" + event.getId() + " has no matching start event!");
/*     */       }
/*     */     }
/*  49 */     else if (event.touchType == TouchEvent.Type.TOUCH_END) {
/*  50 */       DebugHelper.log(DebugHelper.LogLevel.DEBUG1, "Got 'TOUCH_END' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
/*  51 */       int index = findIndexByTouchId(event.touchId);
/*  52 */       if (index != -1) {
/*  53 */         this.currentTouchState.remove(index);
/*     */       }
/*     */       else {
/*  56 */         DebugHelper.log(DebugHelper.LogLevel.WARNING, "'TOUCH_END' with id=" + event.getId() + " has no matching start event!");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TouchEvent getNextTouchEvent()
/*     */   {
/*  67 */     return (TouchEvent)this.eventQueue.poll();
/*     */   }
/*     */   
/*     */   public TouchEvent glanceNextTouchEvent() {
/*  71 */     return (TouchEvent)this.eventQueue.peek();
/*     */   }
/*     */   
/*     */   public void clearEventQueue() {
/*  75 */     this.eventQueue.clear();
/*     */   }
/*     */   
/*     */   public void enableEventQueue() {
/*  79 */     clearEventQueue();
/*  80 */     this.queueEnabled = true;
/*  81 */     System.out.println("Queue Enabled");
/*     */   }
/*     */   
/*     */   public void disableEventQueue() {
/*  85 */     this.queueEnabled = false;
/*  86 */     clearEventQueue();
/*  87 */     System.out.println("Queue Disabled");
/*     */   }
/*     */   
/*     */   public boolean isEventQueueEnabled() {
/*  91 */     return this.queueEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetTouchState()
/*     */   {
/*  99 */     this.currentTouchState.clear();
/*     */   }
/*     */   
/*     */   public List<TouchEvent> pollTouchState() {
/* 103 */     return new ArrayList(this.currentTouchState);
/*     */   }
/*     */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/touchmanager/GenericTouchDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */