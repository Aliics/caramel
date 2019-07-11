package fish.eyebrow.caramel;

import fish.eyebrow.caramel.HelloWorldGrpc.HelloWorldImplBase;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import io.grpc.stub.StreamObserver;

class HelloWorldService extends HelloWorldImplBase {

    private static final String DEFAULT_NAME = "World";


    @Override
    public void greet(final HelloWorldRequest request, final StreamObserver<HelloWorldResponse> responseObserver) {
        final String requestedName = !StringUtil.isNullOrEmpty(request.getName()) ? request.getName() : DEFAULT_NAME;
        final String responseMessage = "Hello, " + requestedName + "!";

        responseObserver.onNext(HelloWorldResponse.newBuilder().setMessage(responseMessage).build());
        responseObserver.onCompleted();
    }
}
