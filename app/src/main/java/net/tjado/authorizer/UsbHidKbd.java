package net.tjado.authorizer;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by tm on 28.04.16.
 */
public abstract class UsbHidKbd {

    // ToDo: replace byte with ByteArray... eveywhere
    protected Map<String, byte[]> kbdVal= new HashMap<String, byte[]>();

    public byte[] getScancode(String key) {

        byte[] value = (byte[]) kbdVal.get(key);

        if ( value == null ) {
            throw new NoSuchElementException("Scancode for '" + key + "' not found (" + this.kbdVal.size() + ")");
        }

        return value;
    }

}
