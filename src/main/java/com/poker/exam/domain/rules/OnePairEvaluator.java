package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class OnePairEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        Map<Rank, Long> counts = HandUtils.countByRank(cards);

        List<Rank> pairs = HandUtils.ranksWithCountAtLeast(counts, 2);
        if (pairs.isEmpty()) {
            return Optional.empty();
        }

        Rank pairRank = pairs.get(0);

        List<Card> best5 = new ArrayList<>();
        best5.addAll(HandUtils.cardsOfRank(cards, pairRank, 2));
        best5.addAll(HandUtils.highestCardsExcludingRanks(cards, Set.of(pairRank), 3));

        return Optional.of(new EvaluatedHand(HandCategory.ONE_PAIR, best5));
    }
}