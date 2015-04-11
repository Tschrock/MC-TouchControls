package net.tschrock.minecraft.touchmanager;

import java.util.List;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;

import net.tschrock.minecraft.touchmanager.drivers.generic.TUIOTouchDriver;
import net.tschrock.minecraft.touchmanager.drivers.linux.X112TUIOTouchDriver;
import net.tschrock.minecraft.touchmanager.drivers.linux.X11TouchDriver;
import net.tschrock.minecraft.touchmanager.drivers.windows.Touch2TUIOTouchDriver;

public class TouchManager {

	protected static ITouchDriver touchDriver = null;
	protected static boolean inited = false;

	private TouchManager() {

	}

	public static void init() {
		init(false);
	}

	public static void init(boolean useGeneric) {
		if (!inited)
			touchDriver = createTouchDriver(useGeneric);
		inited = true;
	}

	private static ITouchDriver createTouchDriver() {
		return createTouchDriver(false);
	}

	private static ITouchDriver createTouchDriver(boolean useGeneric) {
		if (useGeneric)
			return new TUIOTouchDriver();

		switch (LWJGLUtil.getPlatform()) {
		case LWJGLUtil.PLATFORM_LINUX:
			return new X112TUIOTouchDriver();
		case LWJGLUtil.PLATFORM_WINDOWS:
			return new Touch2TUIOTouchDriver();
		case LWJGLUtil.PLATFORM_MACOSX:
			// No touch for OSX :P
		default:
			return new TUIOTouchDriver();
		}
	}

	//
	// Event Queue functions
	//

	public static TouchEvent getNextTouchEvent() {
		return touchDriver.getNextTouchEvent();
	}

	public static TouchEvent glanceNextTouchEvent() {
		return touchDriver.glanceNextTouchEvent();
	}

	public static void clearEventQueue() {
		touchDriver.clearEventQueue();
	}

	public static void enableEventQueue() {
		touchDriver.enableEventQueue();
	}

	public static void disableEventQueue() {
		touchDriver.disableEventQueue();
	}

	public static boolean isEventQueueEnabled() {
		return touchDriver.isEventQueueEnabled();
	}

	//
	// State Polling functions
	//

	public static void resetTouchState() {
		touchDriver.resetTouchState();
	}

	public static List<TouchEvent> pollTouchState() {
		return touchDriver.pollTouchState();
	}

	// Misc

	public static int getXOffset() {
		return touchDriver.hasGlobalFocus() ? Display.getX() : 0;
	}

	public static int getYOffset() {
		return touchDriver.hasGlobalFocus() ? Display.getY() : 0;
	}
}
