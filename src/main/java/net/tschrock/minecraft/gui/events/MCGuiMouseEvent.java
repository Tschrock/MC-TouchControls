package net.tschrock.minecraft.gui.events;

public class MCGuiMouseEvent {

	public MCGuiMouseEvent(int mouseX, int mouseY) {
		this(-1, mouseX, mouseY, false);
	}
	
	public MCGuiMouseEvent(int button, int mouseX, int mouseY) {
		this(button, mouseX, mouseY, false);
	}
	
	public MCGuiMouseEvent(int button, int mouseX, int mouseY, boolean emulated) {
		this.button = button;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.emulated = emulated;
	}
	
	protected int mouseX;
	protected int mouseY;
	protected int button;
	protected boolean emulated;
	
	public int getX() {
		return mouseX;
	}
	public int getY() {
		return mouseY;
	}
	public int getButton() {
		return button;
	}
	public boolean isEmulated() {
		return emulated;
	}
	
	
	
}
