package br.com.rodrigo.vanhackcodechallenge;

import java.util.*;

public class ChallegeCodeMose {

    public static void main(String[] args) {
        var signals = Arrays.asList(".", ".-", "?", "?.", ".?", "?-?");

        signals.forEach(s -> System.out.println(s + possibilities(s)));
    }

    public static List<String> possibilities(String signals) {

        var possibilities = new ArrayList<String>();
        var morse = getMorseCodeCharacteres();

        setPossibilities(signals, possibilities, morse);

        return possibilities;
    }

    private static void setPossibilities(String signals, ArrayList<String> possibilities, Map<String, String> morse) {

        var countUnknownChars = getCountOfUnknownChars(signals.toCharArray());

        // run via morse codes that exists
        for (var entry : morse.entrySet()) {

            if (signals.equals(entry.getValue())) {
                possibilities.add(entry.getKey());
            }

            // If the length of signals and morse code are equals, it means that may exist compatibility between them.
            if (countUnknownChars > 0 && signals.length() == entry.getValue().length()) {
                var tmpSignal = signals; // A temporary signals to receive morse code compatible
                tmpSignal = getcompatibleValue(signals, entry.getValue(), tmpSignal.indexOf('?'));


                if (tmpSignal.equals(entry.getValue()))
                    possibilities.add(entry.getKey());

            }

        }

    }

    // This method retrieve the quantity of chars '?' that exists in the array
    private static int getCountOfUnknownChars(char[] charArr) {
        var countUnknownChars  = 0;

        for (char c : charArr) {
            if (c == '?')
                countUnknownChars++;
        }

        return countUnknownChars;
    }

    // This method retrieve the morse code compatible with the signals. It will run while unknown chars exists.
    private static String getcompatibleValue(String signals, String morse, int i) {
        // This will overwrite the unknown char for the equivalent char in the morse code. The index is used to do it.
        signals = signals.substring(0, i) + morse.charAt(i) + signals.substring(i + 1);

        // If exists unknown chars yet, this method will be call recursively.
        if (signals.contains("?")) {
            signals = getcompatibleValue(signals, morse, signals.indexOf('?'));
        }

        return signals;
    }

    //This method return only the Characters with at most 3 signals.
    private static Map<String, String> getMorseCodeCharacteres() {
        var morse = new LinkedHashMap <String, String>();
        morse.put("E", ".");
        morse.put("I", "..");
        morse.put("S", "...");
        morse.put("U", "..-");
        morse.put("A", ".-");
        morse.put("R", ".-.");
        morse.put("W", ".--");
        morse.put("T", "-");
        morse.put("N", "-.");
        morse.put("D", "-..");
        morse.put("K", "-.-");
        morse.put("M", "--");
        morse.put("G", "--.");
        morse.put("O", "---");

        return morse;
    }

}
