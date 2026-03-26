package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Suit;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StraightFlushEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        Map<Suit, List<Card>> bySuit = HandUtils.groupBySuit(cards);

        return bySuit.values().stream()
                .filter(suitedCards -> suitedCards.size() >= 5)
                .map(suitedCards -> HandUtils.detectStraightRanks(suitedCards)
                        .map(ranks -> new EvaluatedHand(
                                HandCategory.STRAIGHT_FLUSH,
                                HandUtils.pickCardsForOrderedRanks(suitedCards, ranks)
                        )))
                .flatMap(Optional::stream)
                .findFirst();
    }
}