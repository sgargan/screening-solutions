# screening-solutions

Solutions to screening problems

To run, check out and execute
```bash
./gradlew test run
```
which should output 

```bash
Party Guest list (16 customers)
-----------------------------------
id: 4, name: Ian Kehoe
id: 5, name: Nora Dempsey
id: 6, name: Theresa Enright
id: 8, name: Eoin Ahearn
id: 11, name: Richard Finnegan
id: 12, name: Christina McArdle
id: 13, name: Olive Ahearn
id: 15, name: Michael Ahearn
id: 17, name: Patricia Cahill
id: 23, name: Eoin Gallagher
id: 24, name: Rose Enright
id: 26, name: Stephen McArdle
id: 29, name: Oliver Ahearn
id: 30, name: Nick Enright
id: 31, name: Alan Behan
id: 39, name: Lisa Ahearn
```

There is a coverage report also, though it's reporting incorrect coverage for 'try with resources' blocks. run 

```bash
./gradlew clean test jacocoTestReport
```
