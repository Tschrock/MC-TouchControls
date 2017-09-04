/*    */ package net.tschrock.minecraft.gui.touch;
/*    */ 
/*    */ import net.tschrock.minecraft.touchmanager.TouchEvent;
/*    */ 
/*    */ public class TrackedTouchEvent {
/*    */   protected TouchEvent startEvent;
/*    */   protected TouchEvent lastEvent;
/*    */   
/*  9 */   public TrackedTouchEvent(TouchEvent startEvent) { this.startEvent = startEvent;
/* 10 */     this.lastEvent = startEvent;
/* 11 */     this.dX = 0;
/* 12 */     this.dY = 0;
/*    */   }
/*    */   
/*    */ 
/*    */   protected int dX;
/*    */   
/*    */   protected int dY;
/*    */   
/*    */   public static enum TapType
/*    */   {
/* 22 */     UNDETERMINED,  TAP,  HOLD,  DRAG,  HOLDDRAG;
/*    */     
/*    */     private TapType() {} }
/* 25 */   protected TapType tapType = TapType.UNDETERMINED;
/*    */   
/*    */   public TapType getClickType() {
/* 28 */     return this.tapType;
/*    */   }
/*    */   
/*    */   public void setClickType(TapType tapType) {
/* 32 */     this.tapType = tapType;
/*    */   }
/*    */   
/*    */ 
/*    */   public TouchEvent getStartEvent()
/*    */   {
/* 38 */     return this.startEvent;
/*    */   }
/*    */   
/* 41 */   public TouchEvent getLastEvent() { return this.lastEvent; }
/*    */   
/*    */   public void addNewEvent(TouchEvent event)
/*    */   {
/* 45 */     if (event.getId() == this.startEvent.getId()) {
/* 46 */       this.dX += event.getX() - this.lastEvent.getX();
/* 47 */       this.dY += event.getY() - this.lastEvent.getY();
/* 48 */       this.lastEvent = event;
/*    */     }
/*    */   }
/*    */   
/*    */   public int getDX() {
/* 53 */     int tmpDX = this.dX;
/* 54 */     this.dX = 0;
/* 55 */     return tmpDX;
/*    */   }
/*    */   
/*    */   public int getDY() {
/* 59 */     int tmpDY = this.dY;
/* 60 */     this.dY = 0;
/* 61 */     return tmpDY;
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/touch/TrackedTouchEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */