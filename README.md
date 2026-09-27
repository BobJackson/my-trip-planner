# My Trip Planner

An AI-powered trip planning application built with Spring Boot and Spring AI. The system uses a multi-agent architecture to research destinations, estimate budgets, create itineraries, and review travel plans.

## Features

- **Multi-Agent Architecture**: Four specialized AI agents work together to create comprehensive trip plans
  - **Research Agent**: Gathers information about destinations, food options, and practical travel details
  - **Budget Agent**: Estimates realistic costs for accommodation, transportation, food, and activities
  - **Itinerary Agent**: Creates day-by-day travel plans within budget constraints
  - **Review Agent**: Validates the plan for consistency, feasibility, and budget compliance

- **RESTful API**: Simple HTTP endpoint for trip planning requests
- **AI-Powered**: Uses DeepSeek's language models for intelligent trip planning
- **Budget-Conscious**: Ensures all plans stay within the specified budget
- **Structured Output**: Returns structured JSON responses with research, budget, itinerary, and review data

## Tech Stack

- **Java 25**
- **Spring Boot 4.1.1**
- **Spring AI 2.0.1** with DeepSeek integration
- **Hutool 5.8.40** - Java utility library
- **Lombok** - Reduce boilerplate code
- **Gradle** - Build tool

## Prerequisites

- Java 25 or higher
- Gradle 8.x
- DeepSeek API Key

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd my-trip-planner
```

2. Set up the DeepSeek API key as an environment variable:
```bash
export DEEPSEEK_API_KEY=your_api_key_here
```

3. Build the project:
```bash
./gradlew build
```

4. Run the application:
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## Usage

### API Endpoint

**POST** `/api/trips/plan`

### Request Body

```json
{
  "destination": "Tokyo, Japan",
  "numbersOfDays": 5,
  "budget": 2000.00,
  "preferences": "Interested in technology, food, and cultural experiences"
}
```

### Example using cURL

```bash
curl -X POST http://localhost:8080/api/trips/plan \
  -H "Content-Type: application/json" \
  -d '{
    "destination": "Tokyo, Japan",
    "numbersOfDays": 5,
    "budget": 2000.00,
    "preferences": "Interested in technology, food, and cultural experiences"
  }'
```

### Response

```json
{
  "request": {
    "destination": "Tokyo, Japan",
    "numbersOfDays": 5,
    "budget": 2000.00,
    "preferences": "Interested in technology, food, and cultural experiences"
  },
  "research": { ... },
  "budget": { ... },
  "itinerary": { ... },
  "review": { ... },
  "status": "completed"
}
```

## Configuration

The application configuration is in `src/main/resources/application.yaml`:

```yaml
spring:
  application:
    name: my-trip-planner
  ai:
    deepseek:
      api-key: ${DEEPSEEK_API_KEY}
      chat:
        model: deepseek-flash

logging:
  level:
    root: INFO
    com.wangyousong.practice.mytripplanner: DEBUG
    org.springframework.ai.chat.client.advisor: DEBUG
```

### Environment Variables

- `DEEPSEEK_API_KEY`: Your DeepSeek API key (required)

## Project Structure

```
my-trip-planner/
├── src/main/java/com/wangyousong/practice/mytripplanner/
│   ├── configuration/
│   │   └── AgentConfiguration.java    # ChatClient bean configurations
│   ├── controller/
│   │   └── TripController.java        # REST API endpoint
│   ├── dto/
│   │   ├── TripRequest.java           # Request DTO
│   │   ├── TripPlanningResponse.java  # Response DTO
│   │   ├── TripState.java             # Internal state DTO
│   │   ├── ResearchResult.java        # Research agent output
│   │   ├── BudgetResult.java          # Budget agent output
│   │   ├── ItineraryResult.java       # Itinerary agent output
│   │   └── ReviewResult.java          # Review agent output
│   ├── sevice/
│   │   ├── Agent.java                 # Agent interface
│   │   └── impl/
│   │       ├── ResearchAgent.java     # Research implementation
│   │       ├── BudgetAgent.java       # Budget implementation
│   │       ├── ItineraryAgent.java    # Itinerary implementation
│   │       ├── ReviewAgent.java       # Review implementation
│   │       └── TripPlanningOrchestrator.java  # Orchestrates all agents
│   └── MyTripPlannerApplication.java  # Main application class
└── src/main/resources/
    └── application.yaml               # Application configuration
```

## Architecture

The application follows a multi-agent pattern:

1. **TripPlanningOrchestrator** coordinates the workflow
2. Each agent receives the current `TripState` and produces its specific output
3. The state is updated as each agent completes
4. The final response aggregates all agent outputs

### Agent Workflow

```
TripRequest
    ↓
Research Agent → ResearchResult
    ↓
Budget Agent → BudgetResult
    ↓
Itinerary Agent → ItineraryResult
    ↓
Review Agent → ReviewResult
    ↓
TripPlanningResponse
```

## Development

### Running Tests

```bash
./gradlew test
```

### Clean Build

```bash
./gradlew clean build
```

## License

This project is for educational purposes.

## Author

Wang Yousong
