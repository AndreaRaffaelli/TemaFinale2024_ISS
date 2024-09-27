%====================================================================================
% sprintdue description   
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
context(ctxservicearea, "127.0.0.1",  "TCP", "6969").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxashstorage, "localhost",  "TCP", "8021").
 qactor( wis, ctxservicearea, "external").
  qactor( oprobot, ctxservicearea, "external").
  qactor( incinerator, ctxservicearea, "external").
  qactor( monitoring_device, ctxashstorage, "it.unibo.monitoring_device.Monitoring_device").
 static(monitoring_device).
