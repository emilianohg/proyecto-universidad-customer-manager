package views;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputMask implements KeyListener {

    public static final String NUMBERS_ONLY = "NUMBERS_ONLY";
    public static final String LETTERS_ONLY = "LETTERS_ONLY";
    public static final String LETTERS_AND_NUMBERS_ONLY = "LETTERS_AND_NUMBERS_ONLY";
    public static final String ALL_CHARACTERS = "ALL_CHARACTERS";

    private final String filterCharacters;
    private final int maxLength;

    public InputMask(String filterCharacters, int maxLength) {
        this.filterCharacters = filterCharacters;
        this.maxLength = maxLength;
    }

    public InputMask(int maxLength) {
        this.filterCharacters = ALL_CHARACTERS;
        this.maxLength = maxLength;
    }

    public InputMask(String filterCharacters) {
        this.filterCharacters = filterCharacters;
        this.maxLength = -1;
    }

    public InputMask() {
        this.filterCharacters = ALL_CHARACTERS;
        this.maxLength = -1;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        JTextField  input       = (JTextField) keyEvent.getSource();
        char        character   = keyEvent.getKeyChar();

        if (this.maxLength != -1 && input.getText().length() > this.maxLength)
            keyEvent.consume();

        if (!isCharacterValid(character)) {
            keyEvent.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {  }

    @Override
    public void keyReleased(KeyEvent keyEvent) {  }

    private boolean isCharacterValid (char character) {

        boolean status = false;

        switch (filterCharacters) {
            case ALL_CHARACTERS:
                status = true;
                break;
            case NUMBERS_ONLY:
                status = (character >= '0' && character <= '9');
                break;
            case LETTERS_ONLY:
                status = (character >= 'a' && character <= 'z')
                    || (character >= 'A' && character <= 'Z')
                    || character == 32;
                break;
            case LETTERS_AND_NUMBERS_ONLY:
                status = (character >= 'a' && character <= 'z')
                    || (character >= 'A' && character <= 'Z')
                    || (character >= '0' && character <= '9')
                    || character == 32;
                break;
        }

        return status;
    }
}
