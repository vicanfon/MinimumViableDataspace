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

import org.eclipse.edc.api.auth.spi.ApiAuthenticationProvider;
import org.eclipse.edc.api.auth.spi.AuthenticationService;
import org.eclipse.edc.api.auth.spi.registry.ApiAuthenticationProviderRegistry;
import org.eclipse.edc.runtime.metamodel.annotation.Extension;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

import java.util.List;
import java.util.Map;

/**
 * Registers a no-op authentication provider so the public API can run without auth headers.
 */
@Extension(NoAuthExtension.NAME)
public class NoAuthExtension implements ServiceExtension {
    public static final String NAME = "Public API No-Auth Provider";

    @Inject
    private ApiAuthenticationProviderRegistry providerRegistry;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        providerRegistry.register("none", noAuthProvider());
    }

    private ApiAuthenticationProvider noAuthProvider() {
        return config -> Result.success(new NoAuthAuthenticationService());
    }

    private static class NoAuthAuthenticationService implements AuthenticationService {
        @Override
        public boolean isAuthenticated(Map<String, List<String>> headers) {
            return true;
        }
    }
}
