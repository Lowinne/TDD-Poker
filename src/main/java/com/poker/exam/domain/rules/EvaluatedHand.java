package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;

import java.util.List;

public record EvaluatedHand(HandCategory category, List<Card> chosen5) {

    public EvaluatedHand {
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null");
        }
        if (chosen5 == null || chosen5.size() != 5) {
            throw new IllegalArgumentException("chosen5 must contain exactly 5 cards");
        }
        if (chosen5.stream().distinct().count() != 5) {
            throw new IllegalArgumentException("chosen5 must contain 5 distinct cards");
        }
        chosen5 = List.copyOf(chosen5);
    }
}