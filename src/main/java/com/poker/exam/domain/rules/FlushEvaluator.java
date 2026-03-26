package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;

import java.util.List;
import java.util.Optional;

public class FlushEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        return HandUtils.groupBySuit(cards).values().stream()
                .filter(suitedCards -> suitedCards.size() >= 5)
                .map(suitedCards -> suitedCards.stream()
                        .sorted(HandUtils.BY_RANK_DESC)
                        .limit(5)
                        .toList())
                .map(best5 -> new EvaluatedHand(HandCategory.FLUSH, best5))
                .findFirst();
    }
}