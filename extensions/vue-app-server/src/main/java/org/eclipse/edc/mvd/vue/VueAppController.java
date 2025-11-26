/*
 *  Copyright (c) 2025 Contributors
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 */
package org.eclipse.edc.mvd.vue;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.edc.spi.monitor.Monitor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Minimal controller that serves the bundled Vue application from the classpath.
 */
@Path("/ui")
public class VueAppController {
    private final String indexHtml;
    private final Monitor monitor;

    public VueAppController(Monitor monitor) {
        this.monitor = monitor;
        this.indexHtml = loadResource("web/vue-app/index.html");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getIndex() {
        return Response.ok(indexHtml).build();
    }

    @GET
    @Path("/{any:.*}")
    @Produces(MediaType.TEXT_HTML)
    public Response getIndexForNestedRoute(@PathParam("any") String ignored) {
        return getIndex();
    }

    private String loadResource(String path) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                monitor.severe("Unable to load Vue app resource from path: " + path);
                throw new IllegalStateException("Vue app could not be loaded from resources");
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            monitor.severe("Failed to load Vue app resource: " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
