package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HandEvaluatorTest {

    @Test
    void shouldIdentifyHighCardAndReturnTop5Cards() {
        List<Card> sevenCards = List.of(
                new Card(Rank.TWO, Suit.CLUBS),
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.JACK, Suit.CLUBS),
                new Card(Rank.NINE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.FIVE, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES)
        );

        HandEvaluator evaluator = new HandEvaluator();

        EvaluatedHand result = evaluator.evaluate(sevenCards);

        assertThat(result.category()).isEqualTo(HandCategory.HIGH_CARD);

        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.JACK, Suit.CLUBS),
                new Card(Rank.NINE, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.SPADES)
        );
    }
}
