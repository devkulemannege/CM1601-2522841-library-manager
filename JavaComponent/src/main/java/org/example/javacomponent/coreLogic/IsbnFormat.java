package org.example.javacomponent.corelogic;

public class IsbnFormat {
    public static boolean validate(String isbn){
        /* use method as static method to validate isbn */
        int checkDigit = 0, actualCheckDigit = 0;

        try {
            String[] isbnCharacters = isbn.split("");
            actualCheckDigit = Integer.parseInt(isbnCharacters[isbnCharacters.length - 1]); // get last index position

            for (int pos = 0; pos < isbnCharacters.length - 1; pos++) {
                if ((pos % 2) == 1) {
                    checkDigit += Integer.parseInt(isbnCharacters[pos]) * 3;
                } else {
                    checkDigit += Integer.parseInt(isbnCharacters[pos]);
                }
            }
            checkDigit = checkDigit % 10; // remainder
            checkDigit = (10 - checkDigit) % 10;
        } catch (NumberFormatException e) {
            // catch if alphabetical characters in isbn
            System.out.println("isbn includes non-numerical characters");
            return false;
        }

        return checkDigit == actualCheckDigit; // true is equal. false is not equal
    }
}
