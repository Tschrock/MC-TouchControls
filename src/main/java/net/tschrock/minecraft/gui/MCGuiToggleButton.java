/*    */ package net.tschrock.minecraft.gui;
/*    */ 
/*    */ import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
/*    */ 
/*    */ public class MCGuiToggleButton extends MCGuiButton
/*    */ {
/*    */   public MCGuiToggleButton(int id, String text, int x, int y) {
/*  8 */     super(id, text, x, y);
/*    */   }
/*    */   
/*    */   public MCGuiToggleButton(int id, String text, int x, int y, int width, int height) {
/* 12 */     super(id, text, x, y, width, height);
/*    */   }
/*    */   
/* 15 */   protected boolean selected = false;
/*    */   
/*    */   public boolean isSelected() {
/* 18 */     return this.selected;
/*    */   }
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 22 */     this.selected = selected;
/*    */   }
/*    */   
/*    */   public void toggle() {
/* 26 */     this.selected = (!this.selected);
/*    */   }
/*    */   
/*    */   public void onMouseClick(MCGuiMouseEvent event)
/*    */   {
/* 31 */     if (checkBounds(event.getX(), event.getY())) {
/* 32 */       toggle();
/* 33 */       super.onMouseClick(event);
/*    */     }
/*    */   }
/*    */   
/*    */   public <T> T getStateValue(T onDisabled, T onMouseDown, T onMouseOver, T onDfault)
/*    */   {
/* 39 */     return (this.mouseOver) || (this.selected) ? onMouseOver : this.mouseDown ? onMouseDown : !this.enabled ? onDisabled : onDfault;
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/MCGuiToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */