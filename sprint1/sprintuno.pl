%====================================================================================
% sprintuno description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
dispatch( burnStart, burnStart(X) ).
event( burnEnd, burnEnd(X) ).
event( startIncinerator, startIncinerator(X) ).
request( engage, engage(CALLER) ).
request( doplan, doplan(PATH,STEPTIME) ).
%====================================================================================
context(ctxservicearea, "localhost",  "TCP", "8001").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wis, ctxservicearea, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, ctxservicearea, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxservicearea, "it.unibo.incinerator.Incinerator").
 static(incinerator).
