package app.xlui.example.thrift.provider.impl;

import app.xlui.example.thrift.service.HelloService;
import org.apache.thrift.TException;

public class HelloServiceImpl implements HelloService.Iface {
    @Override
    public String sayHello(String username) throws TException {
        return "hello, " + username;
    }

    @Override
    public String sayBye(String username) throws TException {
        return "bye, " + username;
    }
}
