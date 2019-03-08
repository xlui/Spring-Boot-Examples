package app.xlui.example.thrift.provider;

import app.xlui.example.thrift.service.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderApplicationTests {
    private HelloService.Client client;

    @Before
    public void setUp() throws Exception {
        TTransport transport = new TSocket("localhost", 1234);
        TProtocol protocol = new TBinaryProtocol(transport);

        transport.open();
        client = new HelloService.Client(protocol);
    }

    @Test
    public void testSayHello() throws TException {
        final String hello = client.sayHello("xlui");

        assertEquals("hello, xlui", hello);
    }

    @Test
    public void testSayBye() throws Exception {
        final String bye = client.sayBye("xlui");

        assertEquals("bye, xlui", bye);
    }
}
