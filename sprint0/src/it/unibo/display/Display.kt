/* Generated by AN DISI Unibo */ 
package it.unibo.display

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

class Display ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		 val d = DisplayObj.create()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						observeResource("localhost","8004","ctxhello","worker","info")
						observeResource("localhost","8004","ctxhello","gay","test")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="handleinfo",cond=whenDispatch("info"))
					transition(edgeName="t01",targetState="porcodio",cond=whenDispatch("test"))
				}	 
				state("handleinfo") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outblue("$currentMsg")
						  
								 d.print( currentMsg.toString() )  
								 d.print( "$currentMsg" )  
								 d.print( currentMsg.msgContent().toString() )  
								 d.print( "${currentMsg.msgContent()}" )  		 
						if( checkMsgContent( Term.createTerm("info(SOURCE,TERM)"), Term.createTerm("info(SOURCE,TERM)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val Source = payloadArg(0) 
											   val infoMsg = payloadArg(1)	
											   val M      = "$infoMsg from $Source"			
								 d.write( M )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="handleinfo",cond=whenDispatch("info"))
					transition(edgeName="t03",targetState="porcodio",cond=whenDispatch("test"))
				}	 
				state("porcodio") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outblue("$currentMsg")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
} 
