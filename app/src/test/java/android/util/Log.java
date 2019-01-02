package android.util;

/**
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
public class Log {

    public static int d(String tag, String message) {
        System.out.println(tag + ": " + message);
        return 0;
    }


    public static int e(String tag, String message, Throwable t) {
        System.out.println(tag + ": " + message + " " + t.getMessage());
        return 0;
    }

}
