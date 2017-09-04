/*    */ package net.tschrock.minecraft.touchmanager.drivers.windows;
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
/*    */ public class Touch2TUIOTouchDriver
/*    */   extends TUIOTouchDriver
/*    */ {
/*    */   BinRunner procEx;
/*    */   BinRunner procDll;
/* 17 */   Process proc = null;
/*    */   
/*    */   public boolean hasGlobalFocus()
/*    */   {
/* 21 */     return true;
/*    */   }
/*    */   
/*    */   public Touch2TUIOTouchDriver()
/*    */   {
/* 26 */     Thread closeChildThread = new Thread() {
/*    */       public void run() {
/* 28 */         Touch2TUIOTouchDriver.this.proc.destroy();
/*    */       }
/*    */       
/* 31 */     };
/* 32 */     Runtime.getRuntime().addShutdownHook(closeChildThread);
/*    */     
/* 34 */     boolean is64bit = false;
/* 35 */     if (System.getProperty("os.name").contains("Windows")) {
/* 36 */       is64bit = System.getenv("ProgramFiles(x86)") != null;
/*    */     } else {
/* 38 */       is64bit = System.getProperty("os.arch").indexOf("64") != -1;
/*    */     }
/*    */     
/* 41 */     if (is64bit) {
/* 42 */       this.procDll = new BinRunner("bin/TouchHook.dll");
/* 43 */       this.procEx = new BinRunner("bin/Touch2Tuio.exe", " Minecraft");
/*    */     } else {
/* 45 */       this.procDll = new BinRunner("bin/TouchHook_x64.dll");
/* 46 */       this.procEx = new BinRunner("bin/Touch2Tuio_x64.exe", " Minecraft");
/*    */     }
/*    */     
/* 49 */     this.procDll.extract();
/* 50 */     this.proc = this.procEx.extractAndRun();
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/touchmanager/drivers/windows/Touch2TUIOTouchDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */