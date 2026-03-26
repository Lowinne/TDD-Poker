package com.poker.exam.domain.service;

import java.util.List;

public record GameResult(
        List<PlayerHandResult> playerResults,
        List<PlayerHandResult> winners
) {
}