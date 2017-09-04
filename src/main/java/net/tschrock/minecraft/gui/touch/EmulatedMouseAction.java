/*    */ package net.tschrock.minecraft.gui.touch;
/*    */ 
/*    */ import net.tschrock.minecraft.gui.MCGuiComponent;
/*    */ 
/*    */ public class EmulatedMouseAction
/*    */ {
/*    */   protected net.tschrock.minecraft.touchmanager.TouchEvent startTouchEvent;
/*    */   protected Long startTime;
/*    */   protected MCGuiComponent startComponent;
/*    */   protected MCGuiComponent overComponent;
/*    */   
/*    */   public static enum ActionType {
/* 13 */     UNDETERMINED,  CLICK,  DRAG;
/*    */     
/*    */     private ActionType() {} }
/*    */   
/* 17 */   public static enum ClickType { UNDETERMINED,  LEFT,  RIGHT;
/*    */     
/*    */     private ClickType() {} }
/* 20 */   protected ActionType actionType = ActionType.UNDETERMINED;
/* 21 */   protected ClickType clickType = ClickType.UNDETERMINED;
/*    */   
/*    */   public net.tschrock.minecraft.touchmanager.TouchEvent getStartTouchEvent() {
/* 24 */     return this.startTouchEvent;
/*    */   }
/*    */   
/*    */   public MCGuiComponent getOverComponent() {
/* 28 */     return this.overComponent;
/*    */   }
/*    */   
/*    */   public void setOverComponent(MCGuiComponent overComponent) {
/* 32 */     this.overComponent = overComponent;
/*    */   }
/*    */   
/*    */   public Long getStartTime() {
/* 36 */     return this.startTime;
/*    */   }
/*    */   
/*    */   public MCGuiComponent getStartComponent() {
/* 40 */     return this.startComponent;
/*    */   }
/*    */   
/*    */   public ActionType getActionType() {
/* 44 */     return this.actionType;
/*    */   }
/*    */   
/*    */   public void setActionType(ActionType actionType) {
/* 48 */     this.actionType = actionType;
/*    */   }
/*    */   
/*    */   public ClickType getClickType() {
/* 52 */     return this.clickType;
/*    */   }
/*    */   
/*    */   public void setClickType(ClickType clickType) {
/* 56 */     this.clickType = clickType;
/*    */   }
/*    */   
/*    */   public EmulatedMouseAction(net.tschrock.minecraft.touchmanager.TouchEvent startTouchEvent, Long startTime, MCGuiComponent startComponent, MCGuiComponent overComponent) {
/* 60 */     this.startTouchEvent = startTouchEvent;
/* 61 */     this.startTime = startTime;
/* 62 */     this.startComponent = startComponent;
/* 63 */     this.overComponent = overComponent;
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/touch/EmulatedMouseAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */