package com.green.secondproject.common.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PwUtils {
    private static final char[] rndAllCharacters = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            //uppercase
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            //lowercase
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            //special symbols
            '@', '$', '!', '%', '*', '?', '&'
    };

    private static final char[] numberCharacters = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final char[] uppercaseCharacters = new char[] {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final char[] lowercaseCharacters = new char[] {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] specialSymbolCharacters = new char[] {
        '@', '$', '!', '%', '*', '?', '&'
    };

    public static String getRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        List<Character> passwordCharacters = new ArrayList<>();

        int numberCharactersLength = numberCharacters.length;
        passwordCharacters.add(numberCharacters[random.nextInt(numberCharactersLength)]);

        int uppercaseCharactersLength = uppercaseCharacters.length;
        passwordCharacters.add(uppercaseCharacters[random.nextInt(uppercaseCharactersLength)]);

        int lowercaseCharactersLength = lowercaseCharacters.length;
        passwordCharacters.add(lowercaseCharacters[random.nextInt(lowercaseCharactersLength)]);

        int specialSymbolCharactersLength = specialSymbolCharacters.length;
        passwordCharacters.add(specialSymbolCharacters[random.nextInt(specialSymbolCharactersLength)]);

        int rndAllCharactersLength = rndAllCharacters.length;
        for (int i = 0; i < length-4; i++) {
            passwordCharacters.add(rndAllCharacters[random.nextInt(rndAllCharactersLength)]);
        }

        Collections.shuffle(passwordCharacters);

        for (Character character : passwordCharacters) {
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }
}
