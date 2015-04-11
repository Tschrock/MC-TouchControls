package net.tschrock.minecraft.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;

public class MCGuiScreen extends GuiScreen implements IMCGuiContainer {

	protected List<MCGuiComponent> components = new ArrayList<MCGuiComponent>();
	protected MCGuiComponent focusedComponent;
	protected MCGuiComponent mouseOverComponent;
	protected int downedButtons = 0;

	public void addComponent(MCGuiComponent component) {
		components.add(component);
		component.setParent(this);
		component.setParentScreen(this);
	}

	public void removeComponent(MCGuiComponent component) {
		component.setParent(null);
		component.setParentScreen(null);
		components.remove(component);
	}

	public MCGuiComponent getComponentById(int id) {

		for (MCGuiComponent comp : components) {
			if (comp.id == id) {
				return comp;
			}
		}

		return null;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for (MCGuiComponent comp : components) {
			comp.draw(mc);
		}
	}

	@Override
	public void handleInput() throws IOException {
		super.handleInput();
	}

	@Override
	public void handleKeyboardInput() throws IOException {
		super.handleKeyboardInput();
	}

	@Override
	public void handleMouseInput() throws IOException {

		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		// System.out.println(y);
		int button = Mouse.getEventButton();
		MCGuiComponent overComponent = getTopComponentAt(x, y);

		if (overComponent != null && overComponent != focusedComponent) {
			overComponent.onMouseMove(new MCGuiMouseEvent(x, y));
			// System.out.println("overComp MouseMove");
		}
		if (focusedComponent != null) {
			focusedComponent.onMouseMove(new MCGuiMouseEvent(x, y));
			// System.out.println("focusedComp MouseMove");
		}
		if (mouseOverComponent != overComponent) {
			if (mouseOverComponent != null) {
				mouseOverComponent.onMouseOut(new MCGuiMouseEvent(x, y));
			}
			if (overComponent != null) {
				overComponent.onMouseOver(new MCGuiMouseEvent(x, y));
			}
		}

		mouseOverComponent = overComponent;

		if (Mouse.getEventButtonState()) // If button was involved and it is pressed (Button down)
		{
			if (downedButtons == 0) {
				focusedComponent = overComponent;
			}
			downedButtons++;
			if (focusedComponent != null) {
				focusedComponent.onMouseDown(new MCGuiMouseEvent(button, x, y));
			}
		} else if (button != -1) // If button was involved and it isn't pressed (Button up)
		{
			downedButtons--;
			if (focusedComponent != null) {
				focusedComponent.onMouseUp(new MCGuiMouseEvent(button, x, y));
			}
			if (overComponent != null && overComponent == focusedComponent) {
				focusedComponent.onMouseClick(new MCGuiMouseEvent(button, x, y));
			}

		}

	}

	public MCGuiComponent getTopComponentAt(int x, int y) {

		for (int i = components.size() - 1; i >= 0; --i) {
			MCGuiComponent comp = components.get(i);
			if (comp.checkBounds(x, y)) {
				return comp;
			}
		}

		return null;
	}
}
