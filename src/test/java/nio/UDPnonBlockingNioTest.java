package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Auther: Yang
 * @Date: 2018/8/5 10:14
 * @Description:非阻塞的UDPnio
 */
public class UDPnonBlockingNioTest {

    @Test
    public void client() throws IOException {
        try (
                DatagramChannel datagramChannel = DatagramChannel.open()
        ) {
            datagramChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()) {
                String string = scanner.next();
                byteBuffer.put((LocalDateTime.now().toString() + string).getBytes());
                byteBuffer.flip();
                datagramChannel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 9090));
                byteBuffer.clear();
            }
        }
    }

    @Test
    public void server() throws IOException {
        try (
                DatagramChannel datagramChannel = DatagramChannel.open();
        ) {
            datagramChannel.configureBlocking(false);
            datagramChannel.bind(new InetSocketAddress(9090));
            Selector selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_READ);
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        datagramChannel.receive(byteBuffer);
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                }
                iterator.remove();
            }

        }

    }

}
