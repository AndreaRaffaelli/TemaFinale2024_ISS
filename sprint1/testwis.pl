%====================================================================================
% testwis description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
event( burnEnd, burnEnd(ARG) ).
dispatch( info, info(N,VAR,VAL) ).
request( testRequest, testRequest(X) ).
%====================================================================================
context(ctxtest, "localhost",  "TCP", "6969").
 qactor( wis, ctxtest, "it.unibo.wis.Wis").
 static(wis).
  qactor( test_observer, ctxtest, "it.unibo.test_observer.Test_observer").
 static(test_observer).
  qactor( oprobot, ctxtest, "it.unibo.oprobot.Oprobot").
 static(oprobot).
