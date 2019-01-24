package com.sonartrading.cryptostream.impl;

import akka.Done;
import akka.stream.javadsl.Flow;

import com.sonartrading.crypto.api.CryptoEvent;
import com.sonartrading.crypto.api.CryptoService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

/**
 * This subscribes to the CryptoService event stream.
 */
public class CryptoStreamSubscriber {
    @Inject
    public CryptoStreamSubscriber(CryptoService cryptoService, CryptoStreamRepository repository) {
        // Create a subscriber
        cryptoService.helloEvents().subscribe()
                // And subscribe to it with at least once processing semantics.
                .atLeastOnce(
                        // Create a flow that emits a Done for each message it processes
                        Flow.<CryptoEvent>create().mapAsync(1, event -> {
                            if (event instanceof CryptoEvent.GreetingMessageChanged) {
                                CryptoEvent.GreetingMessageChanged messageChanged = (CryptoEvent.GreetingMessageChanged) event;
                                // Update the message
                                return repository.updateMessage(messageChanged.getName(), messageChanged.getMessage());
                            } else {
                                // Ignore all other events
                                return CompletableFuture.completedFuture(Done.getInstance());
                            }
                        })
                );
    }
}
