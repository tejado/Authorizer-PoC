package net.tjado.authorizer;


import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by tm on 21.04.16.
 */
public class OutputKeyboard implements OutputInterface {

    // Logger
    private static Log log = Log.getInstance();

    protected String devicePath = "/dev/hidg0";
    protected FileOutputStream device;
    UsbHidKbd kbdKeyInterpreter;
    public enum Language { English, German };


    public OutputKeyboard(OutputInterface.Language lang) throws IOException {
        setLanguage(lang);

        openDevice();
    }

    public void destruct()  {
        closeDevice();
    }

    public boolean setLanguage(OutputInterface.Language lang) {

        try {
            String className = "UsbHidKbd_" + lang;
            kbdKeyInterpreter = (UsbHidKbd) Class.forName(className).newInstance();
            return true;
        }
        catch (Exception e) {
            kbdKeyInterpreter = new UsbHidKbd_English();
            return false;
        }

    }

    private void openDevice() throws IOException {
        device = new FileOutputStream(devicePath, true);
    }

    private void closeDevice() {
        try {
            if (device != null) {
                device.close();
            }
        } catch (Exception e) {}
    }

    private void clean() throws IOException {
        // overwriting the last keystroke, otherwise it will be repeated until the next writing
        // and it would not be possible to repeat the keystroke
        byte[] scancode_reset = kbdKeyInterpreter.getScancode(null);
        log.debug("RST > " + ToolBox.bytesToHex(scancode_reset));
        device.write(scancode_reset);
    }


    public void sendText(String output) throws IOException {

        byte[] scancode;

        for (int i = 0; i < output.length(); i++) {
            String textCharString = String.valueOf( output.charAt(i) );

            scancode = kbdKeyInterpreter.getScancode( textCharString );

            log.debug( textCharString + " > " + ToolBox.bytesToHex(scancode) );
            device.write(scancode);

            clean();
        }
    }

    public void sendScancode(byte[] output) throws FileNotFoundException, IOException {

        if( output.length == 8) {
            log.debug( ToolBox.bytesToHex(output) );
            device.write(output);

            clean();
        } else if (output.length == 1) {
            byte[] scancode = new byte[] {0x00, 0x00, output[0], 0x00, 0x00, 0x00, 0x00, 0x00};

            log.debug( ToolBox.bytesToHex(scancode) );
            device.write(scancode);

            clean();
        }

    }

}
