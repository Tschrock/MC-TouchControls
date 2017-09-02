package net.tschrock.minecraft.gui;

import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;

public class MCGuiToggleButton extends MCGuiButton{

	public MCGuiToggleButton(int id, String text, int x, int y) {
		super(id, text, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public MCGuiToggleButton(int id, String text, int x, int y, int width, int height) {
		super(id, text, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	protected boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void toggle(){
		selected = !selected;
	}
	
	@Override
	public void onMouseClick(MCGuiMouseEvent event) {
		if(checkBounds(event.getX(), event.getY())){
			toggle();
			super.onMouseClick(event);
		}
	}
	
	@Override
	public <T> T getStateValue(T onDisabled, T onMouseDown, T onMouseOver, T onDfault){
		return  (!enabled) ? onDisabled : (mouseDown) ? onMouseDown : (mouseOver || selected) ? onMouseOver	: onDfault;
	}

}
