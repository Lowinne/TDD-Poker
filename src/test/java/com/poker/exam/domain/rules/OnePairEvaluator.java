package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OnePairEvaluatorTest {

    @Test
    void shouldReturnHighestPairAndTopThreeKickers() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.TWO, Suit.DIAMONDS)
        );

        OnePairEvaluator evaluator = new OnePairEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.ONE_PAIR);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.NINE, Suit.CLUBS)
        );
    }
}