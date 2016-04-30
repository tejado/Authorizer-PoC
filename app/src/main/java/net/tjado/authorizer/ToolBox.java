package net.tjado.authorizer;

/**
 * Created by tm on 27.04.16.
 */
public class ToolBox {

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String formatString(String str) {
        if (str != null) {
            str = str.replaceAll("\\r\\n|\\r|\\n", " ");
        }
        return str;
    }
}
