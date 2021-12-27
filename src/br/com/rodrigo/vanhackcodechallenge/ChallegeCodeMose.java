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
        for (int i = 0; i < signals.length(); i++) {
            for (var entry : morse.entrySet()) {
                if (!possibilities.contains(entry.getKey())) {
                    var tmpSignal = signals;
                    if (tmpSignal.length() == entry.getValue().length()) {
                        if (tmpSignal.charAt(i) == '?')
                            tmpSignal = getcompatibleValue(signals, entry.getValue(), i);

                        if (tmpSignal.equals(entry.getValue()))
                            possibilities.add(entry.getKey());
                    }
                }
            }
        }
    }

    private static String getcompatibleValue(String signals, String morse, int i) {
        signals = signals.substring(0, i) + morse.charAt(i) + signals.substring(i + 1);

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
