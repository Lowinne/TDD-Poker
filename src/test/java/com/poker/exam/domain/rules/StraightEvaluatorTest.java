package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StraightFlushEvaluatorTest {

    @Test
    void shouldDetectStraightFlush() {
        List<Card> cards = List.of(
                new Card(Rank.NINE, Suit.HEARTS),
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.JACK, Suit.HEARTS),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.TWO, Suit.CLUBS),
                new Card(Rank.THREE, Suit.DIAMONDS)
        );

        StraightFlushEvaluator evaluator = new StraightFlushEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.STRAIGHT_FLUSH);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.JACK, Suit.HEARTS),
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.HEARTS)
        );
    }

    @Test
    void shouldTreatRoyalFlushAsBestStraightFlush() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suit.SPADES),
                new Card(Rank.JACK, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.SPADES),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.TWO, Suit.CLUBS),
                new Card(Rank.THREE, Suit.DIAMONDS)
        );

        StraightFlushEvaluator evaluator = new StraightFlushEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.STRAIGHT_FLUSH);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.SPADES),
                new Card(Rank.JACK, Suit.SPADES),
                new Card(Rank.TEN, Suit.SPADES)
        );
    }
}