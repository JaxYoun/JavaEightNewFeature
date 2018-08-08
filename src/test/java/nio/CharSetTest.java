package nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @Auther: Yang
 * @Date: 2018/8/3 22:34
 * @Description:字符集
 */
public class CharSetTest {

    /**
     * 查看支持的字符集
     */
    @Test
    public void test() {
        Charset.availableCharsets().forEach((a, b) -> System.out.println(a + "-->" + b));
    }

    @Test
    public void test0() {
        // TODO 解码打印报错
        Charset charset = Charset.forName("UTF-8");
        //获取编码器
        CharsetEncoder encoder = charset.newEncoder();
        //获取解码器
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(100);
        charBuffer.put("啊九零六零");
        charBuffer.flip();

        ByteBuffer byteBuffer = null;
        try {
            //编码
            byteBuffer = encoder.encode(charBuffer);
            for (int i = 0; i < 5; i++) {
                System.out.println(byteBuffer.get());
            }
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }

        //解码
        byteBuffer.flip();
        try {
            CharBuffer charBuffer1 = decoder.decode(byteBuffer);
            System.out.println(charBuffer1.toString());
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
    }

}
