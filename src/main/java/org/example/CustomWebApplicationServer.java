package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {
    private final int port;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Thread Pool

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");

                /**
                 * Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 *      이렇게 했을때 다른 이슈가 있을 수 있다.
                 *      Thread는 생성될때마다 독립적인 메모리를 할당받고 그럴때마다 메모리를 할당받게 되면 성능이 매우 떨어지게 된다.
                 *      요청이 몰리게되면 Thread를 굉장히 많이 생성하게 되고 메모리 할당 작업이 많이 발생하게 된다.\
                 *      동시접속자가 많을 경우 CPU와 메모리 사용량을 감당할 수 없을 것이다.
                 *      서버의 리소스가 감당할 수 없어서 다운될 수 있다.
                 *          -> Thread Pool 개념을 활용해야한다. Thread 를 일정 개수 만큼 생성해두고 이를 재활용 해야한다.
                 *              Thread Pool 를 이용하여 안정적인 서비스가 가능하도록 해야한다.
                 */
                executorService.execute(new ClientRequestHandler(clientSocket)); // Thread Pool 사용
            }
        }
    }
}
