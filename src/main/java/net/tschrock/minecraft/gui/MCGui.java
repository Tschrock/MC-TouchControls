package net.tschrock.minecraft.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class MCGui extends Gui {

	public static int argbToColorInt(int alpha, int red, int green, int blue) {
		return alpha * (16 * 16) * (16 * 16) * (16 * 16) + red * (16 * 16) * (16 * 16) + green * (16 * 16) + blue;
	}

	public void drawCircle(float x1, float y1, float radius, int num_segments, int color) {

		double theta = 2 * 3.1415926 / (double) num_segments;
		float c = (float) Math.cos(theta); // precalculate the sine and cosine
		float s = (float) Math.sin(theta);
		float t;

		float x = radius;// we start at angle = 0
		float y = 0;

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;

		int j1;

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		//GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		//GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.startDrawing(GL11.GL_TRIANGLE_FAN);

		for (int ii = 0; ii < num_segments; ii++) {

			worldrenderer.addVertex((double) x + x1, (double) y + y1, 0.0D);

			// apply the rotation matrix
			t = x;
			x = c * x + s * y;
			y = -s * t + c * y;
		}

		tessellator.draw();
		GlStateManager.enableTexture2D();
		//GlStateManager.disableBlend();

	}
	public void drawCircleOutline(float x1, float y1, float radius, int num_segments, int color) {

		double theta = 2 * 3.1415926 / (double) num_segments;
		float c = (float) Math.cos(theta); // precalculate the sine and cosine
		float s = (float) Math.sin(theta);
		float t;

		float x = radius;// we start at angle = 0
		float y = 0;

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;

		int j1;

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		//GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		//GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.startDrawing(GL11.GL_LINE_LOOP);

		for (int ii = 0; ii < num_segments; ii++) {

			worldrenderer.addVertex((double) x + x1, (double) y + y1, 0.0D);

			// apply the rotation matrix
			t = x;
			x = c * x + s * y;
			y = -s * t + c * y;
		}

		tessellator.draw();
		GlStateManager.enableTexture2D();
		//GlStateManager.disableBlend();

	}

	public class TextureLink1D {
		public int x;
		public int textureX;
		public int width;

		public TextureLink1D(int x, int textureX, int width) {
			this.x = x;
			this.textureX = textureX;
			this.width = width;
		}
	}

	public void drawBorderedTexturedModalRect(int x, int y, int width, int height, int textureX, int textureY, int textureWidth, int textureHeight,
			int borderSize) {

		TextureLink1D[] xSections = sliceBorderedTexturedRectDim(x, width, textureX, textureWidth, borderSize);
		TextureLink1D[] ySections = sliceBorderedTexturedRectDim(y, height, textureY, textureHeight, borderSize);
		// System.out.println(ySections[0].textureX);

		for (TextureLink1D xSect : xSections) {
			for (TextureLink1D ySect : ySections) {
				drawTexturedModalRect(xSect, ySect);// new TextureLink1D(y, textureY, textureHeight));
			}
		}

	}

	public void drawTexturedModalRect(TextureLink1D xLink, TextureLink1D yLink) {
		drawTexturedModalRect(xLink.x, yLink.x, xLink.textureX, yLink.textureX, xLink.width, yLink.width);
	}

	public TextureLink1D[] sliceBorderedTexturedRectDim(int x, int width, int textureX, int textureWidth, int borderSize) {

		boolean simpleX = width < (textureWidth - borderSize) * 2;
		TextureLink1D[] xSections;

		if (simpleX) {

			xSections = new TextureLink1D[2];
			xSections[0] = new TextureLink1D(x, textureX, width / 2);
			xSections[1] = new TextureLink1D(x + width / 2, (textureX + textureWidth) - width / 2, width / 2);

		} else {

			int maxMidSize = textureWidth - (borderSize * 2);
			int neededMidSize = width - (borderSize * 2);
			int neededMidSections = (int) Math.ceil((float) neededMidSize / (float) maxMidSize);
			xSections = new TextureLink1D[2 + neededMidSections];

			xSections[0] = new TextureLink1D(x, textureX, borderSize);

			for (int i = 1; i <= neededMidSections; ++i) {

				int midSize = (i == neededMidSections) ? neededMidSize - (maxMidSize * (neededMidSections - 1)) : maxMidSize;
				xSections[i] = new TextureLink1D(x + borderSize + ((i - 1) * maxMidSize), textureX + borderSize, midSize);

			}

			xSections[xSections.length - 1] = new TextureLink1D((x + width) - borderSize, (textureX + textureWidth) - borderSize, borderSize);

		}
		return xSections;
	}

	public static double calcRotationAngle(int centerX, int centerY, int targetX, int targetY) {
		double theta = Math.atan2(targetY - centerY, targetX - centerX);
		theta += Math.PI / 2.0;

		if (theta < 0) {
			theta += Math.PI * 2.0;
		}

		return theta;
	}
}