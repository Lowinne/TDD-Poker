package com.poker.exam.domain.model;

public record Card(Rank rank, Suit suit) {

    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }
}
