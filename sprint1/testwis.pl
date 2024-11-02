%====================================================================================
% testwis description   
%====================================================================================
dispatch( robotStart, robotStart(X) ).
dispatch( robotUpdate, robotUpdate(X) ).
event( burnEnd, burnEnd(ARG) ).
%====================================================================================
context(ctxtest, "localhost",  "TCP", "6969").
 qactor( wis, ctxtest, "it.unibo.wis.Wis").
 static(wis).
  qactor( test, ctxtest, "it.unibo.test.Test").
 static(test).
