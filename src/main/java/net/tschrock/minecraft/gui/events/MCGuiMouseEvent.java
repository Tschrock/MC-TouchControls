/*    */ package net.tschrock.minecraft.gui.events;
/*    */ 
/*    */ public class MCGuiMouseEvent { protected int mouseX;
/*    */   protected int mouseY;
/*    */   
/*  6 */   public MCGuiMouseEvent(int mouseX, int mouseY) { this(-1, mouseX, mouseY, false); }
/*    */   
/*    */   public MCGuiMouseEvent(int button, int mouseX, int mouseY)
/*    */   {
/* 10 */     this(button, mouseX, mouseY, false);
/*    */   }
/*    */   
/*    */   public MCGuiMouseEvent(int button, int mouseX, int mouseY, boolean emulated) {
/* 14 */     this.button = button;
/* 15 */     this.mouseX = mouseX;
/* 16 */     this.mouseY = mouseY;
/* 17 */     this.emulated = emulated;
/*    */   }
/*    */   
/*    */ 
/*    */   protected int button;
/*    */   
/*    */   protected boolean emulated;
/*    */   public int getX()
/*    */   {
/* 26 */     return this.mouseX;
/*    */   }
/*    */   
/* 29 */   public int getY() { return this.mouseY; }
/*    */   
/*    */   public int getButton() {
/* 32 */     return this.button;
/*    */   }
/*    */   
/* 35 */   public boolean isEmulated() { return this.emulated; }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/events/MCGuiMouseEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */