package net.tschrock.minecraft.touchmanager.drivers.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import net.tschrock.minecraft.touchmanager.BinRunner;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.drivers.generic.TUIOTouchDriver;

public class Touch2TUIOTouchDriver extends TUIOTouchDriver {

	BinRunner procEx;
	BinRunner procDll;
	Process proc = null;

	@Override
	public boolean hasGlobalFocus() {
		return true;
	}

	public Touch2TUIOTouchDriver() {

		Thread closeChildThread = new Thread() {
			public void run() {
				proc.destroy();
			}
		};

		Runtime.getRuntime().addShutdownHook(closeChildThread);

		boolean is64bit = false;
		if (System.getProperty("os.name").contains("Windows")) {
			is64bit = (System.getenv("ProgramFiles(x86)") != null);
		} else {
			is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
		}

		if (!is64bit) {
			procDll = new BinRunner("bin/TouchHook.dll");
			procEx = new BinRunner("bin/Touch2Tuio.exe", " Minecraft");
		} else {
			procDll = new BinRunner("bin/TouchHook_x64.dll");
			procEx = new BinRunner("bin/Touch2Tuio_x64.exe", " Minecraft");
		}

		procDll.extract();
		proc = procEx.extractAndRun();

	}

}
