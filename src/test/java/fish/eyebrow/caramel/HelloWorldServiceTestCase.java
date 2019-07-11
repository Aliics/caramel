package fish.eyebrow.caramel;

import static org.assertj.core.api.Assertions.assertThat;

import fish.eyebrow.caramel.HelloWorldGrpc.HelloWorldStub;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class HelloWorldServiceTestCase {

    private HelloWorldStub stub;

    private Server registeredServer;

    private ManagedChannel registeredChannel;


    @BeforeEach
    void setUp() throws IOException {
        final String serviceName = InProcessServerBuilder.generateName();
        final Server server = InProcessServerBuilder.forName(serviceName).addService(new HelloWorldService()).build();
        final ManagedChannel channel = InProcessChannelBuilder.forName(serviceName).build();

        final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();
        registeredServer = grpcCleanupRule.register(server).start();
        registeredChannel = grpcCleanupRule.register(channel);

        stub = HelloWorldGrpc.newStub(registeredChannel);
    }


    @Test
    void service_response_will_not_be_null() throws ExecutionException, InterruptedException {
        final CompletableFuture<HelloWorldResponse> responseFuture = new CompletableFuture<>();
        stub.greet(HelloWorldRequest.newBuilder().build(), new SimpleHelloWorldStreamObserver(responseFuture));

        assertThat(responseFuture.get()).isNotNull();
    }


    @Test
    void requesting_with_name_will_then_greet_said_name() throws ExecutionException, InterruptedException {
        final String name = "World";
        final CompletableFuture<HelloWorldResponse> responseFuture = new CompletableFuture<>();
        stub.greet(HelloWorldRequest.newBuilder().setName(name).build(), new SimpleHelloWorldStreamObserver(responseFuture));

        final String expectedMessage = "Hello, World!";
        assertThat(responseFuture.get().getMessage()).isEqualTo(expectedMessage);
    }


    @AfterEach
    void tearDown() {
        registeredChannel.shutdownNow();
        registeredServer.shutdownNow();
    }
}
