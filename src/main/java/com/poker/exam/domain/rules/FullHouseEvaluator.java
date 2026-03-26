package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FullHouseEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        Map<Rank, Long> counts = HandUtils.countByRank(cards);

        List<Rank> trips = HandUtils.ranksWithCountAtLeast(counts, 3);
        if (trips.isEmpty()) {
            return Optional.empty();
        }

        Rank tripRank = trips.get(0);

        Rank pairRank = counts.entrySet().stream()
                .filter(entry -> entry.getKey() != tripRank)
                .filter(entry -> entry.getValue() >= 2)
                .map(Map.Entry::getKey)
                .max(java.util.Comparator.comparingInt(Rank::getValue))
                .orElse(null);

        if (pairRank == null) {
            return Optional.empty();
        }

        List<Card> best5 = new ArrayList<>();
        best5.addAll(HandUtils.cardsOfRank(cards, tripRank, 3));
        best5.addAll(HandUtils.cardsOfRank(cards, pairRank, 2));

        return Optional.of(new EvaluatedHand(HandCategory.FULL_HOUSE, best5));
    }
}