package app.xlui.example.thrift.provider.config;

import app.xlui.example.thrift.provider.impl.HelloServiceImpl;
import app.xlui.example.thrift.provider.runner.ThreadRunner;
import app.xlui.example.thrift.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ProviderConfiguration {
    @Value(value = "${provider.port}")
    private int port;
    @Value(value = "${provider.isDaemon}")
    private boolean isDaemon;

    @Bean
    public TServer server() throws TTransportException {
        TServerTransport transport = new TServerSocket(port);
        HelloService.Processor processor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());
        TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
        TTransportFactory transportFactory = new TTransportFactory();

        TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(transport)
                .processor(processor)
                .protocolFactory(factory)
                .transportFactory(transportFactory);

        return new TThreadPoolServer(tArgs);
    }

    @Bean(initMethod = "start")
    public ThreadRunner publisher(TServer server) {
        return new ThreadRunner(server, isDaemon);
    }
}
