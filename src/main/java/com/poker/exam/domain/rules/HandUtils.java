package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

final class HandUtils {

    private HandUtils() {
    }

    static final Comparator<Card> BY_RANK_DESC =
            Comparator.comparingInt((Card c) -> c.rank().getValue()).reversed();

    static Map<Rank, Long> countByRank(List<Card> cards) {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::rank, Collectors.counting()));
    }

    static Map<Suit, List<Card>> groupBySuit(List<Card> cards) {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::suit));
    }

    static List<Card> cardsOfRank(List<Card> cards, Rank rank, int limit) {
        return cards.stream()
                .filter(card -> card.rank() == rank)
                .limit(limit)
                .toList();
    }

    static List<Card> highestCardsExcludingRanks(List<Card> cards, Set<Rank> excluded, int limit) {
        return cards.stream()
                .filter(card -> !excluded.contains(card.rank()))
                .sorted(BY_RANK_DESC)
                .limit(limit)
                .toList();
    }

    static List<Rank> ranksWithCountAtLeast(Map<Rank, Long> counts, long minCount) {
        return counts.entrySet().stream()
                .filter(entry -> entry.getValue() >= minCount)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparingInt(Rank::getValue).reversed())
                .toList();
    }

    static Optional<List<Rank>> detectStraightRanks(List<Card> cards) {
        Set<Integer> values = cards.stream()
                .map(card -> card.rank().getValue())
                .collect(Collectors.toSet());

        if (values.contains(14)) {
            values.add(1);
        }

        for (int high = 14; high >= 5; high--) {
            boolean straight = true;
            for (int delta = 0; delta < 5; delta++) {
                if (!values.contains(high - delta)) {
                    straight = false;
                    break;
                }
            }
            if (straight) {
                List<Rank> ordered = new ArrayList<>();
                for (int delta = 0; delta < 5; delta++) {
                    ordered.add(Rank.fromValue(high - delta));
                }
                return Optional.of(ordered);
            }
        }

        return Optional.empty();
    }

    static List<Card> pickCardsForOrderedRanks(List<Card> cards, List<Rank> orderedRanks) {
        List<Card> chosen = new ArrayList<>();
        Set<Card> used = new HashSet<>();

        for (Rank rank : orderedRanks) {
            Card picked = cards.stream()
                    .filter(card -> card.rank() == rank)
                    .filter(card -> !used.contains(card))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Missing rank " + rank));
            chosen.add(picked);
            used.add(picked);
        }

        return chosen;
    }

    static List<Integer> tieBreakVector(EvaluatedHand hand) {
        return hand.chosen5().stream()
                .map(card -> {
                    if (hand.category() == HandCategory.STRAIGHT || hand.category() == HandCategory.STRAIGHT_FLUSH) {
                        List<Card> chosen = hand.chosen5();
                        boolean wheel = chosen.get(0).rank() == Rank.FIVE
                                && chosen.get(1).rank() == Rank.FOUR
                                && chosen.get(2).rank() == Rank.THREE
                                && chosen.get(3).rank() == Rank.TWO
                                && chosen.get(4).rank() == Rank.ACE;
                        if (wheel && card.rank() == Rank.ACE) {
                            return 1;
                        }
                    }
                    return card.rank().getValue();
                })
                .toList();
    }
}