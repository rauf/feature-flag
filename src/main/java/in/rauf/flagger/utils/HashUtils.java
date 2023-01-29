package in.rauf.flagger.utils;

import java.util.zip.CRC32;

public class HashUtils {

    public static long getCRC32(byte[] data) {
        CRC32 fileCRC32 = new CRC32();
        fileCRC32.update(data);
        return fileCRC32.getValue();
    }
}
