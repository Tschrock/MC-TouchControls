/*    */ package net.tschrock.minecraft.gui.touch;
/*    */ 
/*    */ import net.tschrock.minecraft.touchmanager.TouchEvent;
/*    */ 
/*    */ public class MCGuiTouchJoystick extends MCGuiTouchComponent {
/*    */   protected TouchEvent boundTouch;
/*    */   
/*    */   public MCGuiTouchJoystick(int id, int x, int y) {
/*  9 */     super(id, x, y);
/*    */   }
/*    */   
/*    */   public MCGuiTouchJoystick(int id, int x, int y, int width, int height) {
/* 13 */     super(id, x, y, width, height);
/*    */   }
/*    */   
/*    */   public MCGuiTouchJoystick(int id, int x, int y, int width, int height, int backgroundColor, int foregroundColor) {
/* 17 */     super(id, x, y, width, height, backgroundColor, foregroundColor);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onTouchEvent(TouchEvent event)
/*    */   {
/* 25 */     if ((event.getType() == net.tschrock.minecraft.touchmanager.TouchEvent.Type.TOUCH_START) && (this.boundTouch == null)) {
/* 26 */       this.boundTouch = event;
/*    */     }
/*    */     
/* 29 */     if ((event.getType() == net.tschrock.minecraft.touchmanager.TouchEvent.Type.TOUCH_UPDATE) && (this.boundTouch != null) && (this.boundTouch.getId() == event.getId())) {
/* 30 */       this.boundTouch = event;
/*    */     }
/*    */     
/* 33 */     if ((event.getType() == net.tschrock.minecraft.touchmanager.TouchEvent.Type.TOUCH_END) && (this.boundTouch != null) && (this.boundTouch.getId() == event.getId())) {
/* 34 */       this.boundTouch = null;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void calculateJoystickPosition()
/*    */   {
/* 47 */     if (this.boundTouch == null)
/*    */     {
/* 49 */       int centeredTouchX = this.boundTouch.getAdjustedX(this.parentScreen.width) - this.preferedX - (this.preferedX + this.preferedWidth / 2);
/* 50 */       int j = this.boundTouch.getAdjustedY(this.parentScreen.height) - this.preferedY - (this.preferedY + this.preferedHeight / 2);
/*    */     }
/*    */     else {
/*    */       int y;
/* 54 */       int i = y = 0;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/touch/MCGuiTouchJoystick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */