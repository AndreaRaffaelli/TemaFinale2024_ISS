%====================================================================================
% sprintuno description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( sonarUpdate, sonarUpdate(QTY) ).
dispatch( robotUpdate, robotUpdate(X) ).
dispatch( info, info(X,Y,Z) ).
request( engage, engage(CALLER) ).
request( doplan, doplan(PATH,STEPTIME) ).
event( startIncinerator, startIncinerator(X) ).
event( startBurn, startBurn(X) ).
event( endBurn, endBurn(ARG) ).
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
%====================================================================================
context(ctxservicearea, "localhost",  "TCP", "8022").
context(ctxashstorage, "192.168.137.129",  "TCP", "8021").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wis, ctxservicearea, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, ctxservicearea, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxservicearea, "it.unibo.incinerator.Incinerator").
 static(incinerator).
