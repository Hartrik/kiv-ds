package cz.harag.ds.cv02.server;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.codehaus.jackson.map.ObjectMapper;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @author Patrik Harag
 * @version 2020-12-04
 */
public class Bank {

    private static final int TIMEOUT = -1;  // ms

    private static final int DEFAULT_BALANCE = 5_000_000;
    private static final int RETARDATION = 50;  // ms
    private static final int OPERATION_GEN_DELAY = 50;  // ms

    private final List<BankConnection> bankConnections;
    private final ZContext context;
    private int balance;

    public Bank(List<BankConnection> bankConnections) {
        this.bankConnections = bankConnections;
        this.context = new ZContext();
        this.balance = DEFAULT_BALANCE;
    }

    public void bindAll() {
        for (final BankConnection connection : bankConnections) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bind(connection);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void connectAll() {
        for (final BankConnection connection : bankConnections) {
            connect(connection);
        }
    }

    /**
     * Starts operation generation.
     */
    public void startGenerator() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    try {
                        Thread.sleep(OPERATION_GEN_DELAY);
                    } catch (InterruptedException e) {
                        return;
                    }
                    try {
                        generateOp(random);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void bind(BankConnection address) throws IOException {
        ZMQ.Socket socket = context.createSocket(SocketType.PAIR);
        socket.bind("tcp://" + address.getBindAddress());

        while (!Thread.currentThread().isInterrupted()) {
            // Block until a message is received
            byte[] rawMsg = socket.recv(0); retardation();
            String stringMsg = new String(rawMsg, ZMQ.CHARSET);
            ObjectMapper objectMapper = new ObjectMapper();
            MessageOperation op = objectMapper.readValue(stringMsg, MessageOperation.class);

            String response = "ok";
            if (op.getOperation() == OperationType.CREDIT) {
                synchronized (this) {
                    balance += op.getValue();
                    System.out.printf("OP: credit %d, status: %d%n", op.getValue(), balance);
                }
            } else {
                synchronized (this) {
                    if (balance >= op.getValue()) {
                        balance -= op.getValue();
                        sendAsync(address, new MessageOperation(op.getValue(), OperationType.CREDIT));
                        System.out.printf("OP: debit %d, status: %d%n", op.getValue(), balance);
                    } else {
                        response = "f";
                        System.out.printf("OP: debit %d FAILED, status: %d%n", op.getValue(), balance);
                    }
                }
            }

            socket.send(response.getBytes(ZMQ.CHARSET), 0);
        }
    }

    private void connect(BankConnection address) {
        ZMQ.Socket socket = context.createSocket(SocketType.PAIR);
        socket.connect("tcp://" + address.getConnectAddress());
        socket.setSendTimeOut(TIMEOUT);
        socket.setReceiveTimeOut(TIMEOUT);
        address.setConnectSocket(socket);
    }

    private void generateOp(Random random) throws IOException {
        BankConnection rndAddress = bankConnections.get(random.nextInt(bankConnections.size()));
        int rndValue = 10_000 + random.nextInt(40_000);

        if (random.nextBoolean()) {
//        if (false) {
            // send money to rnd bank
            synchronized (this) {
                rndValue = Math.min(balance, rndValue);
                balance -= rndValue;
            }
            if (!send(rndAddress, new MessageOperation(rndValue, OperationType.CREDIT))) {
                // OP failed
                synchronized (this) {
                    balance += rndValue;
                    System.out.printf("GEN OP failed, reverting: %d, status: %d%n", rndValue, balance);
                }
            }
        } else {
            // get money from rnd bank
            send(rndAddress, new MessageOperation(rndValue, OperationType.DEBIT));
        }
    }

    private boolean send(BankConnection address, MessageOperation operation) throws IOException {
        ZMQ.Socket socket = address.getConnectSocket();
        synchronized (socket) {
            ObjectMapper objectMapper = new ObjectMapper();

            String response = objectMapper.writeValueAsString(operation);
            socket.send(response.getBytes(ZMQ.CHARSET), 0);

            // Block until a message is received
            byte[] rawMsg = socket.recv(0);
            String stringMsg = new String(rawMsg, ZMQ.CHARSET);
            return stringMsg.equalsIgnoreCase("OK");
        }
    }

    private void sendAsync(final BankConnection address, final MessageOperation operation) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    send(address, operation);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    void retardation() {
        try {
            Thread.sleep(RETARDATION);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
