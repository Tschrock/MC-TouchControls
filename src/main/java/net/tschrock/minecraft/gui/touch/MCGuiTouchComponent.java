package net.tschrock.minecraft.gui.touch;

import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.touchmanager.TouchEvent;

public class MCGuiTouchComponent extends MCGuiComponent implements IMCGuiTouchable {

	
	public MCGuiTouchComponent(int id, int x, int y) {
		super(id, x, y);
	}

	public MCGuiTouchComponent(int id, int x, int y, int width, int height) {
		super(id, x, y, width, height);
	}
	
	public MCGuiTouchComponent(int id, int x, int y, int width, int height, int backgroundColor, int foregroundColor) {
		super(id, x, y, width, height, backgroundColor, foregroundColor);
	}

	public void onTouchEvent(TouchEvent event) {}
	public void onClearTouchEvents() {}
	
	public void onEMouseOver(int mouseX, int mouseY){}
	public void onEMouseOut(int mouseX, int mouseY){}
	public void onEMouseMove(int mouseX, int mouseY){}
	public void onEMouseDown(int button, int mouseX, int mouseY){}
	public void onEMouseUp(int button, int mouseX, int mouseY){}
	public void onEMouseClick(int button, int mouseX, int mouseY){}
}
