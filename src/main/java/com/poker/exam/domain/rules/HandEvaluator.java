package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandEvaluator {

    public EvaluatedHand evaluate(List<Card> cards) {
        Map<Rank, Long> rankCounts = cards.stream()
                .collect(Collectors.groupingBy(Card::rank, Collectors.counting()));

        boolean hasPair = rankCounts.values().stream().anyMatch(count -> count >= 2);

        if (hasPair) {
            Rank pairRank = rankCounts.entrySet().stream()
                    .filter(entry -> entry.getValue() >= 2)
                    .map(Map.Entry::getKey)
                    .max(Comparator.comparingInt(Rank::getValue))
                    .orElseThrow();

            List<Card> pairCards = cards.stream()
                    .filter(c -> c.rank() == pairRank)
                    .limit(2)
                    .toList();

            List<Card> kickers = cards.stream()
                    .filter(c -> c.rank() != pairRank)
                    .sorted(Comparator.comparingInt((Card c) -> c.rank().getValue()).reversed())
                    .limit(3)
                    .toList();

            List<Card> best5 = new ArrayList<>(pairCards);
            best5.addAll(kickers);

            return new EvaluatedHand(HandCategory.ONE_PAIR, best5);
        }

        List<Card> best5 = cards.stream()
                .sorted(Comparator.comparingInt((Card c) -> c.rank().getValue()).reversed())
                .limit(5)
                .toList();

        return new EvaluatedHand(HandCategory.HIGH_CARD, best5);
    }
}