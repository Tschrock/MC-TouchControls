package net.tschrock.minecraft.gui;

public class MCColorConverter {

	public static int argbToColorInt(int alpha, int red, int green, int blue) {
		return alpha * (16 * 16) * (16 * 16) * (16 * 16) + red * (16 * 16) * (16 * 16) + green * (16 * 16) + blue;
	}
}
