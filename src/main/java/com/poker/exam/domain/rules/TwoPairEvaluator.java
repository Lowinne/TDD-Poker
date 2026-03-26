package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TwoPairEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        Map<Rank, Long> counts = HandUtils.countByRank(cards);

        List<Rank> pairs = HandUtils.ranksWithCountAtLeast(counts, 2);
        if (pairs.size() < 2) {
            return Optional.empty();
        }

        Rank highPair = pairs.get(0);
        Rank lowPair = pairs.get(1);

        List<Card> best5 = new ArrayList<>();
        best5.addAll(HandUtils.cardsOfRank(cards, highPair, 2));
        best5.addAll(HandUtils.cardsOfRank(cards, lowPair, 2));
        best5.addAll(HandUtils.highestCardsExcludingRanks(cards, Set.of(highPair, lowPair), 1));

        return Optional.of(new EvaluatedHand(HandCategory.TWO_PAIR, best5));
    }
}