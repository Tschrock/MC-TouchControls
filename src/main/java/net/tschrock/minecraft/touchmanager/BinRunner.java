package net.tschrock.minecraft.touchmanager;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;

import net.tschrock.minecraft.touchcontrols.DebugHelper;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;

// 
// http://stackoverflow.com/questions/600146/run-exe-which-is-packaged-inside-jar
//

public class BinRunner {
	
	public class InputStreamLogger implements Runnable{
		
		String prefix = "";
		InputStream stream = null;
		BufferedReader br = null;
		boolean running = false;
		LogLevel logLevel = LogLevel.DEBUG;
		
		public InputStreamLogger(LogLevel logLevel, String prefix, InputStream stream) {
			this.logLevel = logLevel;
			this.prefix = prefix;
			this.stream = stream;
		}

		@Override
		public void run() {
			String sCurrentLine;
			running = true;
			try {
				br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				while ((sCurrentLine = br.readLine()) != null && running) {
					DebugHelper.log(logLevel, prefix + sCurrentLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	BinRunner self;
	File tmpFile;

	public BinRunner(String internalLocation) {
		this(internalLocation, "");
	}

	public BinRunner(String internalLocation, String commandArguments) {
		this.internalLocation = internalLocation;
		this.commandArguments = commandArguments;

		self = this;
		

		Thread closeChildThread = new Thread() {
			public void run() {
				self.stop();
				self.cleanup();
			}
		};

		Runtime.getRuntime().addShutdownHook(closeChildThread);
	}

	String internalLocation;
	String commandArguments;
	String extractedLocation;
	Process binProcess;
	boolean extracted = false;
	boolean running = false;
	public boolean logError = true;
	public boolean logOutput = true;

	public void extract() {
		if (extracted) {
			cleanup();
		}

		try {
			extractedLocation = extractResource(internalLocation);
			extracted = true;
			DebugHelper.log(LogLevel.INFO, "Extracted '" + internalLocation + "' to '" + extractedLocation + "'");
		} catch (IOException e) {
			DebugHelper.log(LogLevel.ERROR, "Error extracting '" + internalLocation + "':");
			DebugHelper.printTrace(LogLevel.ERROR, e);
		}

	}

	public Process run() {
		if (!extracted) {
			extract();
		}
		if (running) {
			stop();
		}

		try {
			binProcess = Runtime.getRuntime().exec(extractedLocation + " " + commandArguments);
			running = true;
			DebugHelper.log(LogLevel.NOTE, "Started '" + internalLocation + "' at '" + extractedLocation + "'");
			if(logOutput){
				(new Thread(new InputStreamLogger(LogLevel.NOTE, "'" + extractedLocation + " " + commandArguments + "': ", binProcess.getInputStream()))).start();
			}
			if(logError){
				(new Thread(new InputStreamLogger(LogLevel.ERROR, "'" + extractedLocation + " " + commandArguments + "': ", binProcess.getErrorStream()))).start();
			}
		} catch (IOException e) {
			DebugHelper.log(LogLevel.ERROR, "Error running '" + internalLocation + "' at '" + extractedLocation + " " + commandArguments + "':");
			DebugHelper.printTrace(LogLevel.ERROR, e);
		}

		return binProcess;
	}

	public Process extractAndRun() {
		extract();
		return run();
	}

	public void stop() {
		binProcess.destroy();
		running = false;
	}

	public void cleanup() {
		tmpFile.delete();
		extracted = false;
	}

	private static void close(final Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException e) {
				DebugHelper.log(LogLevel.WARNING, "IOExeption while closing stream:");
				DebugHelper.printTrace(LogLevel.WARNING, e);
			}
		}
	}

	private String extractResource(String resourceLocation) throws IOException {
		InputStream fileInStream = this.getClass().getClassLoader().getResourceAsStream(resourceLocation);
		
		String name = FilenameUtils.getName(resourceLocation);
		
		
		tmpFile = new File(System.getProperty("java.io.tmpdir"), name);
		tmpFile.deleteOnExit();
		
		OutputStream fileOutStream = new FileOutputStream(tmpFile);
		try {
			final byte[] buf;
			int i;

			buf = new byte[1024];
			i = 0;

			while ((i = fileInStream.read(buf)) != -1) {
				fileOutStream.write(buf, 0, i);
			}
		} finally {
			close(fileInStream);
			close(fileOutStream);
		}
		return (tmpFile.getAbsolutePath());
	}
}
