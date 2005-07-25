: SkipUpTo
  BEGIN
    DUP GetChar >R <> R> AND
  WHILE
    >IN 1+!
  REPEAT DROP
;

: IsDelimiter ( char -- flag )
  BL 1+ <
;

: GetChar ( -- char flag )
  EndOfChunk
  IF 0 FALSE
  ELSE PeekChar TRUE THEN
;

: OnDelimiter ( -- flag )
  GetChar SWAP IsDelimiter AND
;

: SkipDelimiters ( -- ) \ пропустить пробельные символы
  BEGIN
    OnDelimiter
  WHILE
    >IN 1+!
  REPEAT
;

: NextWord ( -- c-addr u )
  \ это слово теперь будем использовать в INTERPRET
  \ - удобнее: не использует WORD и, соответственно, не мусорит в HERE;
  \ и разделителями считает все что <=BL, в том числе TAB и CRLF
  SkipDelimiters ParseWord
  >IN 1+! \ пропустили разделитель за словом
;
