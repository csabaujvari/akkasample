package com.github.csabaujvari.akkasample.actor.protocol;

import jdk.nashorn.internal.ir.annotations.Immutable;

public class AkkaActorProtocol {

    @Immutable
    public static final class PublishMessage {
        private String message;

        public PublishMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "PublishMessage{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
