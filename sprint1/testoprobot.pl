%====================================================================================
% testoprobot description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
dispatch( startBurn, startBurn(X) ).
dispatch( info, info(X,Y,Z) ).
request( engage, engage(CALLER) ).
request( doplan, doplan(PATH,STEPTIME) ).
event( burnEnd, burnEnd(ARG) ).
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
%====================================================================================
context(ctxtest, "localhost",  "TCP", "6969").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( oprobot, ctxtest, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( test_observer, ctxtest, "it.unibo.test_observer.Test_observer").
 static(test_observer).
  qactor( incinerator, ctxtest, "it.unibo.incinerator.Incinerator").
 static(incinerator).
