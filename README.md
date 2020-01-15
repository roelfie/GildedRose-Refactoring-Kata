# Notes for the Gilded Rose refactoring kata (Java)

The main purpose of this README is to give you insight into what went through my mind when I was doing this coding kata. 

- Click on [my name (roelfie)](https://github.com/roelfie/GildedRose-Refactoring-Kata/commits?author=roelfie) to view 
all my commits on this refactoring kata.
- I have created a [branch (refactor-goblin)](https://github.com/roelfie/GildedRose-Refactoring-Kata/tree/refactor-goblin) 
in which I have refactored the Goblin's code (based on steps 1-4 in which I refactored the GildedRose class and added 
the new 'conjured' feature).

My approach to this kata was, in short:

1. Fix existing test suite (if applicable)
2. Improve test coverage (until I felt confident enough to start refactoring)
3. Refactor
4. Add new features (test-driven)
5. 

## Step 1: Understand requirements / actual workings

- Read requirements
- Read existing code (and add inline comments)
- Fix existing tests (good practice before git push)

NB 1: Do not refactor any code at this stage (insufficient test coverage)!

NB 2: Code as crappy as GildedRose.java I always first annotate with inline comments 
(bad code quality can be an indication that the code is not according to specs).
 
## Step 2: Improve test coverage 

- Refactor / rewrite / delete existing tests (in this case I just deleted) 
- Improve coverage by introducing my own unit tests
  - At this point we might discover that the existing code doesn't work according to specs 
  (in which case Product Owner will have to decide what to do). See my findings below. 
- Now we're ready to refactor the existing code

Challenges: 
- Choose a test approach that doesn't require a complete overhaul after GildedRose.java is refactored. The JUnit 
@ParameterizedTest turned out to be a good approach, as it separates the test data (CSV file) completely from the 
implementation, and refactorings will only affect the 'plumbing' classes (QualityTestCase, QualityTestCaseAggregator).

Findings:
- Aged Brie quality improves twice as fast after the 'sell by' date. And once it reaches quality 50 it keeps that 
quality forever. Doesn't seem right? Check with Product Owner!
- There were 3 tests in TexttestFixture.java (+5 Dexterity Vest, Elixir of the Mongoose, Conjured Mana Cake) that I've 
copied over to the new Junit test. Keep or remove?

## Step 3: Refactor

- In this case the original code (GildedRose.updateQuality()) was so crappy, that a rewrite was better than refactoring:
  - Because of high complexity, a refactoring would require multiple cycles. A rewrite was simply faster.
  - Danger of quirks ending up in the new code after refactoring. Sometimes it's better to start with a clean sheet.
  - The high-coverage test suite gave me the confidence to do a rewrite. 

I didn't touch the Goblin's code in this step. I will refactor his code in a later step.

## Step 4: Add new features

Specs: "Conjured" items degrade in Quality twice as fast as normal items.

- Add tests for conjured items (which initially fail)
- Implement 'conjured' feature

## Step 5: Refactor Goblin's code

- Make Item immutable. No public fields.
  - Ensures that an item can not be messed with. The only public operation on an item is: ask for next day's item.
- No more logic based on item.name. Use OOP concepts instead to model the characteristics of all the different items 
(like quality increase of Aged Cheese; and exotic quality calculations for Backstage Passes; ..).
  - This also allows you to introduce other sorts of aged cheese (not only Brie), and other sorts of backstage passes, etc.
- Moving logic to where you would naturally expect it, also makes it easier to test and maintain it. Examples:
  - The fact that the 'sellIn' value of a legendary item never changes, can now be tested in LegendaryItemTest.java.
  - The fact that the quality of a backstage pass drops to 0 after the concert date, can be tested in BackstagePassTest.java (to do)
  - The fact that the quality of a aged cheese increases over time, can be tested in AgedCheeseTest.java (to do)

