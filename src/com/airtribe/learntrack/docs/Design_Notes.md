# Design Notes

## Why ArrayList instead of an array?
`ArrayList` grows and shrinks dynamically as students, courses, and enrollments are added. A regular array has a fixed length, so adding or removing entries requires more manual handling. 

## Where are static members used and why?
`IdGenerator` uses private static counters and public static methods. IDs are generated centrally, so each new entity receives the next ID without needing an `IdGenerator` object. The constructor is private because the class is a utility class.

## Where is inheritance used and what is gained?
`Student` extend `Person`, reusing common fields such as ID, first name, last name, and email. Both classes override `getDisplayName()` to provide specialized output. This demonstrates inheritance and runtime polymorphism and avoids duplicating common person fields.

## Separation of responsibilities
- Entity classes represent data and basic entity behavior.
- Service classes validate operations and manage in-memory collections.
- `LearnTrackApplication - Main method` handles menu display, input, and user-facing messages.
- Utility classes handle ID generation and reusable validation.
- Custom exceptions distinguish missing entities from invalid user input.

## Limitations
Data is stored only in memory, so it is cleared when the application exits. 