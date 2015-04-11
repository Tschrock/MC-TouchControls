MC - TouchControls
==================

Adds touchscreen controls to minecraft.

Installing:
  1. Make sure you have the latest version of Minecraft Forge installed (http://files.minecraftforge.net)
  2. Download `build/libs/touchcontrols-1.8-0.1.jar` AND `lib/libTUIO.jar`
  3. Put BOTH jar files into your mods folder


Enable touch controls by going to `Options->Controls` and turning `Touchscreen Mode` On.

The mod settings can be found by going to the `Main Menu->Mods->TouchControls->Config`.

Config/Mod Settings:

Setting | Description
------------ | -------------
D-Pad Opacity | How transparent the D-Pad should be, in percent. Doesn't work right now
D-Pad Size | How large the D-Pad should be. (For those of you with tiny/huge thumbs).
Drag Start Radius | The amount of space (in pixels) before a drag is detected.
Invert X-Axis | Self explanatory - It inverts the X axis.
Invert Y-Axis | Same as above, but with the Y axis.
Movement Sensitivity | Like mouse sensitivity, adjusts how fast you can look around.
Timeout - Left Click | The amount of time before which a touch can be a tap.
Timeout - Right Click | The amount of time to wait before a touch can be a long press.
Touch Offset X | An X-Offset for touches. If touches show up left or right of where you're actually touching, try adjusting this.
Touch Offset Y | A Y-Offset for touches. If touches show up above or below of where you're actually touching, try adjusting this.
Custom TUIO Server | This is for me, you don't need to touch this.


Background/Technical Info:
--------------------------
Since Minecraft (And LWJGL) doesn't have multitouch support, this mod runs a platform-dependant program that grabs touchscreen events and sends out TUIO events. It then grabs those events from inside java where we can use them however we want. TUIO is an open framework that defines a common protocol and API for multitouch (See http://www.tuio.org/). Since TUIO is an open framework and uses networking, we can potentialy grap touch events from anywhere (even from another computer), regardless of the underlying OS. 

We still need a TUIO Output Bridge to turn native touch events into TUIO events, but this is already done for us:
 - Linux:
   - mtdev2tuio: https://github.com/olivopaolo/mtdev2tuio
   - x112tuio: (See: src/main/resources/bin/x112tuio)
 - Windows:
   - Touch2Tuio: https://github.com/vialab/Touch2Tuio
   - WM_TOUCH to TUIO: http://nuigroup.com/forums/viewthread/4087/
