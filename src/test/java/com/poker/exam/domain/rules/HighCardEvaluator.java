package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HighCardEvaluatorTest {

    @Test
    void shouldReturnTopFiveCardsInDescendingOrder() {
        List<Card> cards = List.of(
                new Card(Rank.TWO, Suit.CLUBS),
                new Card(Rank.FOUR, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.JACK, Suit.DIAMONDS),
                new Card(Rank.QUEEN, Suit.CLUBS),
                new Card(Rank.ACE, Suit.HEARTS)
        );

        HighCardEvaluator evaluator = new HighCardEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.HIGH_CARD);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.QUEEN, Suit.CLUBS),
                new Card(Rank.JACK, Suit.DIAMONDS),
                new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.SEVEN, Suit.HEARTS)
        );
    }
}