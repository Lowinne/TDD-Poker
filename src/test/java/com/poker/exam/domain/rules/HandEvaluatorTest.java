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

        // On attend : Les 2 Valets en premier, puis l'As, le Roi et le Neuf.
        assertThat(result.chosen5()).containsExactly(
                new Card(Rank.JACK, Suit.CLUBS),
                new Card(Rank.JACK, Suit.HEARTS),
                new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.NINE, Suit.SPADES)
        );
    }
}
