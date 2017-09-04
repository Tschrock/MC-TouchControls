package net.tschrock.minecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class MCGuiLabel extends MCGuiComponent {
	
	public enum TextAlignment {
		LEFT, CENTER
	}

	protected String text;
	protected FontRenderer fontRenderer;
	protected TextAlignment textAlignment = TextAlignment.CENTER;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

	public void setFontRenderer(FontRenderer fontRenderer) {
		this.fontRenderer = fontRenderer;
	}

	public TextAlignment getTextAlignment() {
		return textAlignment;
	}

	public void setTextAlignment(TextAlignment textAlignment) {
		this.textAlignment = textAlignment;
	}

	public MCGuiLabel(int id, String text, int x, int y) {
		this(id, text, x, y, 100, 25);
	}

	public MCGuiLabel(int id, String text, int x, int y, int width, int height) {
		super(id, x, y, width, height);
		this.text = text;
		this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}

	@Override
	public void draw(Minecraft mc) {
		if (visible) {
			int fgColor = (enabled) ? foregroundColor : disabledForegroundColor; 
			super.draw(mc);
			if (textAlignment == TextAlignment.CENTER) {
				drawCenteredString(fontRenderer, text, preferedX + (preferedWidth / 2), preferedY + (preferedHeight / 2) - (fontRenderer.FONT_HEIGHT / 2), fgColor);
			} else {
				drawString(fontRenderer, text, preferedX, preferedY, fgColor);
			}
		}
	}
}