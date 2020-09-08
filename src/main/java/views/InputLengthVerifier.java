package views;

import javax.swing.*;

public class InputLengthVerifier extends InputVerifier {
    final private int length;

    public InputLengthVerifier (int length) {
        this.length = length;
    }
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        System.out.println(text + " - " + (text.length() <= length));
        return text.length() <= length;
    }
}
