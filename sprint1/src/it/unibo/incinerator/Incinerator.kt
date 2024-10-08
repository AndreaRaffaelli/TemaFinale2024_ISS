/* Generated by AN DISI Unibo */ 
package it.unibo.incinerator

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.sysUtil.createActor   //Sept2023

//User imports JAN2024

class Incinerator ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		
		        val BTIME = 3000L;
		        var start = "off";
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outyellow("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						delay(1000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t018",targetState="start",cond=whenEvent("startIncinerator"))
				}	 
				state("start") { //this:State
					action { //it:State
						CommUtils.outyellow("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outmagenta("($name): Inizio inceneritore")
						 start ="on";  
						updateResourceRep( "info($name,start,$start)"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t119",targetState="handleBurnStart",cond=whenDispatch("startBurn"))
				}	 
				state("handleBurnStart") { //this:State
					action { //it:State
						CommUtils.outyellow("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outmagenta("($name): Inizio bruciatura")
						delay(2000) 
						CommUtils.outmagenta("($name): Fine bruciatura")
						 start ="off";  
						updateResourceRep( "info($name,start,$start)"  
						)
						emit("burnEnd", "burnEnd(stop)" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t220",targetState="handleBurnStart",cond=whenDispatch("startBurn"))
				}	 
			}
		}
} 
