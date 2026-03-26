package com.poker.exam.domain.service;

import com.poker.exam.domain.model.Player;
import com.poker.exam.domain.rules.EvaluatedHand;

public record PlayerHandResult(Player player, EvaluatedHand evaluatedHand) {
}