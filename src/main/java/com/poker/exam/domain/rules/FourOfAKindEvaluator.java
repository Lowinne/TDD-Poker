package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FourOfAKindEvaluator implements CategoryEvaluator {

    @Override
    public Optional<EvaluatedHand> evaluate(List<Card> cards) {
        Map<Rank, Long> counts = HandUtils.countByRank(cards);

        List<Rank> quads = HandUtils.ranksWithCountAtLeast(counts, 4);
        if (quads.isEmpty()) {
            return Optional.empty();
        }

        Rank quadRank = quads.get(0);

        List<Card> best5 = new ArrayList<>();
        best5.addAll(HandUtils.cardsOfRank(cards, quadRank, 4));
        best5.addAll(HandUtils.highestCardsExcludingRanks(cards, Set.of(quadRank), 1));

        return Optional.of(new EvaluatedHand(HandCategory.FOUR_OF_A_KIND, best5));
    }
}