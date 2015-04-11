package net.tschrock.minecraft.touchmanager;

import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.tschrock.minecraft.touchcontrols.TouchControlsMod;

public class TouchEvent {

	public enum Type {
		TOUCH_START, TOUCH_UPDATE, TOUCH_END, TOUCH_STATE, UNKNOWN
	}

	protected Type touchType;
	protected int touchId;
	protected int touchX;
	protected int touchY;
	protected long touchTime;

	public Type getType() {
		return touchType;
	}

	public int getId() {
		return touchId;
	}

	public int getX() {
		return touchX;
	}

	public int getY() {
		return touchY;
	}

	public long getTime() {
		return touchTime;
	}

	public TouchEvent(Type touchType, int touchId, int touchX, int touchY) {
		super();
		this.touchType = touchType;
		this.touchId = touchId;
		this.touchX = touchX;
		this.touchY = touchY;
		this.touchTime = Minecraft.getSystemTime();
	}

	public int getAdjustedX(int width){
		return (this.touchX - TouchManager.getXOffset() + TouchControlsMod.config_xOffset) * width / Display.getWidth();
	}
	
	public int getAdjustedY(int height){
		return (this.touchY - TouchManager.getYOffset() + TouchControlsMod.config_yOffset) * height / Display.getHeight();
	}

}