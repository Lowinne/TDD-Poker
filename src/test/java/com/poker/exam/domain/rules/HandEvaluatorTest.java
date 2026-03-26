package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HandEvaluatorTest {

    @Test
    void shouldIdentifyOnePairAndReturnPairPlusTop3Kickers() {
        List<Card> sevenCards = List.of(
                new Card(Rank.JACK, Suit.CLUBS),
                new Card(Rank.TWO, Suit.SPADES),
                new Card(Rank.JACK, Suit.HEARTS),
                new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.FIVE, Suit.CLUBS),
                new Card(Rank.NINE, Suit.SPADES)
        );

        HandEvaluator evaluator = new HandEvaluator();

        EvaluatedHand result = evaluator.evaluate(sevenCards);

        assertThat(result.category()).isEqualTo(HandCategory.ONE_PAIR);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.JACK, Suit.CLUBS),
                new Card(Rank.JACK, Suit.HEARTS),
                new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.NINE, Suit.SPADES)
        );
    }

    @Test
    void shouldPreferStraightOverOnePair() {
        List<Card> sevenCards = List.of(
                new Card(Rank.FIVE, Suit.CLUBS),
                new Card(Rank.SIX, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.EIGHT, Suit.SPADES),
                new Card(Rank.NINE, Suit.DIAMONDS),
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.ACE, Suit.HEARTS)
        );

        HandEvaluator evaluator = new HandEvaluator();

        EvaluatedHand result = evaluator.evaluate(sevenCards);

        assertThat(result.category()).isEqualTo(HandCategory.STRAIGHT);
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.NINE, Suit.DIAMONDS),
                new Card(Rank.EIGHT, Suit.SPADES),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.SIX, Suit.DIAMONDS),
                new Card(Rank.FIVE, Suit.CLUBS)
        );
    }
}