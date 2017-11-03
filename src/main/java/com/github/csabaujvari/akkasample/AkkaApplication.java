package com.github.csabaujvari.akkasample;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import com.github.csabaujvari.akkasample.router.AkkaRouter;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class AkkaApplication extends AllDirectives {

    private static ActorSystem system;
    private static Http http;
    private static ActorMaterializer materializer;
    private static LoggingAdapter log;

    public static void main(String[] args) throws IOException {

        start();

        AkkaRouter akkaRouter = new AkkaRouter();
        Flow<HttpRequest, HttpResponse, NotUsed> flow
                = akkaRouter.routes().flow(system, materializer);

        CompletionStage<ServerBinding> binding
                = http.bindAndHandle(flow, ConnectHttp.toHost("localhost", 8080), materializer);


        log.info("Application started, press enter to exit");
        System.in.read();

        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private static void start() {
        system = ActorSystem.create("application-akka-system");
        http = Http.get(system);
        materializer = ActorMaterializer.create(system);
        log = system.log();
    }

    public static ActorSystem getSystem() {
        return system;
    }
}
