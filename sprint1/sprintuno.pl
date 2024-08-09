%====================================================================================
% sprintuno description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
dispatch( startBurn, startBurn(X) ).
dispatch( info, info(X,Y,Z) ).
request( engage, engage(CALLER) ).
request( doplan, doplan(PATH,STEPTIME) ).
event( burnEnd, burnEnd(ARG) ).
event( startIncinerator, startIncinerator(X) ).
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
%====================================================================================
context(ctxtest, "localhost",  "TCP", "6969").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wis, ctxtest, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, ctxtest, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxtest, "it.unibo.incinerator.Incinerator").
 static(incinerator).
