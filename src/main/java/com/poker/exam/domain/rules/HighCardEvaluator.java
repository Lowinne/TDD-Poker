package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HighCardEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        List<Card> best5 = cards.stream()
                .sorted(Comparator.comparingInt((Card c) -> c.rank().getValue()).reversed())
                .limit(5)
                .toList();

        return Optional.of(new EvaluatedHand(HandCategory.HIGH_CARD, best5));
    }
}
