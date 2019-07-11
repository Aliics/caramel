package fish.eyebrow.caramel;

import java.util.concurrent.CompletableFuture;

public class SimpleHelloWorldStreamObserver implements io.grpc.stub.StreamObserver<HelloWorldResponse> {

    private final CompletableFuture<HelloWorldResponse> responseFuture;


    public SimpleHelloWorldStreamObserver(final CompletableFuture<HelloWorldResponse> responseFuture) {
        this.responseFuture = responseFuture;
    }


    @Override
    public void onNext(final HelloWorldResponse helloWorldResponse) {
        responseFuture.complete(helloWorldResponse);
    }


    @Override
    public void onError(final Throwable throwable) {

    }


    @Override
    public void onCompleted() {

    }
}
