%====================================================================================
% sprintdue description   
%====================================================================================
dispatch( info, info(X,Y,Z) ).
event( startIncinerator, startIncinerator(X) ).
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
%====================================================================================
context(ctxashstorage, "localhost",  "TCP", "8021").
 qactor( monitoring_device, ctxashstorage, "it.unibo.monitoring_device.Monitoring_device").
 static(monitoring_device).
  qactor( incinerator, ctxashstorage, "it.unibo.incinerator.Incinerator").
 static(incinerator).
  qactor( test_observer, ctxashstorage, "it.unibo.test_observer.Test_observer").
 static(test_observer).
