package com.houde.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by I on 2017/12/2.
 */
public abstract class NHttpServer implements HttpHandler
{

    private final HttpServer hs;
    private final int port;

    /**
     * 构造一个服务器
     *
     * @param context
     * @param port
     * @param poolSize
     * @throws IOException
     */
    public NHttpServer(String context, int port, int poolSize) throws IOException
    {
        this.port = port;
        InetSocketAddress addr = new InetSocketAddress(port);
        HttpServerProvider hsp = HttpServerProvider.provider();
        hs = hsp.createHttpServer(addr, 1);
        if (context != null)
        {
            hs.createContext(context, this);
        }

        hs.setExecutor(Executors.newFixedThreadPool(poolSize));
    }

    /**
     * 启动服务器
     *
     */
    public void start()
    {
        hs.start();
    }

    public int getPort()
    {
        return port;
    }

    /**
     * 新建context
     *
     * @param context
     */
    public void createContext(String context)
    {
        this.hs.createContext(context, this);
    }

    @Override
    public abstract void handle(HttpExchange he) throws IOException;
}

