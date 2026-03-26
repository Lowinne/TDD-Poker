package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import java.util.Comparator;
import java.util.List;

public class HandEvaluator {

    public EvaluatedHand evaluate(List<Card> cards) {
        List<Card> best5 = cards.stream()
                .sorted(Comparator.comparingInt((Card c) -> c.rank().getValue()).reversed())
                .limit(5)
                .toList();

        return new EvaluatedHand(HandCategory.HIGH_CARD, best5);
    }
}
