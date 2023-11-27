/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.handler_processing.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used by the handlers to send them instructions and check
 * what they did during a test. Implemented as two singletons
 * so there will be one for client and for server even when
 * running in the same VM. A "doubleton."
 *
 * Actions stored as ints instead of more reasonable object
 * to keep the messages simple over the wire.
 */
public class HandlerTracker implements TestConstants {

    // change if logging necessary
    public static final boolean VERBOSE_HANDLERS = false;
    
    private static HandlerTracker clientHandlerTracker;
    private static HandlerTracker serverHandlerTracker;
    
    // instance data
    List<String> registeredHandlers;
    List<String> closedHandlers;
    List<String> destroyedHandlers;
    Map<String, Integer> handlerActions;
    Map<String, Integer> handleFaultActions;
    List<String> calledHandlers;
    
    private HandlerTracker() {
        registeredHandlers = new ArrayList<String>();
        closedHandlers = new ArrayList<String>();
        destroyedHandlers = new ArrayList<String>();
        handlerActions = new HashMap<String, Integer>();
        handleFaultActions = new HashMap<String, Integer>();
        calledHandlers = new ArrayList<String>();
    }
    
    public static HandlerTracker getClientInstance() {
        if (clientHandlerTracker == null) {
            clientHandlerTracker = new HandlerTracker();
        }
        return clientHandlerTracker;
    }
    
    public static HandlerTracker getServerInstance() {
        if (serverHandlerTracker == null) {
            serverHandlerTracker = new HandlerTracker();
        }
        return serverHandlerTracker;
    }
    
    /*
     * Called before a test to clear all information.
     */
    public void clearAll() {
        registeredHandlers.clear();
        clearClosedHandlers();
        destroyedHandlers.clear();
        handlerActions.clear();
        handleFaultActions.clear();
        clearCalledHandlers();
    }
    
    /*
     * A test may want to clear this information without clearing
     * all tracker data.
     */
    public void clearClosedHandlers() {
        closedHandlers.clear();
    }
    
    /*
     * A test may want to clear this information without clearing
     * all tracker data.
     */
    public void clearCalledHandlers() {
        calledHandlers.clear();
    }
    
    public void setHandlerAction(String name, int action) {
        handlerActions.put(name, action);
    }
    
    /*
     * The default to return if there is no action set
     * is HA_RETURN_TRUE. For programatically added handlers
     * that have no name, default action is HA_ADD_ONE.
     */
    public int getHandlerAction(String name) {
        if (name == null) {
            return HA_ADD_ONE;
        }
        Integer action = handlerActions.get(name);
        if (action == null) {
            return HA_RETURN_TRUE;
        }
        return action.intValue();
    }

    public void setHandleFaultAction(String name, int action) {
        handleFaultActions.put(name, action);
    }
    
    /*
     * The default to return if there is no action set
     * is HA_RETURN_TRUE. For programatically added handlers
     * that have no name, default action is HA_ADD_ONE.
     */
    public int getHandleFaultAction(String name) {
        if (name == null) {
            return HA_ADD_ONE;
        }
        Integer action = handleFaultActions.get(name);
        if (action == null) {
            return HA_RETURN_TRUE;
        }
        return action.intValue();
    }

    /*
     * Called when a handler is initialized
     */
    public void registerHandler(String handlerName) {
        registeredHandlers.add(checkHandlerName(handlerName));
    }
    
    /*
     * Gets the current list. List may be extra long if not
     * cleared before the handler chain is created.
     */
    public List<String> getRegisteredHandlers() {
        return registeredHandlers;
    }

    /*
     * Called when a handler has its close() method called.
     */
    public void registerClose(String name) {
        closedHandlers.add(checkHandlerName(name));
    }
    
    /*
     * Gets the current list. List may be extra long if not
     * cleared before the handler chain is created.
     */
    public List<String> getClosedHandlers() {
        return closedHandlers;
    }
    
    /*
     * Called when a handler has its destroy() method called.
     */
    public void registerDestroy(String name) {
        destroyedHandlers.add(checkHandlerName(name));
    }
    
    /*
     * Gets the current list. List may be extra long if not
     * cleared before the handler chain is created.
     */
    public List<String> getDestroyedHandlers() {
        return destroyedHandlers;
    }
    
    /*
     * Called when a handler is performing some action. Regular
     * handleMessage/Request/Response methods will register a name,
     * while handleFault will register "name_FAULT".
     */
    public void registerCalledHandler(String name) {
        calledHandlers.add(checkHandlerName(name));
    }
    
    /*
     * Gets the current list of handlers that have been
     * called (during handle method or some other time).
     */
    public List<String> getCalledHandlers() {
        return calledHandlers;
    }
    
    /*
     * Used because null names can be passed in
     */
    private String checkHandlerName(String name) {
        if (name != null) {
            return name;
        }
        return "noname";
    }

    public void info(String prefix) {
        System.out.print(prefix);
        System.out.print(": registeredH = " + registeredHandlers.size());
        System.out.print(", closedH = " + closedHandlers.size());
        System.out.print(", destroyedH = " + destroyedHandlers.size());
        System.out.print(", handlerA = " + handlerActions.size());
        System.out.print(", handleFA = " + handleFaultActions.size());
        System.out.print(", calledH = " + calledHandlers.size());
        System.out.println(", handlerActions = " + handlerActions);
    }
}
