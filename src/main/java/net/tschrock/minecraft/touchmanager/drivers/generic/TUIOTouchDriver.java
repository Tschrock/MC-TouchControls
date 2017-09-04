/*    */ package net.tschrock.minecraft.touchmanager.drivers.generic;
/*    */ 
/*    */ import TUIO.TuioBlob;
/*    */ import TUIO.TuioClient;
/*    */ import TUIO.TuioCursor;
/*    */ import TUIO.TuioObject;
/*    */ import TUIO.TuioTime;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import net.tschrock.minecraft.touchmanager.TouchEvent;
/*    */ import net.tschrock.minecraft.touchmanager.TouchEvent.Type;
/*    */ 
/*    */ public class TUIOTouchDriver extends net.tschrock.minecraft.touchmanager.GenericTouchDriver implements TUIO.TuioListener
/*    */ {
/*    */   protected TuioClient tClient;
/*    */   
/*    */   public TUIOTouchDriver()
/*    */   {
/* 19 */     this.tClient = new TuioClient();
/* 20 */     this.tClient.addTuioListener(this);
/* 21 */     this.tClient.connect();
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isNative()
/*    */   {
/* 27 */     return false;
/*    */   }
/*    */   
/* 30 */   public boolean hasGlobalFocus() { return true; }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addTuioBlob(TuioBlob arg0) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public void addTuioCursor(TuioCursor tCursor)
/*    */   {
/* 41 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 42 */     int width = (int)screenSize.getWidth();
/* 43 */     int height = (int)screenSize.getHeight();
/*    */     
/* 45 */     onNewEvent(new TouchEvent(TouchEvent.Type.TOUCH_START, tCursor.getCursorID(), tCursor.getScreenX(width), tCursor.getScreenY(height)));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addTuioObject(TuioObject arg0) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void refresh(TuioTime arg0) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void removeTuioBlob(TuioBlob arg0) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void removeTuioCursor(TuioCursor tCursor)
/*    */   {
/* 68 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 69 */     int width = (int)screenSize.getWidth();
/* 70 */     int height = (int)screenSize.getHeight();
/* 71 */     onNewEvent(new TouchEvent(TouchEvent.Type.TOUCH_END, tCursor.getCursorID(), tCursor.getScreenX(width), tCursor.getScreenY(height)));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void removeTuioObject(TuioObject arg0) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void updateTuioBlob(TuioBlob arg0) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void updateTuioCursor(TuioCursor tCursor)
/*    */   {
/* 89 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 90 */     int width = (int)screenSize.getWidth();
/* 91 */     int height = (int)screenSize.getHeight();
/* 92 */     onNewEvent(new TouchEvent(TouchEvent.Type.TOUCH_UPDATE, tCursor.getCursorID(), tCursor.getScreenX(width), tCursor.getScreenY(height)));
/*    */   }
/*    */   
/*    */   public void updateTuioObject(TuioObject arg0) {}
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/touchmanager/drivers/generic/TUIOTouchDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */