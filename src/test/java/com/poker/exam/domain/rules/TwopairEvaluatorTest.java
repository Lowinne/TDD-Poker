package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TwoPairEvaluatorTest {

    @Test
    void shouldReturnHigherPairThenLowerPairThenKicker() {
        List<Card> cards = List.of(
                new Card(Rank.KING, Suit.CLUBS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.NINE, Suit.DIAMONDS),
                new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.FOUR, Suit.HEARTS),
                new Card(Rank.TWO, Suit.DIAMONDS)
        );

        TwoPairEvaluator evaluator = new TwoPairEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.TWO_PAIR);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.KING, Suit.CLUBS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.NINE, Suit.DIAMONDS),
                new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.ACE, Suit.CLUBS)
        );
    }
}