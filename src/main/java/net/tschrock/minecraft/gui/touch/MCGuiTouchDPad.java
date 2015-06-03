package net.tschrock.minecraft.gui.touch;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.tschrock.minecraft.gui.IMCGuiContainer;
import net.tschrock.minecraft.gui.MCGuiButton;
import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
import net.tschrock.minecraft.touchcontrols.DebugHelper;
import net.tschrock.minecraft.touchcontrols.KeyHelper;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.TouchEvent.Type;

public class MCGuiTouchDPad extends MCGuiTouchComponent {

	protected MCGuiButton[] buttons;

	protected boolean beingTouched = false;

	protected TouchEvent currentTouchEvent;
	protected MCGuiButton previousTouch;
	protected MCGuiButton currentTouch;

	protected int padding;

	public MCGuiTouchDPad(int id, int x, int y) {
		this(id, x, y, 128);
	}

	public MCGuiTouchDPad(int id, int x, int y, int width) {
		this(id, x, y, width, argbToColorInt(255, 50, 50, 50), 14737632);
	}

	public MCGuiTouchDPad(int id, int x, int y, int width, int backgroundColor, int foregroundColor) {
		super(id, x, y, width, width, backgroundColor, foregroundColor);
		padding = 3;
		int totPad = padding * 2;
		int buttonWidth = (preferedWidth - totPad) / 3;
		String[] buttonChars = { "◤", "▲", "◥", "◀", "", "▶", "◣", "▼", "◢" };
		buttons = new MCGuiButton[9];

		for (int i = 1; i <= 3; ++i) {
			for (int j = 1; j <= 3; ++j) {
				int index = ((i - 1) * 3) + j - 1;
				int xPos = preferedX + padding + ((j - 1) * buttonWidth);
				int yPos = preferedY + padding + ((i - 1) * buttonWidth);

				if (index == 4) {
					buttons[index] = new MCGuiButton(index, buttonChars[index], xPos + padding, yPos + padding, buttonWidth - (padding * 2),
							buttonWidth - (padding * 2));
				} else {
					buttons[index] = new MCGuiButton(index, buttonChars[index], xPos, yPos, buttonWidth, buttonWidth);
				}

				buttons[index].setEnabled(index % 2 == 1 || index == 4);
			}
		}
	}

	protected float transparency = 0.85F;

	public float getTransparency() {
		return transparency;
	}

	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}

	@Override
	public void draw(Minecraft mc) {

		int dPadState = getCurrentState();
		buttons[0].setEnabled(dPadState == 0 || dPadState == 1 || dPadState == 3);
		buttons[2].setEnabled(dPadState == 2 || dPadState == 1 || dPadState == 5);
		buttons[6].setEnabled(dPadState == 6 || dPadState == 7 || dPadState == 3);
		buttons[8].setEnabled(dPadState == 8 || dPadState == 7 || dPadState == 5);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_DST_COLOR);
		GL14.glBlendColor(0, 0, 0, transparency);

		// Draw background

		super.draw(mc);

		// Draw D-pad Buttons

		for (MCGuiButton btn : buttons) {
			btn.draw(mc);
		}
		GlStateManager.disableBlend();

	}

	@Override
	public void onTouchEvent(TouchEvent event) {

		if (event.getType() == Type.TOUCH_START) {
			if (currentTouchEvent == null) {
				DebugHelper.log(LogLevel.DEBUG, "MCGuiDPad with id=" + id + " recieved 'TOUCH_START' at (" + event.getX() + ", " + event.getY() + ")~[" + event.getAdjustedX(parentScreen.width) + ", " + event.getAdjustedY(parentScreen.height) + "]");
				currentTouchEvent = event;
			}
		} else if (event.getType() == Type.TOUCH_UPDATE) {
			if (currentTouchEvent != null && currentTouchEvent.getId() == event.getId()) {
				DebugHelper.log(LogLevel.DEBUG2, "MCGuiDPad with id=" + id + " recieved 'TOUCH_UPDATE' at (" + event.getX() + ", " + event.getY() + ")~[" + event.getAdjustedX(parentScreen.width) + ", " + event.getAdjustedY(parentScreen.height) + "]");
				currentTouchEvent = event;
			}
		} else if (event.getType() == Type.TOUCH_END) {
			if (currentTouchEvent != null && currentTouchEvent.getId() == event.getId()) {
				DebugHelper.log(LogLevel.DEBUG, "MCGuiDPad with id=" + id + " recieved 'TOUCH_END' at (" + event.getX() + ", " + event.getY() + ")~[" + event.getAdjustedX(parentScreen.width) + ", " + event.getAdjustedY(parentScreen.height) + "]");
				currentTouchEvent = null;
			}
		}

		MCGuiButton overBtn;
		if (currentTouchEvent != null) {
			int x = currentTouchEvent.getAdjustedX(parentScreen.width);
			int y = currentTouchEvent.getAdjustedY(parentScreen.height);
			overBtn = getTopButtonAt(x, y);
			if (overBtn == null && !checkBounds(x, y)) {
				overBtn = buttons[getButtonInDirection(x, y)];
			}
		} else {
			overBtn = null;
		}

		if (currentTouch != overBtn) {
			previousTouch = currentTouch;
			currentTouch = overBtn;

			if (previousTouch != null) {
				previousTouch.onMouseOut(new MCGuiMouseEvent(0, 0));
			}
			if (currentTouch != null) {
				currentTouch.onMouseOver(new MCGuiMouseEvent(0, 0));
			}

		}

	}

	private static final int[] angleMap = { 1, 2, 5, 8, 7, 6, 3, 0 };

	private int getButtonInDirection(int x, int y) {

		double angle = calcRotationAngle(preferedX + (preferedWidth / 2), preferedY + (preferedHeight / 2), x, y);

		double pieceSize = (2 * Math.PI) / 8;

		angle += pieceSize / 2;

		if (angle < 0) {
			angle += Math.PI * 2.0;
		}
		if (angle >=  Math.PI * 2.0) {
			angle -= Math.PI * 2.0;
		}

		int currentPiece = (int) Math.floor(angle / pieceSize);

		return angleMap[currentPiece];
	}

	public MCGuiButton getTopButtonAt(int x, int y) {

		for (int i = buttons.length - 1; i >= 0; --i) {
			MCGuiButton comp = buttons[i];
			if (comp.checkBounds(x, y)) {
				return comp;
			}
		}

		return null;
	}

	public int getCurrentState() {
		return (currentTouch == null) ? ((currentTouchEvent == null) ? -2 : -1) : currentTouch.getId();
	}

	@Override
	public void setPreferedX(int preferedX) {
		if (this.preferedX != preferedX) {
			super.setPreferedX(preferedX);
			updateComponentPositions();
		}
	}

	@Override
	public void setPreferedY(int preferedY) {
		if (this.preferedY != preferedY) {
			super.setPreferedY(preferedY);
			updateComponentPositions();
		}
	}

	@Override
	public void setPreferedHeight(int preferedHeight) {
		if (this.preferedHeight != preferedHeight) {
			super.setPreferedHeight(preferedHeight);
			updateComponentPositions();
		}
	}

	@Override
	public void setPreferedWidth(int preferedWidth) {
		setPreferedHeight(preferedWidth);
	}

	public void updateComponentPositions() {
		padding = 3;
		int totPad = padding * 2;
		int buttonWidth = (preferedWidth - totPad) / 3;

		for (int i = 1; i <= 3; ++i) {
			for (int j = 1; j <= 3; ++j) {
				int index = ((i - 1) * 3) + j - 1;
				int xPos = preferedX + padding + ((j - 1) * buttonWidth);
				int yPos = preferedY + padding + ((i - 1) * buttonWidth);

				if (index == 4) {
					buttons[index].setPreferedX(xPos + padding);
					buttons[index].setPreferedY(yPos + padding);
					buttons[index].setPreferedHeight(buttonWidth - (padding * 2));
					buttons[index].setPreferedWidth(buttonWidth - (padding * 2));
				} else {
					buttons[index].setPreferedX(xPos);
					buttons[index].setPreferedY(yPos);
					buttons[index].setPreferedHeight(buttonWidth);
					buttons[index].setPreferedWidth(buttonWidth);
				}
			}
		}
	}

}
