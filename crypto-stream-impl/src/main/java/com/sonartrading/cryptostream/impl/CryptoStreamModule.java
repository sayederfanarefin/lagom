package com.sonartrading.cryptostream.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import com.sonartrading.crypto.api.CryptoService;
import com.sonartrading.cryptostream.api.CryptoStreamService;

/**
 * The module that binds the CryptoStreamService so that it can be served.
 */
public class CryptoStreamModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        // Bind the CryptoStreamService service
        bindService(CryptoStreamService.class, CryptoStreamServiceImpl.class);
        // Bind the CryptoService client
        bindClient(CryptoService.class);
        // Bind the subscriber eagerly to ensure it starts up
        bind(CryptoStreamSubscriber.class).asEagerSingleton();
    }
}
