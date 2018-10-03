# SchedulingSolver

## Grammar
```
[ ] - optional group
( ) - non-optional group
| - OR
:= - defined as

NODE := <MONTH>|<DATE>|<DAY>|<DURATION>|<TIME>|<SCHEDULE>|<String>
LET := LET <String> = <NODE>
MONTH := January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec
DATE := <MONTH> <Num>[st|th] | <Num>[st|th] OF <MONTH>
DAY := Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|Mon|Tues|Wed|Thur|Fri|Sat|Sun|<DATE>
DURATION := <Num> (hours|minutes|seconds)
TIME := <Num>[ ][AM|PM|am|pm]
SCHEDULE := SCHEDULE <String> AT <TIME> [FOR <DURATION>] [AT LOCATION <String>] [ON [EVERY ]<DAY> [AND <DAY>]*  [UNTIL <DATE>]]
```

## Notes From TA 
- Criteria for assesment: abstraction
- need to add variables and/or control flow
- Ideas from TA
    - (variables) `Guests -> x -> asdaf@adfa.com, asdfasd@bbbb.com, ...`
    - Segregate events so that guests can't see each other?
