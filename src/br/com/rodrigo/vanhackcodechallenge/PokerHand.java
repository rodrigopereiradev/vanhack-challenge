package br.com.rodrigo.vanhackcodechallenge;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHand {

    // This variable returns the list of hand in order to rank it. It's ordered from of the most valuable hand to the lowest.
    private final List<String> handsRanking = Arrays.asList("STRAIGHT_FLUSH", "FOUR_OF_A_KIND", "FULL_HOUSE", "FLUSH",
            "STRAIGHT", "THREE_OF_A_KIND", "TWO_PAIR", "ONE_PAIR", "HIGH_CARD");

    // This variable returns the list of cards values in order to rank it. It's ordered from of the most valuable card to the lowest.
    private final List<String> cardsRanking = Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");

    private final String hand;

    PokerHand(String hand) {
        this.hand = hand;
    }

    public Result compareWith(PokerHand hand) {

        var handRanking = this.getHandRanking();
        var otherHandRanking =  hand.getHandRanking();

        if (handRanking.equals(otherHandRanking)) {
            return getResultWhenTheRankingAreTheSame(hand, handRanking);
        }

        return handRanking < otherHandRanking ? Result.WIN : Result.LOSS;
    }

    private Result getResultWhenTheRankingAreTheSame(PokerHand otherHand, Integer rankingPosition) {
        var handValues = this.getHandMapped().get("values");
        var otherHandValues = otherHand.getHandMapped().get("values");

        if (rankingPosition == handsRanking.indexOf("ONE_PAIR") + 1)
            return resultWithHigherOnePair(otherHand);

        return resultWithHigherCardRule(handValues, otherHandValues);
    }

    private Result resultWithHigherCardRule(List<String> HandValues, List<String> otherHandValues) {
        var positionsHand = getCardsPositionsInTheCardsRanking(HandValues);
        var positionsOtherHand = getCardsPositionsInTheCardsRanking(otherHandValues);

        positionsHand.sort(Integer::compareTo);
        positionsOtherHand.sort(Integer::compareTo);

        Integer handHigher = getHigherPostionBetweenTwoHand(positionsHand,positionsOtherHand);
        Integer otherHandHigher = getHigherPostionBetweenTwoHand(positionsOtherHand,positionsHand);

        if (handHigher.equals(0) && otherHandHigher.equals(0))
            return Result.TIE;

        if (handHigher.equals(0))
            return Result.LOSS;

        if (otherHandHigher.equals(0))
            return Result.WIN;

        return handHigher < otherHandHigher ? Result.WIN : Result.LOSS;
    }

    private Result resultWithHigherOnePair(PokerHand otherHand) {
        var handMapped = this.getHandMapped();
        var otherHandMapped = otherHand.getHandMapped();
        var handPositionsMapped = getPositionsMapped(handMapped);
        var otherHandPositionsMapped = getPositionsMapped(otherHandMapped);

        Integer handKey = getValueOnePair(handPositionsMapped);
        Integer otherHandKey = getValueOnePair(otherHandPositionsMapped);

        if (handKey.equals(otherHandKey)) {
            return resultWithHigherCardRule(handMapped.get("values"), otherHandMapped.get("values"));
        }

        return handKey < otherHandKey ? Result.WIN : Result.LOSS;
    }

    private Integer getValueOnePair(Map<Integer, Integer> handPositionsMapped) {
        Integer handKey = 0;
        for (Map.Entry<Integer, Integer> entry : handPositionsMapped.entrySet()) {
            if (entry.getValue().equals(2))
                handKey = entry.getKey();
        }
        return handKey;
    }

    // This method compare the positions between hands and return the position of the highest card, case it exists.
    private Integer getHigherPostionBetweenTwoHand(List<Integer> positionsHand, List<Integer> positionsOtherHand) {
        Integer higherPostion = 0;
        for(int i = 0; i < positionsHand.size(); i++) {
            if (positionsHand.get(i) < positionsOtherHand.get(i)) {
                higherPostion = positionsHand.get(i);
                break;
            }
        }
        return higherPostion;
    }

    private Integer getHandRanking() {
        var handMap = this.getHandMapped();
        if (isStraightFlush(handMap))
            return handsRanking.indexOf("STRAIGHT_FLUSH") + 1;

        if (isFourOfKind(handMap))
            return handsRanking.indexOf("FOUR_OF_A_KIND") + 1;

        if (isFullHouse(handMap))
            return handsRanking.indexOf("FULL_HOUSE") + 1;

        if (isFlush(handMap))
            return  handsRanking.indexOf("FLUSH") + 1;

        if (isStraight(handMap))
            return  handsRanking.indexOf("STRAIGHT") + 1;

        if (isThreeOfKind(handMap))
            return handsRanking.indexOf("THREE_OF_A_KIND") + 1;

        if(isTwoPair(handMap)) {
            return handsRanking.indexOf("TWO_PAIR") + 1;
        }

        if(isOnePair(handMap)) {
            return handsRanking.indexOf("ONE_PAIR") + 1;
        }
        
        return handsRanking.indexOf("HIGH_CARD") + 1;
    }

    private boolean isStraightFlush(Map<String, List<String>> handMap) {
        var hasTheSameSuits = new HashSet<>(handMap.get("suits")).size() == 1;
        var cardPositions = getCardsPositionsInTheCardsRanking(handMap.get("values"));
        var isAValuesSequence = isAValuesSequence(cardPositions);
        return hasTheSameSuits && isAValuesSequence;
    }

    private boolean isFourOfKind(Map<String, List<String>> handMap) {
        var isFourOfKind = false;
        var positionsMap = getPositionsMapped(handMap);
        for (Map.Entry<Integer, Integer> entry : positionsMap.entrySet()) {
            if (entry.getValue() == 4) {
                isFourOfKind = true;
                break;
            }
        }
        return isFourOfKind;
    }

    private boolean isFullHouse(Map<String, List<String>> handMap) {
        var positionsMap = getPositionsMapped(handMap);
        return positionsMap.containsValue(2) && positionsMap.containsValue(3);
    }

    private boolean isFlush(Map<String, List<String>> handMap) {
        return new HashSet<>(handMap.get("suits")).size() == 1;
    }

    private boolean isStraight(Map<String, List<String>> handValues) {
        var positions = getCardsPositionsInTheCardsRanking(handValues.get("values"));
        return isAValuesSequence(positions);
    }

    private boolean isThreeOfKind(Map<String, List<String>> handMap) {
        var positionsMap = getPositionsMapped(handMap);
        return positionsMap.containsValue(3) && !positionsMap.containsValue(2);
    }

    private boolean isTwoPair(Map<String, List<String>> handMap) {
        var positionsMapped = getPositionsMapped(handMap);
        var count = getCountOfRepetitionsByPosition(positionsMapped);
        return count == 2;
    }

    private boolean isOnePair(Map<String, List<String>> handMap) {
        var positionsMapped = getPositionsMapped(handMap);
        var count = getCountOfRepetitionsByPosition(positionsMapped);
        return count == 1;
    }

    private int getCountOfRepetitionsByPosition(Map<Integer, Integer> positionsMapped) {
        var count = 0;
        for (Map.Entry<Integer, Integer> entry : positionsMapped.entrySet()) {
            if (entry.getValue().equals(2))
                count++;
        }
        return count;
    }

    // This method map the values of cards like keys and how much this values appears in the hand.
    private Map<Integer, Integer> getPositionsMapped(Map<String, List<String>> handMap) {
        var positions = getCardsPositionsInTheCardsRanking(handMap.get("values"));
        return positions.stream().collect(Collectors.toMap(i -> i, i -> 1, Integer::sum));
    }

    // This method return if the list is a sequence without differences, like "2, 3, 4, 5, 6"
    private boolean isAValuesSequence(List<Integer> cardPositions) {
        cardPositions.sort(Integer::compareTo);
        var count = 0;
        for(int i = 0; i < cardPositions.size(); i++) {
            var value = i == 0 ? cardPositions.get(i) :  cardPositions.get(i - 1) + 1;
            if (value == cardPositions.get(i))
               count++;

        }
        return count == cardPositions.size();
    }

    private List<Integer> getCardsPositionsInTheCardsRanking(List<String> cardsValues) {
        var cardPostionsOnTheRanking = new ArrayList<Integer>();
        cardsValues.forEach(v -> cardPostionsOnTheRanking.add(cardsRanking.indexOf(v) + 1));
        return cardPostionsOnTheRanking;
    }

    // This method create a Map where the key is value of a card and the values is its suit;
    private Map<String, List<String>> getHandMapped() {
        var map = new LinkedHashMap<String, List<String>>();
        var values = new ArrayList<String>();
        var suits = new ArrayList<String>();
        var strArr = this.hand.split(" ");
        Arrays.stream(strArr).forEach(s -> {
            var charArr = s.toCharArray();
            values.add(String.valueOf(charArr[0]));
            suits.add(String.valueOf(charArr[1]));
        });
        map.put("values", values);
        map.put("suits", suits);
        return map;
    }

    private String getHand() {
        return this.hand;
    }

    private void print(String s) {
        System.out.println(s);
    }

    public enum Result { TIE, WIN, LOSS }

}
