# Notes for the Gilded Rose refactoring kata (Java)

In this README I describe how I approached this coding kata. In short:

1. Fix existing test suite (if applicable)
2. Improve test coverage -- until you feel confident enough to start refactoring
3. Refactor
4. Add new features (test-driven)

## Step 1: Understand requirements / actual workings

- Read requirements (GildedRoseRequirements.txt)
- Read existing code (src/main/java)
- Annotate peculiarities in existing code
- Fix existing tests (good practice before git push)

NB 1: Do not refactor any code at this stage (insufficient test coverage)!

NB 2: Code as crappy as GildedRose.java I always first annotate with inline comments 
(bad code quality can be an indication that the code is not according to specs).
 
## Step 2: Improve test coverage 

- Use / refactor / rewrite existing tests
- Improve coverage by introducing my own unit tests
  - At this point we might discover that the existing code doesn't work according to specs (in which case Product Owner will have to decide what to do). 
- Now we're ready to refactor the existing code

Challenge: 
Choose a test approach that doesn't require a complete overhaul after GildedRose.java is refactored. 
So this requires some thinking ahead. 

## Step 3: Refactor

- Refactor existing code

## Step 4: Add new features

Specs: "Conjured" items degrade in Quality twice as fast as normal items.

- Add tests for conjured items (they should fail)
- Implement 'conjured' feature

## Step 5: Final touch: Simplify, cleanup, etc.

- 
