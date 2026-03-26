package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;

import java.util.List;
import java.util.Optional;

public class HandEvaluator {

    private final List<CategoryEvaluator> evaluators = List.of(

            new OnePairEvaluator(),
            new HighCardEvaluator()
    );

    public EvaluatedHand evaluate(List<Card> cards) {
        if (cards == null || cards.size() != 7) {
            throw new IllegalArgumentException("HandEvaluator expects exactly 7 cards");
        }

        for (CategoryEvaluator evaluator : evaluators) {
            Optional<EvaluatedHand> result = evaluator.evaluate(cards);
            if (result.isPresent()) {
                return result.get();
            }
        }

        throw new IllegalStateException("Impossible d'évaluer la main");
    }
}