%====================================================================================
% sprintzero description   
%====================================================================================
event( burnEnd, burnEnd(X) ).
event( startUp, startUp(X) ).
%====================================================================================
context(ctxservicearea, "localhost",  "TCP", "8001").
 qactor( wis, ctxservicearea, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, ctxservicearea, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxservicearea, "it.unibo.incinerator.Incinerator").
 static(incinerator).
