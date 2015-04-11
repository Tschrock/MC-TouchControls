package net.tschrock.minecraft.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.TextAlignment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.tschrock.minecraft.gui.events.IMCGuiButtonPushListener;
import net.tschrock.minecraft.gui.events.MCGuiButtonPushEvent;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;

public class MCGuiButton extends MCGuiLabel {

	protected static final ResourceLocation defaultButtonTextures = new ResourceLocation("textures/gui/widgets.png");
	protected static final String defaultButtonSoundLocation = "gui.button.press";

	private List<IMCGuiButtonPushListener> buttonPushListeners = new ArrayList<IMCGuiButtonPushListener>();

	public void registerButtonPushListener(IMCGuiButtonPushListener pushListener) {
		buttonPushListeners.add(pushListener);
	}

	public void unregisterButtonPushListener(IMCGuiButtonPushListener pushListener) {
		buttonPushListeners.remove(pushListener);
	}

	protected ResourceLocation buttonTextures = defaultButtonTextures;
	protected String buttonSoundLocation = defaultButtonSoundLocation;

	protected int hoverBackgroundColor;
	protected int mouseDownBackgroundColor;

	protected int hoverForegroundColor = 16777120;
	protected int mouseDownForegroundColor;

	public ResourceLocation getButtonTextures() {
		return buttonTextures;
	}

	public void setButtonTextures(ResourceLocation buttonTextures) {
		this.buttonTextures = buttonTextures;
	}

	public String getButtonSoundLocation() {
		return buttonSoundLocation;
	}

	public void setButtonSoundLocation(String buttonSoundLocation) {
		this.buttonSoundLocation = buttonSoundLocation;
	}

	public int getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}

	public void setHoverBackgroundColor(int hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}

	public int getMouseDownBackgroundColor() {
		return mouseDownBackgroundColor;
	}

	public void setMouseDownBackgroundColor(int mouseDownBackgroundColor) {
		this.mouseDownBackgroundColor = mouseDownBackgroundColor;
	}

	public int getHoverForegroundColor() {
		return hoverForegroundColor;
	}

	public void setHoverForegroundColor(int hoverForegroundColor) {
		this.hoverForegroundColor = hoverForegroundColor;
	}

	public int getMouseDownForegroundColor() {
		return mouseDownForegroundColor;
	}

	public void setMouseDownForegroundColor(int mouseDownForegroundColor) {
		this.mouseDownForegroundColor = mouseDownForegroundColor;
	}

	public MCGuiButton(int id, String text, int x, int y) {
		super(id, text, x, y);
	}

	public MCGuiButton(int id, String text, int x, int y, int width, int height) {
		super(id, text, x, y, width, height);
	}

	protected boolean mouseDown = false;
	protected boolean mouseOver = false;

	public boolean isMouseDown() {
		return mouseDown;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	@Override
	public void onMouseDown(MCGuiMouseEvent event) {
		System.out.println("onMouseDown: " + event.getButton());
		if (event.getButton() == 0) {
			mouseDown = true;
		}
	}

	@Override
	public void onMouseOver(MCGuiMouseEvent event) {
		//System.out.println("onMouseOver");
		mouseOver = true;
	}

	@Override
	public void onMouseOut(MCGuiMouseEvent event) {
		//System.out.println("onMouseOut");
		mouseOver = false;
	}

	@Override
	public void onMouseUp(MCGuiMouseEvent event) {
		System.out.println("onMouseUp: " + event.getButton());
		if (event.getButton() == 0) {
			mouseDown = false;
		}
	};

	@Override
	public void onMouseClick(MCGuiMouseEvent event) {
		//System.out.println("onMouseClick");
		if (enabled && visible && event.getButton() == 0 && checkBounds(event.getX(), event.getY())) {
			if (buttonSoundLocation != null) {
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation(buttonSoundLocation), 1.0F));
			}
			fireButtonPushEvent(new MCGuiButtonPushEvent(this, event.getX(), event.getY()));
		}
	}

	protected void fireButtonPushEvent(MCGuiButtonPushEvent event) {
		for (IMCGuiButtonPushListener listener : buttonPushListeners) {
			listener.onButtonPush(event);
		}
	}

	@Override
	public void draw(Minecraft mc) {
		if (visible) {

			// Draw button background
			if (buttonTextures != null) {
				mc.getTextureManager().bindTexture(buttonTextures);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				int k = getStateValue(0, 2, 2, 1);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.blendFunc(770, 771);
				// this.drawTexturedModalRect(preferedX, preferedY, 0, 46 + k * 20, preferedWidth / 2, preferedHeight);
				// this.drawTexturedModalRect(preferedX + preferedWidth / 2, preferedY, 200 - preferedWidth / 2, 46 + k * 20, preferedWidth / 2,
				// preferedHeight);

				this.drawBorderedTexturedModalRect(preferedX, preferedY, preferedWidth, preferedHeight, 0, 46 + k * 20, 200, 20, 3);

			} else {
				int bgColor = getStateValue(disabledBackgroundColor, mouseDownBackgroundColor, hoverBackgroundColor, backgroundColor);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.blendFunc(770, 771);
				this.drawRect(preferedX, preferedY, preferedX + preferedWidth, preferedY + preferedHeight, bgColor);
			}

			// Draw button text
			int fgColor = getStateValue(disabledForegroundColor, mouseDownForegroundColor, hoverForegroundColor, foregroundColor);
			if (textAlignment == TextAlignment.CENTER) {
				drawCenteredString(fontRenderer, text, preferedX + (preferedWidth / 2), preferedY + (preferedHeight / 2)
						- (fontRenderer.FONT_HEIGHT / 2), fgColor);
			} else {
				drawString(fontRenderer, text, preferedX, preferedY, fgColor);
			}
		}
	}

	public <T> T getStateValue(T onDisabled, T onMouseDown, T onMouseOver, T onDfault) {
		return (!enabled) ? onDisabled : (mouseDown) ? onMouseDown : (mouseOver) ? onMouseOver : onDfault;
	}

}
