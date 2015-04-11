package net.tschrock.minecraft.gui;

public interface IMCGuiContainer {

	public void addComponent(MCGuiComponent component);
	public void removeComponent(MCGuiComponent component);
	
	public MCGuiComponent getComponentById(int id);
	
	public MCGuiComponent getTopComponentAt(int x, int y);
}
