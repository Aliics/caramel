package fish.eyebrow.caramel;

import fish.eyebrow.caramel.HelloWorldGrpc.HelloWorldImplBase;
import io.grpc.stub.StreamObserver;

class HelloWorldService extends HelloWorldImplBase {

    @Override
    public void greet(final HelloWorldRequest request, final StreamObserver<HelloWorldResponse> responseObserver) {
        responseObserver.onNext(HelloWorldResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
