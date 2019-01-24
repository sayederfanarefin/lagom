package com.sonartrading.crypto.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import com.sonartrading.crypto.api.CryptoService;

/**
 * The module that binds the CryptoService so that it can be served.
 */
public class CryptoModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(CryptoService.class, CryptoServiceImpl.class);
    }
}
