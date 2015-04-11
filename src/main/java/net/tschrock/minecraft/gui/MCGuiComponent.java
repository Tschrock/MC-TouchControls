package net.tschrock.minecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
import net.tschrock.minecraft.touchmanager.TouchEvent;

public class MCGuiComponent extends MCGui {

	protected int preferedX;
	protected int preferedY;

	protected int preferedWidth;
	protected int preferedHeight;

	protected int backgroundColor;
	protected int foregroundColor;
	protected int disabledBackgroundColor = argbToColorInt(255, 25, 25, 25);
	protected int disabledForegroundColor = 10526880;

	protected int id;
	protected boolean enabled = true;
	protected boolean visible = true;

	protected IMCGuiContainer parent;
	protected MCGuiScreen parentScreen;

	public MCGuiScreen getParentScreen() {
		return parentScreen;
	}

	public void setParentScreen(MCGuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	public int getPreferedX() {
		return preferedX;
	}

	public void setPreferedX(int preferedX) {
		this.preferedX = preferedX;
	}

	public int getPreferedY() {
		return preferedY;
	}

	public void setPreferedY(int preferedY) {
		this.preferedY = preferedY;
	}

	public int getPreferedWidth() {
		return preferedWidth;
	}

	public void setPreferedWidth(int preferedWidth) {
		this.preferedWidth = preferedWidth;
	}

	public int getPreferedHeight() {
		return preferedHeight;
	}

	public void setPreferedHeight(int preferedHeight) {
		this.preferedHeight = preferedHeight;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(int foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	
	public int getDisabledBackgroundColor() {
		return disabledBackgroundColor;
	}

	public void setDisabledBackgroundColor(int disabledBackgroundColor) {
		this.disabledBackgroundColor = disabledBackgroundColor;
	}

	public int getDisabledForegroundColor() {
		return disabledForegroundColor;
	}

	public void setDisabledForegroundColor(int disabledForegroundColor) {
		this.disabledForegroundColor = disabledForegroundColor;
	}

	public IMCGuiContainer getParent() {
		return parent;
	}

	public void setParent(IMCGuiContainer parent) {
		this.parent = parent;
	}

	public int getId() {
		return id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public MCGuiComponent(int id, int x, int y) {
		this(id, x, y, 100, 25);
	}

	public MCGuiComponent(int id, int x, int y, int width, int height) {
		this(id, x, y, width, height, argbToColorInt(255, 100, 100, 100), 14737632);
	}

	public MCGuiComponent(int id, int x, int y, int width, int height, int backgroundColor, int foregroundColor) {
		this.id = id;
		preferedX = x;	
		preferedY = y;
		preferedWidth = width;
		preferedHeight = height;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
	}

	public void draw(Minecraft mc) {
		if (visible) {
			int bgColor = (enabled) ? backgroundColor : disabledBackgroundColor;
			drawRect(preferedX, preferedY, preferedX + preferedWidth, preferedY + preferedHeight, backgroundColor);
		}
	}

	public boolean checkBounds(int x, int y){
		return x >= preferedX && y >= preferedY && x < preferedX + preferedWidth && y < preferedY + preferedHeight;
	}
	
	public void onFocus() {

	}

	public void onUnfocus() {

	}

	public void onMouseOver(MCGuiMouseEvent event) {

	}
	
	public void onMouseOut(MCGuiMouseEvent event) {

	}

	public void onMouseMove(MCGuiMouseEvent event) {

	}

	public void onMouseDown(MCGuiMouseEvent event) {

	}

	public void onMouseUp(MCGuiMouseEvent event) {

	}

	public void onMouseClick(MCGuiMouseEvent event) {

	}

	public void onKeyPress() {

	}

	public void onTouchEvent(TouchEvent event) {

	}

}
