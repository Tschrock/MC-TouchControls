package net.tschrock.minecraft.touchcontrols;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.lwjgl.opengl.Display;

import com.sun.glass.ui.Window.Level;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.tschrock.minecraft.touchmanager.TouchManager;

public class DebugHelper {

	public static enum LogLevel {
		ERROR(1), WARNING(2), NOTE(3), INFO(4), DEBUG(5), DEBUG1(6), DEBUG2(7);
		private int value;

		private LogLevel(int value) {
			this.value = value;
		}
	}

	protected static boolean enabled = false;
	public static LogLevel logLevel = LogLevel.ERROR;
	public static PrintStream logStream = System.out;

	public static void enable() {
		enabled = true;
		log(LogLevel.NOTE, "TouchControls Debug Log Enabled");
	}

	public static void dissable() {
		log(LogLevel.NOTE, "TouchControls Debug Log Disabled");
		enabled = false;
	}

	public static void setEnabled(boolean enable) {
		if (enable && !enabled) {
			enable();
		}
		if (!enable && enabled) {
			dissable();
		}
	}

	public static final String[] sysProps = { "java.version", "java.vendor", "java.vendor.url", "java.home", "java.vm.specification.version",
			"java.vm.specification.vendor", "java.vm.specification.name", "java.vm.version", "java.vm.vendor", "java.vm.name",
			"java.specification.version", "java.specification.vendor", "java.specification.name", "java.class.version", "java.class.path",
			"java.library.path", "java.io.tmpdir", "java.compiler", "os.name", "os.arch", "os.version", "file.separator", "path.separator",
			"line.separator" };

	public static void logSystemInfo() {
		log(LogLevel.NOTE, "System Info:");

		for (String prop : sysProps) {
			log(LogLevel.NOTE, "    " + prop + ": " + System.getProperty(prop));
		}

	}

	public static void logMinecraftInfo() {
		log(LogLevel.NOTE, "Minecraft Info:");

		log(LogLevel.NOTE, "    Version: " + Minecraft.getMinecraft().getVersion());

		List<ModContainer> mods = Loader.instance().getActiveModList();
		log(LogLevel.NOTE, "    Mods (" + mods.size() + "): ");

		String list = "";
		for (ModContainer mod : mods) {
			list += mod.getModId() + "(" + mod.getName() + " " + mod.getVersion() + "), ";
		}
		log(LogLevel.NOTE, "        " + list);
	}

	public static void logModConfig() {
		log(LogLevel.NOTE, "Current TouchControls Config:");

		log(LogLevel.NOTE, "    config_invertX: " + TouchControlsMod.config_invertX);
		log(LogLevel.NOTE, "    config_invertY: " + TouchControlsMod.config_invertY);
		log(LogLevel.NOTE, "    config_dPadSize: " + TouchControlsMod.config_dPadSize);
		log(LogLevel.NOTE, "    config_dPadOpacity: " + TouchControlsMod.config_dPadOpacity);
		log(LogLevel.NOTE, "    config_sensitivity: " + TouchControlsMod.config_sensitivity);
		log(LogLevel.NOTE, "    config_leftClickTimeout: " + TouchControlsMod.config_leftClickTimeout);
		log(LogLevel.NOTE, "    config_rightClickTimeout: " + TouchControlsMod.config_rightClickTimeout);
		log(LogLevel.NOTE, "    config_dragStartRadius: " + TouchControlsMod.config_dragStartRadius);
		log(LogLevel.NOTE, "    config_xOffset: " + TouchControlsMod.config_xOffset);
		log(LogLevel.NOTE, "    config_yOffset: " + TouchControlsMod.config_yOffset);
		log(LogLevel.NOTE, "    config_customTuio: " + TouchControlsMod.config_customTuio);

	}

	public static void logTouchAndWindowInfo() {
		Minecraft mc = Minecraft.getMinecraft();

		log(LogLevel.NOTE, "Current Touch And Window Info:");
		log(LogLevel.NOTE, "    Touch Driver: " + TouchManager.getCurrentTouchDriver());
		GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

		int i = 1;
		for (GraphicsDevice screen : screens) {
			DisplayMode mode = screen.getDisplayMode();
			log(LogLevel.NOTE, "    Screen " + ++i + " Dimensions: " + mode.getWidth() + "x" + mode.getHeight());
		}

		log(LogLevel.NOTE, "    Is Fullscreen: " + mc.isFullScreen());
		log(LogLevel.NOTE, "    Window Title: " + Display.getTitle());
		log(LogLevel.NOTE, "    Window Position: (" + Display.getX() + ", " + Display.getY() + ")");
		log(LogLevel.NOTE, "    Window Dimensions: (" + Display.getWidth() + ", " + Display.getHeight() + ")");
		log(LogLevel.NOTE, "    Window Scale Factor: " + Display.getPixelScaleFactor());

		if (mc.currentScreen != null) {
			log(LogLevel.NOTE, "    Current Screen: " + mc.currentScreen.getClass().getSimpleName());
			log(LogLevel.NOTE, "    Current Screen Dimensions: " + mc.currentScreen.width + "x" + mc.currentScreen.height);
		} else {
			log(LogLevel.NOTE, "    Current Screen: null");
		}
		log(LogLevel.NOTE, "    In-Game Gui Dimensions: " + ForgeEventHandler.width + "x" + ForgeEventHandler.height);

	}

	public static void log(LogLevel level, Object o) {
		if (!enabled) return;
		if (level.value <= logLevel.value){
			logStream.print("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "] [" + level.toString() + "] ");
			logStream.println(o);
		}
	}

	public static void printTrace(LogLevel level, Exception e) {
		if (!enabled) return;
		logStream.print("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "] ");
		e.printStackTrace(logStream);
	}

}