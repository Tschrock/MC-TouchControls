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
	Process proc = null;
	
	@Override
	public boolean hasGlobalFocus() {
		return false;
	}
	
	public Touch2TUIOTouchDriver() {

		Thread closeChildThread = new Thread() {
			public void run() {
				proc.destroy();
			}
		};

		Runtime.getRuntime().addShutdownHook(closeChildThread);
		
		procEx = new BinRunner("bin/Touch2Tuio_x64.exe", " Minecraft");
		proc = procEx.extractAndRun();
		
	}
	
}
