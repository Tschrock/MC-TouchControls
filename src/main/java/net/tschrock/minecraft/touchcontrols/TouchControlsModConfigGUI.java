/*    */ package net.tschrock.minecraft.touchcontrols;
/*    */ 
/*    */ import net.minecraftforge.common.config.Configuration;
/*    */ import net.minecraftforge.fml.client.config.GuiConfig;
/*    */ 
/*    */ public class TouchControlsModConfigGUI extends GuiConfig
/*    */ {
/*    */   public TouchControlsModConfigGUI(net.minecraft.client.gui.GuiScreen parent)
/*    */   {
/* 10 */     super(parent, new net.minecraftforge.common.config.ConfigElement(TouchControlsMod.configFile
/* 11 */       .getCategory("general")).getChildElements(), "touchcontrols", false, false, 
/* 12 */       GuiConfig.getAbridgedConfigPath(TouchControlsMod.configFile.toString()));
/*    */   }
/*    */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/touchcontrols/TouchControlsModConfigGUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */