package com.houde.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by I on 2017/12/2.
 */
public class River extends NHttpServer  {
    /**
     * 构造一个服务器
     *
     * @param context
     * @param port
     * @param poolSize
     * @throws IOException
     */
    public River(String context, int port, int poolSize) throws IOException {
        super(context, port, poolSize);
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        URI u = he.getRequestURI();
        String mapping = u.getPath();
        System.out.println(mapping);
        System.out.println( u.getQuery());
        ByteArrayOutputStream r = new ByteArrayOutputStream();
        String re = "haha";
        r.write(re.getBytes("utf-8"));
        he.sendResponseHeaders(200, r.size());
        he.getResponseBody().write(r.toByteArray());
        he.getResponseBody().flush();
        he.close();
    }

    public static void main(String[] args) throws IOException {
        River river = new River("/river", 8088, 2);

        river.start();
    }
}
