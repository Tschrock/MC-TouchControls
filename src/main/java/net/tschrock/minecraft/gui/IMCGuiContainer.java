package net.tschrock.minecraft.gui;

public abstract interface IMCGuiContainer
{
  public abstract void addComponent(MCGuiComponent paramMCGuiComponent);
  
  public abstract void removeComponent(MCGuiComponent paramMCGuiComponent);
  
  public abstract MCGuiComponent getComponentById(int paramInt);
  
  public abstract MCGuiComponent getTopComponentAt(int paramInt1, int paramInt2);
}


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/gui/IMCGuiContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */