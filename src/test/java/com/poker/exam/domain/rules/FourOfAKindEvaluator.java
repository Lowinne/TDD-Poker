package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FourOfAKindEvaluatorTest {

    @Test
    void shouldReturnQuadsThenBestKicker() {
        List<Card> cards = List.of(
                new Card(Rank.SEVEN, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.TWO, Suit.DIAMONDS)
        );

        FourOfAKindEvaluator evaluator = new FourOfAKindEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.FOUR_OF_A_KIND);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.SEVEN, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.ACE, Suit.CLUBS)
        );
    }
}