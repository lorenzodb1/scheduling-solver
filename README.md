# SchedulingSolver

## tokenizer.Grammar
```
[ ] - optional group
( ) - non-optional group
| - OR
:= - defined as

STATEMENT := <LET>|<SCHEDULE>|<FOR>
NODE := <MONTH>|<DATE>|<DAY>|<DURATION>|<TIME>|<GUEST>|<LOCATION>|<ID>|<String>
ID := $<String>
NODESET := {<NODE> (, <NODE>)*}
LET := LET <ID> = (<NODE>|<NODESET>)
FOR := FOR <ID> IN <NODESET> <STATEMENT>* ENDFOR
MONTH := January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec
DATE := <MONTH> <Num>[st|th] | <Num>[st|th] OF <MONTH>
DAY := Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|Mon|Tues|Wed|Thur|Fri|Sat|Sun|<DATE>
DURATION := <Num> (hours|minutes|seconds)
TIME := <Num>[ ][AM|PM|am|pm]
GUEST := <Any Valid Email>
LOCATION := <Any Valid Address>
SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [AT LOCATION <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH (<NODE>|<NODESET>)]
```

## Notes From TA 
- Criteria for assesment: abstraction
- need to add variables and/or control flow
- Ideas from TA
    - (variables) `Guests -> x -> asdaf@adfa.com, asdfasd@bbbb.com, ...`
    - Segregate events so that guests can't see each other?
