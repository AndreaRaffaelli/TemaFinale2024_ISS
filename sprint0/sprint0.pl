%====================================================================================
% sprint0 description   
%====================================================================================
dispatch( sonarUpdate, sonarUpdate(X) ).
dispatch( scaleUpdate, scaleUpdate(X) ).
dispatch( robotStart, robotStart(X) ).
dispatch( burnStart, burnStart(X) ).
event( burnEnd, burnEnd(X) ).
dispatch( ledOn, ledOn(X) ).
dispatch( ledOff, ledOff(X) ).
dispatch( ledBlink, ledBlink(X) ).
dispatch( decScale, decScale(X) ).
dispatch( depositRP, depositRP(X) ).
dispatch( addAsh, addAsh(X) ).
%====================================================================================
context(ctxlocale, "localhost",  "TCP", "8020").
 qactor( wis, ctxlocale, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, ctxlocale, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxlocale, "it.unibo.incinerator.Incinerator").
 static(incinerator).
  qactor( sonar, ctxlocale, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( wastestorage, ctxlocale, "it.unibo.wastestorage.Wastestorage").
 static(wastestorage).
  qactor( monitoringdevice, ctxlocale, "it.unibo.monitoringdevice.Monitoringdevice").
 static(monitoringdevice).
