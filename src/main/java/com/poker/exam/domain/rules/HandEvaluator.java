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
        for (CategoryEvaluator evaluator : evaluators) {
            Optional<EvaluatedHand> result = evaluator.evaluate(cards);
            if (result.isPresent()) {
                return result.get();
            }
        }

        // Théoriquement impossible car HighCard matche toujours
        throw new IllegalStateException("Impossible d'évaluer la main");
    }
}