package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import java.util.List;

public record EvaluatedHand(HandCategory category, List<Card> chosen5) {}
