package app.xlui.example.thrift.consumer;

import app.xlui.example.thrift.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ConsumerApplication implements CommandLineRunner {
    private TTransport transport;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
    @Bean(initMethod = "start")
    public Thread thread() {
        return new Thread(() -> {
            while (true) {
                // prevent stop
            }
        });
    }

    @Override
    public void run(String... args) throws Exception {
        transport = new TSocket("localhost", 1234);
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        HelloService.Client client = new HelloService.Client(protocol);

        log.info("client start");
        transport.open();
        try {
            log.info("client say hello.");
            log.info("server response: [" + client.sayHello("xlui") + "]");
            log.info("client say byte.");
            log.info("server response: [" + client.sayBye("xlui") + "]");
        } catch (TException e) {
            e.printStackTrace();
        }
        log.info("client stop");
    }

}
