/*     */ package net.tschrock.minecraft.touchmanager;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Paths;
/*     */ import net.tschrock.minecraft.touchcontrols.DebugHelper;
/*     */ import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinRunner
/*     */ {
/*     */   BinRunner self;
/*     */   String internalLocation;
/*     */   String commandArguments;
/*     */   String extractedLocation;
/*     */   Process binProcess;
/*     */   
/*     */   public class InputStreamLogger
/*     */     implements Runnable
/*     */   {
/*  37 */     String prefix = "";
/*  38 */     InputStream stream = null;
/*  39 */     BufferedReader br = null;
/*  40 */     boolean running = false;
/*  41 */     DebugHelper.LogLevel logLevel = DebugHelper.LogLevel.DEBUG;
/*     */     
/*     */     public InputStreamLogger(DebugHelper.LogLevel logLevel, String prefix, InputStream stream) {
/*  44 */       this.logLevel = logLevel;
/*  45 */       this.prefix = prefix;
/*  46 */       this.stream = stream;
/*     */     }
/*     */     
/*     */ 
/*     */     public void run()
/*     */     {
/*  52 */       this.running = true;
/*     */       try {
/*  54 */         this.br = new BufferedReader(new InputStreamReader(this.stream, "UTF-8"));
/*  55 */         String sCurrentLine; while (((sCurrentLine = this.br.readLine()) != null) && (this.running)) {
/*  56 */           DebugHelper.log(this.logLevel, this.prefix + sCurrentLine);
/*     */         }
/*     */       } catch (IOException e) {
/*  59 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BinRunner(String internalLocation)
/*     */   {
/*  68 */     this(internalLocation, "");
/*     */   }
/*     */   
/*     */   public BinRunner(String internalLocation, String commandArguments) {
/*  72 */     this.internalLocation = internalLocation;
/*  73 */     this.commandArguments = commandArguments;
/*     */     
/*  75 */     this.self = this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  82 */   boolean extracted = false;
/*  83 */   boolean running = false;
/*  84 */   public boolean logError = true;
/*  85 */   public boolean logOutput = true;
/*     */   
/*     */   public void extract() {
/*  88 */     if (this.extracted) {
/*  89 */       cleanup();
/*     */     }
/*     */     try
/*     */     {
/*  93 */       this.extractedLocation = extractResource(this.internalLocation);
/*  94 */       this.extracted = true;
/*  95 */       DebugHelper.log(DebugHelper.LogLevel.INFO, "Extracted '" + this.internalLocation + "' to '" + this.extractedLocation + "'");
/*     */     } catch (IOException e) {
/*  97 */       DebugHelper.log(DebugHelper.LogLevel.ERROR, "Error extracting '" + this.internalLocation + "':");
/*  98 */       DebugHelper.printTrace(DebugHelper.LogLevel.ERROR, e);
/*     */     }
/*     */   }
/*     */   
/*     */   public Process run()
/*     */   {
/* 104 */     if (!this.extracted) {
/* 105 */       extract();
/*     */     }
/* 107 */     if (this.running) {
/* 108 */       stop();
/*     */     }
/*     */     try
/*     */     {
/* 112 */       this.binProcess = Runtime.getRuntime().exec(this.extractedLocation + " " + this.commandArguments);
/* 113 */       this.running = true;
/* 114 */       DebugHelper.log(DebugHelper.LogLevel.NOTE, "Started '" + this.internalLocation + "' at '" + this.extractedLocation + "'");
/* 115 */       if (this.logOutput) {
/* 116 */         new Thread(new InputStreamLogger(DebugHelper.LogLevel.NOTE, "'" + this.extractedLocation + " " + this.commandArguments + "': ", this.binProcess.getInputStream())).start();
/*     */       }
/* 118 */       if (this.logError) {
/* 119 */         new Thread(new InputStreamLogger(DebugHelper.LogLevel.ERROR, "'" + this.extractedLocation + " " + this.commandArguments + "': ", this.binProcess.getErrorStream())).start();
/*     */       }
/*     */     } catch (IOException e) {
/* 122 */       DebugHelper.log(DebugHelper.LogLevel.ERROR, "Error running '" + this.internalLocation + "' at '" + this.extractedLocation + " " + this.commandArguments + "':");
/* 123 */       DebugHelper.printTrace(DebugHelper.LogLevel.ERROR, e);
/*     */     }
/*     */     
/* 126 */     return this.binProcess;
/*     */   }
/*     */   
/*     */   public Process extractAndRun() {
/* 130 */     extract();
/* 131 */     return run();
/*     */   }
/*     */   
/*     */   public void stop() {
/* 135 */     this.binProcess.destroy();
/* 136 */     this.running = false;
/*     */   }
/*     */   
/*     */   public void cleanup() {
/*     */     try {
/* 141 */       Files.deleteIfExists(Paths.get(this.extractedLocation, new String[0]));
/*     */     } catch (IOException e) {
/* 143 */       DebugHelper.log(DebugHelper.LogLevel.WARNING, "IOExeption while cleaning up '" + this.extractedLocation + "'");
/* 144 */       DebugHelper.printTrace(DebugHelper.LogLevel.WARNING, e);
/*     */     }
/* 146 */     this.extracted = false;
/*     */   }
/*     */   
/*     */   private static void close(Closeable stream) {
/* 150 */     if (stream != null) {
/*     */       try {
/* 152 */         stream.close();
/*     */       } catch (IOException e) {
/* 154 */         DebugHelper.log(DebugHelper.LogLevel.WARNING, "IOExeption while closing stream:");
/* 155 */         DebugHelper.printTrace(DebugHelper.LogLevel.WARNING, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String extractResource(String resourceLocation) throws IOException {
/* 161 */     InputStream fileInStream = getClass().getClassLoader().getResourceAsStream(resourceLocation);
/* 162 */     File tempFile = File.createTempFile(new File(resourceLocation).getName(), "");
/* 163 */     tempFile.deleteOnExit();
/* 164 */     tempFile.setExecutable(true);
/* 165 */     OutputStream fileOutStream = new FileOutputStream(tempFile);
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 171 */       byte[] buf = new byte['Ѐ'];
/* 172 */       int i = 0;
/*     */       
/* 174 */       while ((i = fileInStream.read(buf)) != -1) {
/* 175 */         fileOutStream.write(buf, 0, i);
/*     */       }
/*     */     } finally {
/* 178 */       close(fileInStream);
/* 179 */       close(fileOutStream);
/*     */     }
/* 181 */     return tempFile.getAbsolutePath();
/*     */   }
/*     */ }


/* Location:              /home/alan/Downloads/touchcontrols-1.8-0.0.3(1).jar!/net/tschrock/minecraft/touchmanager/BinRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */