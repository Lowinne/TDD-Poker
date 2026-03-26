package com.poker.exam.domain.service;

import com.poker.exam.domain.model.Board;
import com.poker.exam.domain.model.Card;
import com.poker.exam.domain.model.Player;
import com.poker.exam.domain.rules.EvaluatedHand;
import com.poker.exam.domain.rules.HandEvaluator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameResolver {

    private final HandEvaluator handEvaluator = new HandEvaluator();
    private final TieBreak tieBreak = new TieBreak();

    public GameResult resolve(Board board, List<Player> players) {
        if (board == null) {
            throw new IllegalArgumentException("Board must not be null");
        }
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one player");
        }

        validateNoDuplicateCards(board, players);

        List<PlayerHandResult> results = players.stream()
                .map(player -> {
                    List<Card> sevenCards = new ArrayList<>(board.communityCards());
                    sevenCards.addAll(player.holeCards());
                    EvaluatedHand hand = handEvaluator.evaluate(sevenCards);
                    return new PlayerHandResult(player, hand);
                })
                .toList();

        PlayerHandResult best = results.get(0);
        List<PlayerHandResult> winners = new ArrayList<>();
        winners.add(best);

        for (int i = 1; i < results.size(); i++) {
            PlayerHandResult challenger = results.get(i);
            int cmp = tieBreak.compare(challenger.evaluatedHand(), best.evaluatedHand());

            if (cmp > 0) {
                best = challenger;
                winners = new ArrayList<>();
                winners.add(challenger);
            } else if (cmp == 0) {
                winners.add(challenger);
            }
        }

        return new GameResult(results, winners);
    }

    private void validateNoDuplicateCards(Board board, List<Player> players) {
        Set<Card> allCards = new HashSet<>(board.communityCards());

        if (allCards.size() != board.communityCards().size()) {
            throw new IllegalArgumentException("Duplicate cards found on board");
        }

        for (Player player : players) {
            for (Card card : player.holeCards()) {
                if (!allCards.add(card)) {
                    throw new IllegalArgumentException("Duplicate card found across board/players: " + card);
                }
            }
        }
    }
}