package com.poker.exam.domain.rules;

import com.poker.exam.domain.model.Card;
import java.util.List;
import java.util.Optional;

public interface CategoryEvaluator {
    Optional<EvaluatedHand> evaluate(List<Card> cards);
}
