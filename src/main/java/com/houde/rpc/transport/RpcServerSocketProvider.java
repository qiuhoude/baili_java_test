package com.houde.rpc.transport;

import com.houde.rpc.serialization.Hessian2Serialization;
import com.houde.rpc.serialization.Serialization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiukun
 * @create 2019-03-14 17:57
 */
public class RpcServerSocketProvider {
    public static void main(String[] args) throws Exception {

        //序列化层实现参考之前的章节
        Serialization serialization = new Hessian2Serialization();

        ServerSocket serverSocket = new ServerSocket(8088);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (true) {
            final Socket socket = serverSocket.accept();
            executorService.execute(() -> {
                try {
                    InputStream is = socket.getInputStream();
                    OutputStream os = socket.getOutputStream();
                    try {
                        DataInputStream dis = new DataInputStream(is);
                        // 读取数据长度,此处有粘包的问题
                        int length = dis.readInt();
                        byte[] requestBody = new byte[length];
                        dis.read(requestBody);
                        //反序列化requestBody => RpcRequest
                        RpcRequest rpcRequest = serialization.deserialize(requestBody, RpcRequest.class);
                        //反射调用生成响应 并组装成 rpcResponse
                        RpcResponse rpcResponse = invoke(rpcRequest);
                        //序列化rpcResponse => responseBody
                        byte[] responseBody = serialization.serialize(rpcResponse);
                        DataOutputStream dos = new DataOutputStream(os);
                        dos.writeInt(responseBody.length);
                        dos.write(responseBody);
                        dos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        is.close();
                        os.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public static RpcResponse invoke(RpcRequest rpcRequest) {
        //模拟反射调用
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        String res = "噜啦啦 " + rpcRequest.getRequestId();
        rpcResponse.setValue(res);
        //... some operation
        return rpcResponse;
    }

}
