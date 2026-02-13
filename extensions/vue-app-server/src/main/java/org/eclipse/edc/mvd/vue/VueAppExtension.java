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

import org.eclipse.edc.runtime.metamodel.annotation.Extension;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.web.spi.WebService;
import org.eclipse.edc.web.spi.configuration.ApiContext;

/**
 * Registers a lightweight UI backed by a Vue single-page application on the connector's public API context.
 */
@Extension(value = VueAppExtension.NAME)
public class VueAppExtension implements ServiceExtension {
    public static final String NAME = "Vue App Server Extension";

    @Inject
    private WebService webService;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        Monitor monitor = context.getMonitor();
        var controller = new VueAppController(monitor);
        webService.registerResource(ApiContext.PUBLIC, controller);
        monitor.info("Vue application served at /ui on the public API context");
    }
}
