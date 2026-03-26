package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ThreeOfAKindEvaluatorTest {

    @Test
    void shouldReturnTripsThenTwoHighestKickers() {
        List<Card> cards = List.of(
                new Card(Rank.QUEEN, Suit.CLUBS),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.QUEEN, Suit.SPADES),
                new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.FOUR, Suit.HEARTS),
                new Card(Rank.THREE, Suit.DIAMONDS)
        );

        ThreeOfAKindEvaluator evaluator = new ThreeOfAKindEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.THREE_OF_A_KIND);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.QUEEN, Suit.CLUBS),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.QUEEN, Suit.SPADES),
                new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.TEN, Suit.CLUBS)
        );
    }
}