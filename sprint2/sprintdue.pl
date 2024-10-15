%====================================================================================
% sprintdue description   
%====================================================================================
dispatch( info, info(X,Y,Z) ).
event( startIncinerator, startIncinerator(X) ).
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
event( sonardata, distance(D) ).
%====================================================================================
context(ctxservicearea, "127.0.0.1",  "TCP", "6969").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxashstorage, "localhost",  "TCP", "8021").
 qactor( wis, ctxservicearea, "external").
  qactor( oprobot, ctxservicearea, "external").
  qactor( incinerator, ctxservicearea, "external").
  qactor( monitoring_device, ctxashstorage, "it.unibo.monitoring_device.Monitoring_device").
 static(monitoring_device).
  qactor( sonardevice, ctxashstorage, "it.unibo.sonardevice.Sonardevice").
 static(sonardevice).
  qactor( datacleaner, ctxashstorage, "it.unibo.datacleaner.Datacleaner").
 static(datacleaner).
