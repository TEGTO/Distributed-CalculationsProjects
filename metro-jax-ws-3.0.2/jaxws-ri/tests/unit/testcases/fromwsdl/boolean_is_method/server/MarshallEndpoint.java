/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.boolean_is_method.server;

/**
 * @author Jitendra Kotamraju
 */
@jakarta.jws.WebService(endpointInterface = "fromwsdl.boolean_is_method.server.MarshallTest")
public class MarshallEndpoint implements MarshallTest {

    public JavaBean javaBeanTest(JavaBean bean) {
        return bean;
    }

}
