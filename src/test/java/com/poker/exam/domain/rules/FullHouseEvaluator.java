package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FullHouseEvaluatorTest {

    @Test
    void shouldReturnBestTripsThenBestPair() {
        List<Card> cards = List.of(
                new Card(Rank.KING, Suit.CLUBS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.TEN, Suit.DIAMONDS),
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.FOUR, Suit.HEARTS),
                new Card(Rank.TWO, Suit.DIAMONDS)
        );

        FullHouseEvaluator evaluator = new FullHouseEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.FULL_HOUSE);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.KING, Suit.CLUBS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.TEN, Suit.DIAMONDS),
                new Card(Rank.TEN, Suit.CLUBS)
        );
    }

    @Test
    void shouldUseSecondTripsAsPairWhenTwoTripsExist() {
        List<Card> cards = List.of(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.KING, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.TWO, Suit.DIAMONDS)
        );

        FullHouseEvaluator evaluator = new FullHouseEvaluator();

        EvaluatedHand result = evaluator.evaluate(cards).orElseThrow();

        assertThat(result.category()).isEqualTo(HandCategory.FULL_HOUSE);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.KING, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS)
        );
    }
}