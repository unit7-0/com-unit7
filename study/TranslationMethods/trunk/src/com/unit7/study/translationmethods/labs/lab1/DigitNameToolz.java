package com.unit7.study.translationmethods.labs.lab1;

public class DigitNameToolz implements NameToolz {
    @Override
    public String makeName(String name) {
        int newDigit = 0;
        int index = findFirstDigit(name);

        if (index == -1) {
            return name + newDigit;
        }
        
        return name.substring(0, index + 1) + (Integer.parseInt(name.substring(index + 1)) + 1);
    }

    @Override
    public String getName(String string) {
        int index = 1;
        for (; index < string.length(); ++index) {
            if (!Character.isDigit(string.charAt(index)))
                break;
        }
        
        return string.substring(0, index);
    }
    
    /**
     * @return first digit index or -1 if not
     * @param string
     */
    private int findFirstDigit(String string) {
        int index = string.length() - 1;
        for (; index > 0; --index) {
            if (!Character.isDigit(string.charAt(index))) {
                break;
            }
        }
        
        return index == string.length() - 1 ? -1 : index;
    }
}
