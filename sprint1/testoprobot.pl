%====================================================================================
% testoprobot description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
dispatch( startBurn, startBurn(X) ).
request( engage, engage(CALLER) ).
request( doplan, doplan(PATH,STEPTIME) ).
event( burnEnd, burnEnd(ARG) ).
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
%====================================================================================
context(ctxtest, "localhost",  "TCP", "6969").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "6970").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( oprobot, ctxtest, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( wis, ctxtest, "it.unibo.wis.Wis").
 static(wis).
  qactor( incinerator, ctxtest, "it.unibo.incinerator.Incinerator").
 static(incinerator).
