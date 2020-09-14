package views;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputFormat implements KeyListener {

    public static final String NUMBERS_ONLY = "NUMBERS_ONLY";
    public static final String RFC = "RFC";
    public static final String LETTERS_ONLY = "LETTERS_ONLY";
    public static final String LETTERS_AND_NUMBERS_ONLY = "LETTERS_AND_NUMBERS_ONLY";
    public static final String LETTERS_AND_NUMBERS_WITHOUT_SPACE = "LETTERS_AND_NUMBERS_WITHOUT_SPACE";
    public static final String ALL_CHARACTERS = "ALL_CHARACTERS";

    private final String filterCharacters;
    private final int maxLength;

    public InputFormat(String filterCharacters, int maxLength) {
        this.filterCharacters = filterCharacters;
        this.maxLength = maxLength;
    }

    public InputFormat(int maxLength) {
        this(ALL_CHARACTERS, maxLength);
    }

    public InputFormat(String filterCharacters) {
        this(filterCharacters, -1);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        JTextField  input       = (JTextField) keyEvent.getSource();
        char        character   = keyEvent.getKeyChar();

        if (this.maxLength != -1 && input.getText().length() >= this.maxLength)
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
                status = Character.isDigit(character);
                break;
            case LETTERS_ONLY:
                status = Character.isLetter(character) || Character.isSpaceChar(character);
                break;
            case RFC:
                status =   Character.isLetter(character)
                        || Character.isDigit(character);
                break;
            case LETTERS_AND_NUMBERS_ONLY:
                status =   Character.isLetter(character)
                        || Character.isSpaceChar(character)
                        || Character.isDigit(character);
                break;
        }

        if (filterCharacters.equals(RFC) && (character == 'ñ' || character == 'Ñ'))
            status = false;

        return status;
    }
}
