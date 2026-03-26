package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;

import java.util.List;
import java.util.Optional;

public class StraightEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        return HandUtils.detectStraightRanks(cards)
                .map(ranks -> new EvaluatedHand(
                        HandCategory.STRAIGHT,
                        HandUtils.pickCardsForOrderedRanks(cards, ranks)
                ));
    }
}