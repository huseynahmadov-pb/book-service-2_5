package az.company.bookservice_2_5.config;

import az.company.bookservice_2_5.grpc.server.BookGrpcServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    private final BookGrpcServer bookGrpcServer;

    @Value("${grpc.server.port:9090}")
    private int grpcPort;

    private Server server;

    @PostConstruct
    public void start() throws IOException {
        server = ServerBuilder.forPort(grpcPort)
                .addService(bookGrpcServer)
                .build()
                .start();
        log.info("gRPC server started on port {}", grpcPort);
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
            log.info("gRPC server stopped");
        }
    }
}
