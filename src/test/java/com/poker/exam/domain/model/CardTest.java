package com.poker.exam.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CardTest {

    @Test
    void cardsWithSameRankAndSuitShouldBeEqual() {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.ACE, Suit.SPADES);

        assertThat(card1).isEqualTo(card2);
    }

    @Test
    void aceShouldHaveHigherValueThanKing() {
        Card ace = new Card(Rank.ACE, Suit.HEARTS);
        Card king = new Card(Rank.KING, Suit.SPADES);

        assertThat(ace.rank().getValue()).isGreaterThan(king.rank().getValue());
    }
}
