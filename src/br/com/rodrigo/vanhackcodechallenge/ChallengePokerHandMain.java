package br.com.rodrigo.vanhackcodechallenge;

public class ChallengePokerHandMain {

    public static void main(String[] args) {

        var pokerHand1 = new PokerHand("2H 3H 4H 5H 6H");
        var pokerHand14 = new PokerHand("4S 5H 6H TS AC");
        var pokerHand13 = new PokerHand("KD 4S KC 3H 8S");
        var pokerHand2 = new PokerHand("2H 3H 4H 5H 6H");
        var pokerHand3 = new PokerHand("AS AH 2H AD AC");
        var pokerHand4 = new PokerHand("2S AH 2H AS AC");
        var pokerHand5 = new PokerHand("2S AH 2H AS AC");
        var pokerHand6 = new PokerHand("AS 3S 4S 8S 2S");
        var pokerHand7 = new PokerHand("2H 3H 5H 6H 7H");
        var pokerHand8 = new PokerHand("2S 3H 4H 5S 6C");
        var pokerHand9 = new PokerHand("2S 3H 4H 5S 6C");
        var pokerHand10 = new PokerHand("2S 2H 4H 5S 4C");
        var pokerHand11 = new PokerHand("2S 2H 4H 5S 4C");
        var pokerHand12 = new PokerHand("6S AD 7H 4S AS");

        var result = pokerHand1.compareWith(new PokerHand("KS AS TS QS JS"));
        System.out.println(result);
        result = pokerHand14.compareWith(new PokerHand("3S 5H 6H TS AC"));
        System.out.println(result);
        result = pokerHand13.compareWith(new PokerHand("QH 8H KD JH 8S"));
        System.out.println(result);
        result = pokerHand2.compareWith(new PokerHand("AS AD AC AH JD"));
        System.out.println(result);
        result = pokerHand3.compareWith(new PokerHand("JS JD JC JH 3D"));
        System.out.println(result);
        result = pokerHand4.compareWith(new PokerHand("JS JD JC JH AD"));
        System.out.println(result);
        result = pokerHand5.compareWith(new PokerHand("2H 3H 5H 6H 7H"));
        System.out.println(result);
        result = pokerHand6.compareWith(new PokerHand("2H 3H 5H 6H 7H"));
        System.out.println(result);
        result = pokerHand7.compareWith(new PokerHand("2S 3H 4H 5S 6C"));
        System.out.println(result);
        result = pokerHand8.compareWith(new PokerHand("3D 4C 5H 6H 2S"));
        System.out.println(result);
        result = pokerHand9.compareWith(new PokerHand("AH AC 5H 6H AS"));
        System.out.println(result);
        result = pokerHand10.compareWith(new PokerHand("AH AC 5H 6H AS"));
        System.out.println(result);
        result = pokerHand11.compareWith(new PokerHand("AH AC 5H 6H 7S"));
        System.out.println(result);
        result = pokerHand12.compareWith(new PokerHand("AH AC 5H 6H 7S"));
        System.out.println(result);
    }
}
