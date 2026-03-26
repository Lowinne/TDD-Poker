package com.poker.exam.domain.service;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import com.poker.exam.domain.rules.EvaluatedHand;
import com.poker.exam.domain.rules.HandCategory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TieBreakTest {

    private final TieBreak tieBreak = new TieBreak();

    @Test
    void shouldCompareOnePairUsingKickers() {
        EvaluatedHand jacksWithAce = new EvaluatedHand(
                HandCategory.ONE_PAIR,
                List.of(
                        new Card(Rank.JACK, Suit.CLUBS),
                        new Card(Rank.JACK, Suit.HEARTS),
                        new Card(Rank.ACE, Suit.DIAMONDS),
                        new Card(Rank.KING, Suit.SPADES),
                        new Card(Rank.NINE, Suit.CLUBS)
                )
        );

        EvaluatedHand jacksWithQueen = new EvaluatedHand(
                HandCategory.ONE_PAIR,
                List.of(
                        new Card(Rank.JACK, Suit.DIAMONDS),
                        new Card(Rank.JACK, Suit.SPADES),
                        new Card(Rank.QUEEN, Suit.HEARTS),
                        new Card(Rank.TEN, Suit.CLUBS),
                        new Card(Rank.NINE, Suit.DIAMONDS)
                )
        );

        assertThat(tieBreak.compare(jacksWithAce, jacksWithQueen)).isPositive();
    }

    @Test
    void shouldCompareWheelAsLowerThanSixHighStraight() {
        EvaluatedHand wheel = new EvaluatedHand(
                HandCategory.STRAIGHT,
                List.of(
                        new Card(Rank.FIVE, Suit.CLUBS),
                        new Card(Rank.FOUR, Suit.SPADES),
                        new Card(Rank.THREE, Suit.HEARTS),
                        new Card(Rank.TWO, Suit.DIAMONDS),
                        new Card(Rank.ACE, Suit.CLUBS)
                )
        );

        EvaluatedHand sixHigh = new EvaluatedHand(
                HandCategory.STRAIGHT,
                List.of(
                        new Card(Rank.SIX, Suit.CLUBS),
                        new Card(Rank.FIVE, Suit.DIAMONDS),
                        new Card(Rank.FOUR, Suit.HEARTS),
                        new Card(Rank.THREE, Suit.SPADES),
                        new Card(Rank.TWO, Suit.CLUBS)
                )
        );

        assertThat(tieBreak.compare(wheel, sixHigh)).isNegative();
    }

    @Test
    void shouldReturnZeroWhenHandsAreExactlyEquivalent() {
        EvaluatedHand left = new EvaluatedHand(
                HandCategory.FLUSH,
                List.of(
                        new Card(Rank.ACE, Suit.HEARTS),
                        new Card(Rank.JACK, Suit.HEARTS),
                        new Card(Rank.NINE, Suit.HEARTS),
                        new Card(Rank.SIX, Suit.HEARTS),
                        new Card(Rank.FOUR, Suit.HEARTS)
                )
        );

        EvaluatedHand right = new EvaluatedHand(
                HandCategory.FLUSH,
                List.of(
                        new Card(Rank.ACE, Suit.DIAMONDS),
                        new Card(Rank.JACK, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.DIAMONDS),
                        new Card(Rank.SIX, Suit.DIAMONDS),
                        new Card(Rank.FOUR, Suit.DIAMONDS)
                )
        );

        assertThat(tieBreak.compare(left, right)).isZero();
    }
}