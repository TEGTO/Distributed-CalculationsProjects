/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.inherited_annotations.server.base;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;


@WebService
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
public class EchoBase {
    public String echoA(String str) {
        return "EchoBase:"+str;
    }

    public String echoB(String str) {
        return "EchoBase:"+str;
    }

    public String echoC(String str) {
        return "EchoBase:"+str;
    }
}
