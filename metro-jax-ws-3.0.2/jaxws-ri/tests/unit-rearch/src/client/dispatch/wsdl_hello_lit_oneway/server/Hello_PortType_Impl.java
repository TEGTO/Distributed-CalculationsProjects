/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.wsdl_hello_lit_oneway.server;

import jakarta.jws.WebService;
import jakarta.jws.Oneway;



/**
 * Impl class for interface generated by wscompile -import.
 * This class will overwrite the impl class generated by wscompile.
 */
@WebService(name = "Hello", serviceName = "Hello_Service", endpointInterface="client.dispatch.wsdl_hello_lit_oneway.server.Hello")
public class Hello_PortType_Impl implements Hello {

    @Oneway
        public void hello(Hello_Type req)  {
        return;
    }

    @Oneway
        public void voidTest(VoidTest req) {
        return;
    }

    @Oneway
        public void echo(String message) {
        // Oneway methods should return response code before invoking
        // the endpoint. To test this, sleep() is required.
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
        }
        if (!message.equals("Sun Microsystems")) {
            System.out.println("ERROR: echoTest failed! Expected \"Sun Microsystems\", received null!");
        }
        return;
    }

}
