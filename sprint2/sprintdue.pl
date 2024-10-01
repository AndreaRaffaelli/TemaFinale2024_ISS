%====================================================================================
% sprintdue description   
%====================================================================================
dispatch( info, info(X,Y,Z) ).
event( startIncinerator, startIncinerator(X) ).
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
%====================================================================================
context(ctxservicearea, "127.0.0.1",  "TCP", "6969").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxashstorage, "localhost",  "TCP", "8021").
 qactor( monitoring_device, ctxashstorage, "it.unibo.monitoring_device.Monitoring_device").
 static(monitoring_device).
