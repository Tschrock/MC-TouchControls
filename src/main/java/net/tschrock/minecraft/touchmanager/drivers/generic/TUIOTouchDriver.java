package net.tschrock.minecraft.touchmanager.drivers.generic;

import java.awt.Dimension;
import java.awt.Toolkit;

import TUIO.TuioBlob;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import net.tschrock.minecraft.touchmanager.GenericTouchDriver;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.TouchEvent.Type;

public class TUIOTouchDriver extends GenericTouchDriver implements TuioListener{

	public TUIOTouchDriver() {
		tClient = new TuioClient();
		tClient.addTuioListener(this);
		tClient.connect();
	}
	
	protected TuioClient tClient;

	public boolean isNative(){
		return false;
	}
	public boolean hasGlobalFocus(){
		return true;
	}

	@Override
	public void addTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTuioCursor(TuioCursor tCursor) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		//System.out.println(tCursor.getX() + ", " + tCursor.getY() + ", " + tCursor.getScreenX(width) + ", " + tCursor.getScreenY(height));
		this.onNewEvent(new TouchEvent(Type.TOUCH_START, tCursor.getCursorID(), tCursor.getScreenX(width), tCursor.getScreenY(height)));
	}

	@Override
	public void addTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(TuioTime arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioCursor(TuioCursor tCursor) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		this.onNewEvent(new TouchEvent(Type.TOUCH_END, tCursor.getCursorID(), tCursor.getScreenX(width), tCursor.getScreenY(height)));
		
	}

	@Override
	public void removeTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioCursor(TuioCursor tCursor) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		this.onNewEvent(new TouchEvent(Type.TOUCH_UPDATE, tCursor.getCursorID(), tCursor.getScreenX(width), tCursor.getScreenY(height)));
		
	}

	@Override
	public void updateTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
