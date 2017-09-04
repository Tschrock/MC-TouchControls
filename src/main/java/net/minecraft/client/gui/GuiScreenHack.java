package net.minecraft.client.gui;

import java.io.IOException;

public class GuiScreenHack{

	public static void callKeyTyped(GuiScreen screen, char typedChar, int keyCode) throws IOException {
		screen.keyTyped(typedChar, keyCode);
	}
	
}