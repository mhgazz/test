package com.demo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mediator class for interacting with Nashorn engine
 * maintains its own context in order to avoid variable overlapping
 * between different requests
 * @author mariano
 *
 */

public class NashornMed {

	private static final Logger logger =
			LoggerFactory.getLogger(NashornMed.class);
	private ScriptEngineManager engineManager;
	private ScriptEngine engine;
	
	/**
	 * this code initializes the Nashorn context for the
	 * instance of the mediator
	 * private variable context
	 */
	
	public NashornMed()	{
		logger.debug("initializing server-side javascript");
		engineManager = new ScriptEngineManager();
	    engine = engineManager.getEngineByName("nashorn");
        ScriptContext newContext = new SimpleScriptContext();
        newContext.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
	}
	
	/**
	 * executes javascript
	 * @param script
	 */
	public void execute (String script)  {

	    try {
			engine.eval(script);
	    } catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * returns variable values from javascript context
	 * @param script
	 * @return
	 */
	public String getValue (String script)  {

	    try {
			Object obj = engine.eval(script);
			return obj!=null?obj.toString():null;
	    } catch (ScriptException e) {
			e.printStackTrace();
		}
		return script;
	}
	
	/**
	 * add variables to javascript context
	 * @param bindings
	 */
	public void setVariables(HashMap bindings) {
		
		if (bindings!=null && !bindings.isEmpty()) {
			 Set<String> keys = bindings.keySet();
			 Iterator<String> it = keys.iterator();
			 while (it.hasNext()) {
				 String key = it.next();
				 String value = (String) bindings.get(key);
				 engineManager.put(key, value);
			 }	 
		}
		
	}



}
