%====================================================================================
% sprintuno description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
dispatch( incUpdate, incUpdate(X) ).
dispatch( burnStart, burnStart(X) ).
event( burnEnd, burnEnd(X) ).
event( startIncinerator, startIncinerator(X) ).
request( engage, engage(CALLER) ).
request( doplan, doplan(PATH,STEPTIME) ).
%====================================================================================
context(ctxservicearea, "localhost",  "TCP", "8001").
 qactor( wis, ctxservicearea, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, ctxservicearea, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxservicearea, "it.unibo.incinerator.Incinerator").
 static(incinerator).
  qactor( testobserver, ctxservicearea, "it.unibo.testobserver.Testobserver").
 static(testobserver).
