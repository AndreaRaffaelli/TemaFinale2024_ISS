%====================================================================================
% sprintuno description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
dispatch( info, info(X,Y,Z) ).
request( engage, engage(CALLER) ).
request( doplan, doplan(PATH,STEPTIME) ).
event( endBurn, endBurn(ARG) ).
event( sonarUpdate, sonarUpdate(QTY) ).
event( startIncinerator, startIncinerator(X) ).
event( startBurn, startBurn(X) ).
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
%====================================================================================
context(ctxservicearea, "localhost",  "TCP", "8022").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxashstorage, "192.168.137.129",  "TCP", "8021").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wis, ctxservicearea, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, ctxservicearea, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxservicearea, "it.unibo.incinerator.Incinerator").
 static(incinerator).
