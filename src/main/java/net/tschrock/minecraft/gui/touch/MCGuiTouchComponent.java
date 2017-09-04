/*    */ package net.tschrock.minecraft.gui.touch;
/*    */ 
/*    */ import net.tschrock.minecraft.gui.MCGuiComponent;
/*    */ import net.tschrock.minecraft.touchmanager.TouchEvent;
/*    */ 
/*    */ public class MCGuiTouchComponent extends MCGuiComponent implements IMCGuiTouchable
/*    */ {
/*    */   public MCGuiTouchComponent(int id, int x, int y)
/*    */   {
/* 10 */     super(id, x, y);
/*    */   }
/*    */   
/*    */   public MCGuiTouchComponent(int id, int x, int y, int width, int height) {
/* 14 */     super(id, x, y, width, height);
/*    */   }
/*    */   
/*    */   public MCGuiTouchComponent(int id, int x, int y, int width, int height, int backgroundColor, int foregroundColor) {
/* 18 */     super(id, x, y, width, height, backgroundColor, foregroundColor);
/*    */   }
/*    */   
/*    */   public void onTouchEvent(TouchEvent event) {}
/*    */   
/*    */   public void onClearTouchEvents() {}
/*    */   
/*    */   public void onEMouseOver(int mouseX, int mouseY) {}
/*    */   
/*    */   public void onEMouseOut(int mouseX, int mouseY) {}
/*    */   
/*    */   public void onEMouseMove(int mouseX, int mouseY) {}
/*    */   
/*    */   public void onEMouseDown(int button, int mouseX, int mouseY) {}
/*    */   
/*    */   public void onEMouseUp(int button, int mouseX, int mouseY) {}
/*    */   
/*    */   public void onEMouseClick(int button, int mouseX, int mouseY) {}
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/touch/MCGuiTouchComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */