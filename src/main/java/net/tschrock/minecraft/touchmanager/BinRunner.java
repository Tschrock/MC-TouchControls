package net.tschrock.minecraft.touchmanager;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

// 
// http://stackoverflow.com/questions/600146/run-exe-which-is-packaged-inside-jar
//

public class BinRunner {

	BinRunner self;

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
	}

	String internalLocation;
	String commandArguments;
	String extractedLocation;
	Process binProcess;
	boolean extracted = false;
	boolean running = false;

	public void extract() {
		if (extracted) {
			cleanup();
		}

		try {
			extractedLocation = extractResource(internalLocation);
			extracted = true;
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			Files.deleteIfExists(Paths.get(extractedLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
		extracted = false;
	}

	private static void close(final Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private String extractResource(String resourceLocation) throws IOException {
		InputStream fileInStream = this.getClass().getClassLoader().getResourceAsStream(resourceLocation);
		File tempFile = File.createTempFile((new File(resourceLocation)).getName(), Long.toString(System.currentTimeMillis()));
		tempFile.deleteOnExit();
		tempFile.setExecutable(true);
		OutputStream fileOutStream = new FileOutputStream(tempFile);
		
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
		return (tempFile.getAbsolutePath());
	}
}
