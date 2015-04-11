package net.tschrock.minecraft.touchmanager.drivers.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import net.tschrock.minecraft.touchmanager.BinRunner;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.drivers.generic.TUIOTouchDriver;

public class X112TUIOTouchDriver extends TUIOTouchDriver {

	BinRunner procEx;
	Process proc = null;
	
	public X112TUIOTouchDriver() {

		Thread closeChildThread = new Thread() {
			public void run() {
				proc.destroy();
			}
		};

		Runtime.getRuntime().addShutdownHook(closeChildThread);
		
		procEx = new BinRunner("bin/x112tuio");
		proc = procEx.extractAndRun();
		
	}
	
}
