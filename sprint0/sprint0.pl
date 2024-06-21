%====================================================================================
% sprint0 description   
%====================================================================================
event( sonarUpdate, sonarUpdate(X) ).
event( scaleUpdate, scaleUpdate(X) ).
dispatch( sonarUpdate, sonarUpdate(X) ).
dispatch( scaleUpdate, scaleUpdate(X) ).
%====================================================================================
context(ctxlocale, "localhost",  "TCP", "8001").
 qactor( wis, ctxlocale, "it.unibo.wis.Wis").
 static(wis).
  qactor( sonar, ctxlocale, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( wastestorage, ctxlocale, "it.unibo.wastestorage.Wastestorage").
 static(wastestorage).
  qactor( oprobot, ctxlocale, "it.unibo.oprobot.Oprobot").
 static(oprobot).
  qactor( incinerator, ctxlocale, "it.unibo.incinerator.Incinerator").
 static(incinerator).
  qactor( mdevice, ctxlocale, "it.unibo.mdevice.Mdevice").
 static(mdevice).
