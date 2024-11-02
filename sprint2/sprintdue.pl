%====================================================================================
% sprintdue description   
%====================================================================================
dispatch( info, info(SOURCE,VAL,VAR) ).
event( startIncinerator, startIncinerator(X) ).
request( testStart, testStart(X) ).
request( testRequest, testRequest(X) ).
event( sonardata, distance(D) ).
event( sonarUpdate, sonarUpdate(QTY) ).
%====================================================================================
context(ctxservicearea, "192.168.137.1",  "TCP", "8021").
context(ctxashstorage, "localhost",  "TCP", "8022").
 qactor( wis, ctxservicearea, "external").
  qactor( incinerator, ctxservicearea, "external").
  qactor( monitoring_device, ctxashstorage, "it.unibo.monitoring_device.Monitoring_device").
 static(monitoring_device).
  qactor( sonardevice, ctxashstorage, "it.unibo.sonardevice.Sonardevice").
 static(sonardevice).
  qactor( datacleaner, ctxashstorage, "it.unibo.datacleaner.Datacleaner").
 static(datacleaner).
