/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.stax_api.client;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.*;

import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */
public class ConcurrencyTest extends TestCase {
    private static final XMLOutputFactory xof =
            XMLOutputFactory.newInstance();
    private static final XMLInputFactory xif =
            XMLInputFactory.newInstance();

    // Keep this until sjsxp in JDK disables instance reuse
    static {
        try {
            xif.setProperty("reuse-instance", false);
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }

    private static final int NO_THREADS = 10;
    private static final int PER_THREAD = 5000;

    public void test() throws Exception {
        Thread[] threads = new Thread[NO_THREADS];
        MyRunnable[] run = new MyRunnable[NO_THREADS];
        for (int i = 0; i < NO_THREADS; i++) {
            run[i] = new MyRunnable(i);
            threads[i] = new Thread(run[i]);
        }
        for (int i = 0; i < NO_THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NO_THREADS; i++) {
            threads[i].join();
        }
        for (int i = 0; i < NO_THREADS; i++) {
            Exception e = run[i].getException();
            if (e != null) {
                e.printStackTrace();
                fail("Failed with exception." + e);
            }
        }
    }

    private static class MyRunnable implements Runnable {
        final int no;
        volatile Exception e;

        MyRunnable(int no) {
            this.no = no;
        }

        public void run() {
            for (int req = 0; req < PER_THREAD && e == null; req++) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    XMLStreamWriter w = getWriter(bos);
                    //System.out.println("Writer="+w+" Thread="+Thread.currentThread());
                    w.writeStartDocument();
                    w.writeStartElement("hello");
                    for (int j = 0; j < 50; j++) {
                        w.writeStartElement("a" + j);
                        w.writeEndElement();
                    }
                    w.writeEndElement();
                    w.writeEndDocument();
                    w.close();
                    bos.close();

                    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                    XMLStreamReader r = getReader(bis);
                    while (r.hasNext()) {
                        r.next();
                    }
                    r.close();
                    bis.close();
                } catch (Exception e) {
                    this.e = e;
                }
            }
        }

        public Exception getException() {
            return e;
        }
    }

    // Should use synchronized as per API. But without it,
    // it should work with woodstox, sjsxp
    private static XMLStreamReader getReader(InputStream is)
            throws Exception {
        return xif.createXMLStreamReader(is);
    }

    // Should use synchronized as per API. But without it,
    // it should work with woodstox, sjsxp
    private static XMLStreamWriter getWriter(OutputStream os)
            throws Exception {
        return xof.createXMLStreamWriter(os);
    }

}
