package com.poker.exam.domain.service;

import com.poker.exam.domain.model.Board;
import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Player;
import com.poker.exam.domain.model.Rank;
import com.poker.exam.domain.model.Suit;
import com.poker.exam.domain.rules.HandCategory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameResolverTest {

    @Test
    void shouldReturnTieWhenBoardPlaysForEveryone() {
        Board board = new Board(List.of(
                new Card(Rank.FIVE, Suit.CLUBS),
                new Card(Rank.SIX, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.EIGHT, Suit.SPADES),
                new Card(Rank.NINE, Suit.DIAMONDS)
        ));

        Player player1 = new Player("P1", List.of(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.ACE, Suit.DIAMONDS)
        ));

        Player player2 = new Player("P2", List.of(
                new Card(Rank.KING, Suit.CLUBS),
                new Card(Rank.QUEEN, Suit.DIAMONDS)
        ));

        GameResolver resolver = new GameResolver();

        GameResult result = resolver.resolve(board, List.of(player1, player2));

        assertThat(result.winners()).hasSize(2);
        assertThat(result.winners().stream().map(w -> w.player().name())).containsExactly("P1", "P2");
        assertThat(result.winners().get(0).evaluatedHand().category()).isEqualTo(HandCategory.STRAIGHT);
        assertThat(result.winners().get(0).evaluatedHand().chosen5()).containsExactly(
                new Card(Rank.NINE, Suit.DIAMONDS),
                new Card(Rank.EIGHT, Suit.SPADES),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.SIX, Suit.DIAMONDS),
                new Card(Rank.FIVE, Suit.CLUBS)
        );
    }

    @Test
    void shouldUseKickerWhenQuadsAreOnBoard() {
        Board board = new Board(List.of(
                new Card(Rank.SEVEN, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.TWO, Suit.DIAMONDS)
        ));

        Player player1 = new Player("P1", List.of(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.KING, Suit.CLUBS)
        ));

        Player player2 = new Player("P2", List.of(
                new Card(Rank.QUEEN, Suit.CLUBS),
                new Card(Rank.JACK, Suit.CLUBS)
        ));

        GameResolver resolver = new GameResolver();

        GameResult result = resolver.resolve(board, List.of(player1, player2));

        assertThat(result.winners()).hasSize(1);
        assertThat(result.winners().get(0).player().name()).isEqualTo("P1");
        assertThat(result.winners().get(0).evaluatedHand().category()).isEqualTo(HandCategory.FOUR_OF_A_KIND);
        assertThat(result.winners().get(0).evaluatedHand().chosen5()).containsExactly(
                new Card(Rank.SEVEN, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.ACE, Suit.CLUBS)
        );
    }
}