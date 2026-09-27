package com.wangyousong.practice.mytripplanner.sevice.impl;

import com.wangyousong.practice.mytripplanner.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripPlanningOrchestrator {

    private final ResearchAgent researchAgent;
    private final BudgetAgent budgetAgent;
    private final ItineraryAgent itineraryAgent;
    private final ReviewAgent reviewAgent;

    public TripPlanningResponse plan(TripRequest request) {
        log.info(
                "Trip planning workflow stated: destination={}, days={}",
                request.destination(),
                request.numbersOfDays()
        );

        StopWatch sw = new StopWatch();
        sw.start();

        TripState state = TripState.start(request);

        ResearchResult research = researchAgent.execute(request);
        state = state.withResearch(research);

        BudgetResult budget = budgetAgent.execute(state);
        state = state.withBudget(budget);

        if (!budget.withinBudget()) {
            log.warn(
                    "Trip exceeds budget: requestedBudget={}, estimatedCost={}",
                    request.budget(),
                    budget.totalCost()
            );

            ReviewResult review = new ReviewResult(
                    false,
                    "Trip proposed trip exceeds the available budget.",
                    List.of("Estimated trip cost is higher than the requested budget."),
                    List.of("Reduce accommodation cost", "Remove lower-priority activities", "Use public transport")
            );
            return new TripPlanningResponse(
                    request,
                    research,
                    budget,
                    null,
                    review,
                    "BUDGET_EXCEEDED"
            );
        }

        ItineraryResult itinerary = itineraryAgent.execute(state);
        state = state.withItinerary(itinerary);

        ReviewResult review = reviewAgent.execute(state);

        sw.stop();
        log.info("Trip planning workflow completed: destination={}, executionTimeMs={},valid={}",
                request.destination(),
                sw.getTotalTimeMillis(),
                review.valid());

        return new TripPlanningResponse(
                request,
                research,
                budget,
                itinerary,
                review,
                review.valid() ? "COMPLETED" : "REVIEW_FAILED"
        );
    }
}
