package fish.eyebrow.caramel;

import fish.eyebrow.caramel.HelloWorldGrpc.HelloWorldImplBase;
import io.grpc.stub.StreamObserver;

class HelloWorldService extends HelloWorldImplBase {

    @Override
    public void greet(final HelloWorldRequest request, final StreamObserver<HelloWorldResponse> responseObserver) {
        final String requestedName = request.getName();
        final String responseMessage = "Hello, " + requestedName + "!";

        responseObserver.onNext(HelloWorldResponse.newBuilder().setMessage(responseMessage).build());
        responseObserver.onCompleted();
    }
}
