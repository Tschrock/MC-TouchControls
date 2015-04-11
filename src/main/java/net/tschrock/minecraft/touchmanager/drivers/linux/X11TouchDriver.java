package net.tschrock.minecraft.touchmanager.drivers.linux;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import net.tschrock.minecraft.touchmanager.BinRunner;
import net.tschrock.minecraft.touchmanager.GenericTouchDriver;
import net.tschrock.minecraft.touchmanager.ITouchDriver;
import net.tschrock.minecraft.touchmanager.TouchEvent;

public class X11TouchDriver extends GenericTouchDriver {
	
	X11TouchDriver self;

	BinRunner procEx;
	Process proc = null;
	OutputStream procIn = null;
	InputStream procOut = null;
	BufferedReader br = null;
	
	Boolean running = false;
	
	public X11TouchDriver() {

		self = this;
		
		Thread closeChildThread = new Thread() {
			public void run() {
				self.shutdown();
			}
		};

		Runtime.getRuntime().addShutdownHook(closeChildThread);

		this.start();
	}

	public boolean isNative(){
		return true;
	}
	public boolean hasGlobalFocus(){
		return true;
	}

	@Override
	public void run() {
		running = true;
		try {
			procEx = new BinRunner("bin/X11Touch");
			proc = procEx.extractAndRun();
			procIn = proc.getOutputStream();
			procOut = proc.getInputStream();
			br = new BufferedReader(new InputStreamReader(procOut, "UTF-8"));

			String sCurrentLine;
			String[] parts;
			int type;
			int id;
			int x;
			int y;
			TouchEvent.Type eType;
			
			while ((sCurrentLine = br.readLine()) != null && running) {
				parts = sCurrentLine.split(",");
				type = Integer.parseInt(parts[0]);
				id = Integer.parseInt(parts[1]);
				x = Integer.parseInt(parts[2]);
				y = Integer.parseInt(parts[3]);
				
				if (type == 18) { // It's the start of a touch
					eType = TouchEvent.Type.TOUCH_START;
				} else if (type == 19) { // We are just updating the touch
					eType = TouchEvent.Type.TOUCH_UPDATE;
				} else if (type == 20) { // We are getting rid of a touch
					eType = TouchEvent.Type.TOUCH_END;
				} else {
					eType = TouchEvent.Type.UNKNOWN;
				}

				this.onNewEvent(new TouchEvent(eType, id, x, y));
			}

		} catch (IOException e) {
			running = false;
			disableEventQueue();
			shutdown();
			e.printStackTrace();
		}

	}
	

	public void requestStop() {
		if (running && proc.isAlive()){
			running = false;
			proc.destroy();
		}
	}
	
	public void shutdown() {
		if(proc.isAlive()){
			proc.destroy();
			try {
				br.close();
				procOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
