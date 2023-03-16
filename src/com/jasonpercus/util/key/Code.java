/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.key;



/**
 * Cette classe permet de renvoyer le code d'une touche par son nom et vice-versa
 * @see java.awt.event.KeyEvent Pour l'utilisation des codes de touches
 * @author JasonPercus
 * @version 1.0
 */
public final class Code {
    
    
    
//CONSTANTE
    /**
     * Correspond au tableau de tous les codes de touches associés à leur nom
     */
    private final static java.util.List<Code> CODES = new java.util.ArrayList<>();
    
    
    
//INIT STATIC
    /**
     * Remplit le tableau {@link #CODES}
     */
    static{
        CODES.add(new Code(java.awt.event.KeyEvent.VK_0, "0"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_1, "1"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_2, "2"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_3, "3"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_4, "4"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_5, "5"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_6, "6"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_7, "7"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_8, "8"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_9, "9"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_A, "A"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ACCEPT, "ACCEPT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ADD, "ADD"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_AGAIN, "AGAIN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ALL_CANDIDATES, "ALL_CANDIDATES"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ALPHANUMERIC, "ALPHANUMERIC"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ALT, "ALT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ALT_GRAPH, "ALT_GRAPH"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_AMPERSAND, "AMPERSAND"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ASTERISK, "ASTERISK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_AT, "AT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_B, "B"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_BACK_QUOTE, "BACK_QUOTE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_BACK_SLASH, "BACK_SLASH"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_BACK_SPACE, "BACK_SPACE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_BEGIN, "BEGIN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_BRACELEFT, "BRACELEFT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_BRACERIGHT, "BRACERIGHT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_C, "C"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CANCEL, "CANCEL"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CAPS_LOCK, "CAPS_LOCK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CIRCUMFLEX, "CIRCUMFLEX"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CLEAR, "CLEAR"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CLOSE_BRACKET, "CLOSE_BRACKET"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CODE_INPUT, "CODE_INPUT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_COLON, "COLON"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_COMMA, "COMMA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_COMPOSE, "COMPOSE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CONTEXT_MENU, "CONTEXT_MENU"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CONTROL, "CONTROL"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CONVERT, "CONVERT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_COPY, "COPY"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_CUT, "CUT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_D, "D"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_ABOVEDOT, "ABOVEDOT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_ABOVERING, "ABOVERING"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_ACUTE, "ACUTE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_BREVE, "BREVE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_CARON, "CARON"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_CEDILLA, "CEDILLA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_CIRCUMFLEX, "CIRCUMFLEX"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_DIAERESIS, "DIAERESIS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_DOUBLEACUTE, "DOUBLEACUTE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_GRAVE, "GRAVE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_IOTA, "IOTA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_MACRON, "MACRON"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_OGONEK, "OGONEK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_SEMIVOICED_SOUND, "SEMIVOICED_SOUND"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_TILDE, "TILDE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DEAD_VOICED_SOUND, "VOICED_SOUND"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DECIMAL, "DECIMAL"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DELETE, "DELETE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DIVIDE, "DIVIDE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DOLLAR, "DOLLAR"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_DOWN, "DOWN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_E, "E"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_END, "END"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ENTER, "ENTER"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_EQUALS, "EQUALS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ESCAPE, "ESCAPE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_EURO_SIGN, "EURO_SIGN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_EXCLAMATION_MARK, "EXCLAMATION_MARK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F, "F"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F1, "F1"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F10, "F10"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F11, "F11"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F12, "F12"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F13, "F13"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F14, "F14"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F15, "F15"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F16, "F16"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F17, "F17"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F18, "F18"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F19, "F19"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F2, "F2"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F20, "F20"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F21, "F21"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F22, "F22"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F23, "F23"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F24, "F24"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F3, "F3"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F4, "F4"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F5, "F5"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F6, "F6"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F7, "F7"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F8, "F8"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_F9, "F9"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_FINAL, "FINAL"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_FIND, "FIND"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_FULL_WIDTH, "FULL_WIDTH"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_G, "G"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_GREATER, "GREATER"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_H, "H"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_HALF_WIDTH, "HALF_WIDTH"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_HELP, "HELP"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_HIRAGANA, "HIRAGANA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_HOME, "HOME"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_I, "I"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_INPUT_METHOD_ON_OFF, "INPUT_METHOD_ON_OFF"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_INSERT, "INSERT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_INVERTED_EXCLAMATION_MARK, "INVERTED_EXCLAMATION_MARK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_J, "J"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_JAPANESE_HIRAGANA, "JAPANESE_HIRAGANA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_JAPANESE_KATAKANA, "JAPANESE_KATAKANA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_JAPANESE_ROMAN, "JAPANESE_ROMAN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_K, "K"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KANA, "KANA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KANA_LOCK, "KANA_LOCK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KANJI, "KANJI"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KATAKANA, "KATAKANA"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KP_DOWN, "KP_DOWN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KP_LEFT, "KP_LEFT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KP_RIGHT, "KP_RIGHT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_KP_UP, "KP_UP"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_L, "L"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_LEFT, "LEFT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_LEFT_PARENTHESIS, "LEFT_PARENTHESIS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_LESS, "LESS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_M, "M"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_META, "META"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_MINUS, "MINUS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_MODECHANGE, "MODECHANGE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_MULTIPLY, "MULTIPLY"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_N, "N"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NONCONVERT, "NONCONVERT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUM_LOCK, "NUM_LOCK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMBER_SIGN, "NUMBER_SIGN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD0, "NUMPAD0"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD1, "NUMPAD1"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD2, "NUMPAD2"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD3, "NUMPAD3"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD4, "NUMPAD4"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD5, "NUMPAD5"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD6, "NUMPAD6"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD7, "NUMPAD7"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD8, "NUMPAD8"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_NUMPAD9, "NUMPAD9"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_O, "O"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_OPEN_BRACKET, "OPEN_BRACKET"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_P, "P"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PAGE_DOWN, "PAGE_DOWN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PAGE_UP, "PAGE_UP"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PASTE, "PASTE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PAUSE, "PAUSE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PERIOD, "PERIOD"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PLUS, "PLUS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PREVIOUS_CANDIDATE, "PREVIOUS_CANDIDATE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PRINTSCREEN, "PRINTSCREEN"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_PROPS, "PROPS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_Q, "Q"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_QUOTE, "QUOTE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_QUOTEDBL, "QUOTEDBL"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_R, "R"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_RIGHT, "RIGHT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_RIGHT_PARENTHESIS, "RIGHT_PARENTHESIS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_ROMAN_CHARACTERS, "ROMAN_CHARACTERS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_S, "S"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SCROLL_LOCK, "SCROLL_LOCK"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SEMICOLON, "SEMICOLON"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SEPARATER, "SEPARATER"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SEPARATOR, "SEPARATOR"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SHIFT, "SHIFT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SLASH, "SLASH"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SPACE, "SPACE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_STOP, "STOP"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_SUBTRACT, "SUBTRACT"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_T, "T"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_TAB, "TAB"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_U, "U"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_UNDEFINED, "UNDEFINED"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_UNDERSCORE, "UNDERSCORE"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_UNDO, "UNDO"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_UP, "UP"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_V, "V"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_W, "W"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_WINDOWS, "WINDOWS"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_X, "X"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_Y, "Y"));
        CODES.add(new Code(java.awt.event.KeyEvent.VK_Z, "Z"));
    }

    
    
//ATTRIBUTS
    /**
     * Correspond au code de la touche
     */
    private final int code;
    
    /**
     * Correspond au nom de la touche
     */
    private final String name;
    
    
    
//CONSTRUCTORS
    /**
     * Crée un code par défaut
     * @deprecated Non utilisable
     */
    @Deprecated
    private Code() {
        this.code = -1;
        this.name = null;
    }

    /**
     * Crée un Code
     * @param code Correspond au code de la touche
     * @param name Correspond au nom de la touche
     */
    private Code(int code, String name) {
        this.code = code;
        this.name = name;
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Renvoie le nom d'une touche par son {@link java.awt.event.KeyEvent code}
     * @param code Correspond au code de la touche
     * @return Retourne le nom de la touche
     */
    public static String name(int code){
        for(Code c : CODES){
            if(c.code == code)
                return c.name;
        }
        return null;
    }
    
    /**
     * Renvoie le code d'une touche par son {@link java.awt.event.KeyEvent nom}
     * @param name Correspond au nom de la touche
     * @return Retourne le code de la touche
     */
    public static int code(String name){
        for(Code c : CODES){
            if(c.name.equals(name))
                return c.code;
        }
        return -1;
    }
    
    
    
}