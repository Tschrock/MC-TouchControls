/*    */ package net.tschrock.minecraft.gui.events;
/*    */ 
/*    */ import net.tschrock.minecraft.gui.touch.MCGuiTouchPad;
/*    */ 
/*    */ public class TouchPadEvent {
/*    */   protected MCGuiTouchPad touchPad;
/*    */   protected net.tschrock.minecraft.gui.touch.TrackedTouchEvent touchEvent;
/*    */   protected Type type;
/*    */   
/* 10 */   public TouchPadEvent(MCGuiTouchPad touchPad, net.tschrock.minecraft.gui.touch.TrackedTouchEvent touchEvent, Type type) { this.touchPad = touchPad;
/* 11 */     this.touchEvent = touchEvent;
/* 12 */     this.type = type;
/*    */   }
/*    */   
/* 15 */   public static enum Type { TAP,  HOLD_START,  HOLD_END,  DRAG_START,  DRAG_UPDATE,  DRAG_END;
/*    */     
/*    */     private Type() {}
/*    */   }
/*    */   
/*    */   public net.tschrock.minecraft.gui.touch.TrackedTouchEvent getTouchEvent() {
/* 21 */     return this.touchEvent;
/*    */   }
/*    */   
/* 24 */   public Type getType() { return this.type; }
/*    */   
/*    */   public MCGuiTouchPad getTouchPad() {
/* 27 */     return this.touchPad;
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/events/TouchPadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */