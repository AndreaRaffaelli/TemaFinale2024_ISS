%====================================================================================
% testincinerator description   
%====================================================================================
dispatch( startBurn, startBurn(X) ).
event( burnEnd, burnEnd(ARG) ).
dispatch( info, info(N,VAR,VAL) ).
event( startIncinerator, startIncinerator(X) ).
%====================================================================================
context(ctxtest, "localhost",  "TCP", "6969").
 qactor( incinerator, ctxtest, "it.unibo.incinerator.Incinerator").
 static(incinerator).
