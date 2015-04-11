package net.tschrock.minecraft.gui.events;

import net.tschrock.minecraft.gui.MCGuiButton;

public class MCGuiButtonPushEvent {
	
	protected MCGuiButton button;
	protected int mouseX;
	protected int mouseY;
	
	public MCGuiButton getButton() {
		return button;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public MCGuiButtonPushEvent(MCGuiButton mcGuiButton, int mouseX, int mouseY) {
		button = mcGuiButton;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

}
