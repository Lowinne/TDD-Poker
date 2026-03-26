package com.poker.exam.domain.model;

public record Card(Rank rank, Suit suit) {

    // Surcharge optionnelle mais très pratique pour lire tes logs d'erreurs de tests !
    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }
}
