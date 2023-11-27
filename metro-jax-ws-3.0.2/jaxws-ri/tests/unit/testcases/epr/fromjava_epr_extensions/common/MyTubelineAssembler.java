/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.fromjava_epr_extensions.common;

import com.sun.xml.ws.util.pipe.StandaloneTubeAssembler;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class MyTubelineAssembler extends StandaloneTubeAssembler{
    @Override
    public Tube createServer(ServerTubeAssemblerContext context) {
        return new MyTube(context,super.createServer(context));
    }
}
