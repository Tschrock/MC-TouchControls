/*    */ package net.tschrock.minecraft.touchmanager.drivers.linux;
/*    */ 
/*    */ import net.tschrock.minecraft.touchmanager.BinRunner;
/*    */ import net.tschrock.minecraft.touchmanager.drivers.generic.TUIOTouchDriver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class X112TUIOTouchDriver
/*    */   extends TUIOTouchDriver
/*    */ {
/*    */   BinRunner procEx;
/* 16 */   Process proc = null;
/*    */   
/*    */   public X112TUIOTouchDriver()
/*    */   {
/* 20 */     Thread closeChildThread = new Thread() {
/*    */       public void run() {
/* 22 */         X112TUIOTouchDriver.this.proc.destroy();
/*    */       }
/*    */       
/* 25 */     };
/* 26 */     Runtime.getRuntime().addShutdownHook(closeChildThread);
/*    */     
/* 28 */     this.procEx = new BinRunner("bin/x112tuio");
/* 29 */     this.proc = this.procEx.extractAndRun();
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/touchmanager/drivers/linux/X112TUIOTouchDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */