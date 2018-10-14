# SchedulingSolver

## tokenizer.Grammar
```
[ ] - optional group
( ) - non-optional group
| - OR
:= - defined as

[x] STATEMENT := <LET>|<SCHEDULE>|<FOR>
[x] NODE := <MONTH>|<DATE>|<DAY>|<DURATION>|<TIME>|<GUEST>|<LOCATION>|<ID>|<String>
[x] ID := $<String>
[x] NODESET := {<NODE> (, <NODE>)*}
[x] LET := LET <ID> = (<NODE>|<NODESET>)
[x] FOR := FOR <ID> IN <NODESET> <STATEMENT>* ENDFOR
[x] MONTH := January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec|january|february|march|april|may|june|july|august|september|october|november|december|jan|feb|mar|apr|jun|jul|aug|sept|oct|nov|dec|
[x] DATE := <MONTH> <Num>[st|th] | <Num>[st|th] OF <MONTH>
[x] DAY := Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|Mon|Tues|Wed|Thur|Fri|Sat|Sun
[x] DURATION := <Num> (hours|minutes)
[x] TIME := <Num>[ ][AM|PM|am|pm]
[x] (written, not tested) GUEST := <Any Valid Email>
[x] LOCATION := <Any Valid Address>
[ ] SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [IN <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH <NODE> [AND <NODE>]*]
```

## Notes From TA 
- Criteria for assesment: abstraction
- need to add variables and/or control flow
- Ideas from TA
    - (variables) `Guests -> x -> asdaf@adfa.com, asdfasd@bbbb.com, ...`
    - Segregate events so that guests can't see each other?

## Notes for Video
- include "Rational Reconstruction": comparison of making a schedule in google calendar vs doing it in our language
- show off cool features:
    - variables
    - for loops
    - variable scoping within for loops, 
    - several layer deep nesting of FOR loops
    - ability to add on optional extras to SCHEDULE in any order
    - several ways to setup complex calendars, can use multiple ON statements in given schedule call,
        - Can specify multiple ranges on SCHEDULE, ex: `SCHEDULE dinner AT 5pm ON EVERY Sunday AND Monday UNTIL December 1st ON Tuesday UNTIL December 4th`
        - Can use FOR loops to generate multiple SCHEDULE statements
