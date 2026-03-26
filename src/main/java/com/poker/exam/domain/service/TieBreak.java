package com.poker.exam.domain.service;

import com.poker.exam.domain.rules.EvaluatedHand;
import com.poker.exam.domain.rules.HandCategory;

import java.util.List;

public class TieBreak {

    public int compare(EvaluatedHand left, EvaluatedHand right) {
        int categoryComparison = Integer.compare(
                left.category().strength(),
                right.category().strength()
        );

        if (categoryComparison != 0) {
            return categoryComparison;
        }

        List<Integer> leftVector = tieBreakVector(left);
        List<Integer> rightVector = tieBreakVector(right);

        for (int i = 0; i < leftVector.size(); i++) {
            int cmp = Integer.compare(leftVector.get(i), rightVector.get(i));
            if (cmp != 0) {
                return cmp;
            }
        }

        return 0;
    }

    private List<Integer> tieBreakVector(EvaluatedHand hand) {
        if (hand.category() == HandCategory.STRAIGHT || hand.category() == HandCategory.STRAIGHT_FLUSH) {
            boolean wheel = hand.chosen5().get(0).rank().getValue() == 5
                    && hand.chosen5().get(1).rank().getValue() == 4
                    && hand.chosen5().get(2).rank().getValue() == 3
                    && hand.chosen5().get(3).rank().getValue() == 2
                    && hand.chosen5().get(4).rank().getValue() == 14;

            if (wheel) {
                return List.of(5, 4, 3, 2, 1);
            }
        }

        return hand.chosen5().stream()
                .map(card -> card.rank().getValue())
                .toList();
    }
}