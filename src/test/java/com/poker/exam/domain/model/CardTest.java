package com.poker.exam.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CardTest {

    @Test
    void cardsWithSameRankAndSuitShouldBeEqual() {
        // Étant donné (Arrange)
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.ACE, Suit.SPADES);

        // Alors (Assert) - Vérifie que le record gère bien l'égalité par valeur
        assertThat(card1).isEqualTo(card2);
    }

    @Test
    void aceShouldHaveHigherValueThanKing() {
        // Étant donné
        Card ace = new Card(Rank.ACE, Suit.HEARTS);
        Card king = new Card(Rank.KING, Suit.SPADES);

        // Alors
        assertThat(ace.rank().getValue()).isGreaterThan(king.rank().getValue());
    }
}
