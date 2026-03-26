package com.poker.exam.domain.model;

import java.util.List;

public record Player(String name, List<Card> holeCards) {

    public Player {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name must not be blank");
        }
        if (holeCards == null || holeCards.size() != 2) {
            throw new IllegalArgumentException("A player must have exactly 2 hole cards");
        }
        if (holeCards.stream().distinct().count() != 2) {
            throw new IllegalArgumentException("Hole cards must be distinct");
        }
        holeCards = List.copyOf(holeCards);
    }
}