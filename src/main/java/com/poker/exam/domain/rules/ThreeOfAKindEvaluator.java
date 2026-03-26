package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ThreeOfAKindEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        Map<Rank, Long> counts = HandUtils.countByRank(cards);

        List<Rank> trips = HandUtils.ranksWithCountAtLeast(counts, 3);
        if (trips.isEmpty()) {
            return Optional.empty();
        }

        Rank tripRank = trips.get(0);

        List<Card> best5 = new ArrayList<>();
        best5.addAll(HandUtils.cardsOfRank(cards, tripRank, 3));
        best5.addAll(HandUtils.highestCardsExcludingRanks(cards, Set.of(tripRank), 2));

        return Optional.of(new EvaluatedHand(HandCategory.THREE_OF_A_KIND, best5));
    }
}