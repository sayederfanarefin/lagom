package com.sonartrading.cryptostream.impl;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import com.sonartrading.crypto.api.CryptoService;
import com.sonartrading.cryptostream.api.CryptoStreamService;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Implementation of the CryptoStreamService.
 */
public class CryptoStreamServiceImpl implements CryptoStreamService {
    private final CryptoService cryptoService;
    private final CryptoStreamRepository repository;

    @Inject
    public CryptoStreamServiceImpl(CryptoService cryptoService, CryptoStreamRepository repository) {
        this.cryptoService = cryptoService;
        this.repository = repository;
    }

    @Override
    public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> directStream() {
        return hellos -> completedFuture(
                hellos.mapAsync(8, name -> cryptoService.hello(name).invoke()));
    }

    @Override
    public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> autonomousStream() {
        return hellos -> completedFuture(
                hellos.mapAsync(8, name -> repository.getMessage(name).thenApply(message ->
                        String.format("%s, %s!", message.orElse("Hello"), name)
                ))
        );
    }
}
