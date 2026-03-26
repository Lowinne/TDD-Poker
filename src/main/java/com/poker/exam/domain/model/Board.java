package com.poker.exam.domain.model;

import java.util.List;

public record Board(List<Card> communityCards) {

    public Board {
        if (communityCards == null || communityCards.size() != 5) {
            throw new IllegalArgumentException("A board must contain exactly 5 cards");
        }
        if (communityCards.stream().distinct().count() != 5) {
            throw new IllegalArgumentException("Board cards must be distinct");
        }
        communityCards = List.copyOf(communityCards);
    }
}