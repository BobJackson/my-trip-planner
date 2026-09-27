package com.wangyousong.practice.mytripplanner.dto;

public record TripPlanningResponse(
        TripRequest request,
        ResearchResult research,
        BudgetResult budget,
        ItineraryResult itinerary,
        ReviewResult review,
        String status
) {
}
