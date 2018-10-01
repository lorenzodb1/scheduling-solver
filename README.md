# SchedulingSolver

## Grammar
```
[ ] - optional group
( ) - non-optional group
| - OR
:= - defined as

MONTH := January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec
DATE := <MONTH> <Num>[st|th] | <Num>[st|th] OF <MONTH>
DAY := Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|Mon|Tues|Wed|Thur|Fri|Sat|Sun|<DATE>
LOCATION := <String>
DURATION := <Num> (hours|minutes|seconds)
TIME := <Num>[ ][AM|PM|am|pm]
EVENT := <String>
SCHEDULE := SCHEDULE <EVENT> AT <TIME> [FOR <DURATION>] [AT LOCATION <LOCATION>] [ON [EVERY ]<DAY> [and <DAY>]*  [UNTIL <DATE>]]
```
