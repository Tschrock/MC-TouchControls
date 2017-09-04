/*    */ package net.tschrock.minecraft.gui.events;
/*    */ 
/*    */ import net.tschrock.minecraft.gui.MCGuiButton;
/*    */ 
/*    */ public class MCGuiButtonPushEvent
/*    */ {
/*    */   protected MCGuiButton button;
/*    */   protected int mouseX;
/*    */   protected int mouseY;
/*    */   
/*    */   public MCGuiButton getButton() {
/* 12 */     return this.button;
/*    */   }
/*    */   
/*    */   public int getMouseX() {
/* 16 */     return this.mouseX;
/*    */   }
/*    */   
/*    */   public int getMouseY() {
/* 20 */     return this.mouseY;
/*    */   }
/*    */   
/*    */   public MCGuiButtonPushEvent(MCGuiButton mcGuiButton, int mouseX, int mouseY) {
/* 24 */     this.button = mcGuiButton;
/* 25 */     this.mouseX = mouseX;
/* 26 */     this.mouseY = mouseY;
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/events/MCGuiButtonPushEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */