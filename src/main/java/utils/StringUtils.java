package utils;

public class StringUtils {
    public static String rightpad(String _text, int _length) {
        return String.format("%-" + _length + "." + _length + "s", _text);
    }
}
