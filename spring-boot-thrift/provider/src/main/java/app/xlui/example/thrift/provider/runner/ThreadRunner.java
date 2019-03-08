package app.xlui.example.thrift.provider.runner;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.server.TServer;

@Slf4j
public class ThreadRunner extends Thread {
    private TServer server;

    public ThreadRunner(TServer server, boolean daemon) {
        this.server = server;
        if (daemon) {
            setDaemon(true);
        }
    }

    @Override
    public void run() {
        log.info("server start...");
        server.serve();
    }
}
