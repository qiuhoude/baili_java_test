package com.houde.netty.nio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * @author qiukun
 * @create 2019-04-26 10:05
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {
        File file = new File("log.txt");
        long len = file.length();
        byte[] ds = new byte[(int) len];
        MappedByteBuffer mappedByteBuffer =
                new FileInputStream(file).getChannel()
                        .map(FileChannel.MapMode.READ_ONLY, 0, len);
        for (int i = 0; i < len; i++) {
            byte b = mappedByteBuffer.get();
            ds[i] = b;
        }
        Scanner scanner = new Scanner(new ByteArrayInputStream(ds));
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
     }
}
