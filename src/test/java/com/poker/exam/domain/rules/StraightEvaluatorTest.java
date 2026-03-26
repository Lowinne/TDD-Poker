package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StraightEvaluatorTest {

    @Test
    void shouldDetectAceLowStraight() {
        List<Card> cards = List.of(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.TWO, Suit.DIAMONDS),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.FOUR, Suit.SPADES),
                new Card(Rank.FIVE, Suit.CLUBS),
                new Card(Rank.KING, Suit.DIAMONDS),
                new Card(Rank.NINE, Suit.HEARTS)
        );

        StraightEvaluator evaluator = new StraightEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.STRAIGHT);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.FIVE, Suit.CLUBS),
                new Card(Rank.FOUR, Suit.SPADES),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.TWO, Suit.DIAMONDS),
                new Card(Rank.ACE, Suit.CLUBS)
        );
    }

    @Test
    void shouldDetectAceHighStraight() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.JACK, Suit.DIAMONDS),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.THREE, Suit.DIAMONDS),
                new Card(Rank.TWO, Suit.HEARTS)
        );

        StraightEvaluator evaluator = new StraightEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.STRAIGHT);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.JACK, Suit.DIAMONDS),
                new Card(Rank.TEN, Suit.CLUBS)
        );
    }
}