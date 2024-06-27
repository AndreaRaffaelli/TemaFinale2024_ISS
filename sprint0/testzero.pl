%====================================================================================
% testzero description   
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
context(testctx, "localhost",  "TCP", "6969").
 qactor( wis, testctx, "it.unibo.wis.Wis").
 static(wis).
  qactor( oprobot, testctx, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( wastestorage, testctx, "it.unibo.wastestorage.Wastestorage").
 static(wastestorage).
  qactor( sonar, testctx, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( incinerator, testctx, "it.unibo.incinerator.Incinerator").
 static(incinerator).
  qactor( monitoringdevice, testctx, "it.unibo.monitoringdevice.Monitoringdevice").
 static(monitoringdevice).
