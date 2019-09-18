package com.houde.rpc.transport;

import com.houde.rpc.serialization.Hessian2Serialization;
import com.houde.rpc.serialization.Serialization;

import java.io.*;
import java.net.Socket;

/**
 * @author qiukun
 * @create 2019-03-14 18:01
 */
public class RpcSocketConsumer {
    public static void main(String[] args) throws IOException {

        Serialization serialization = new Hessian2Serialization();

        Socket socket = new Socket("localhost", 8088);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        //封装rpc请求
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(123452L);
        //序列化 rpcRequest => requestBody
        byte[] requestBody = serialization.serialize(rpcRequest);
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(requestBody.length);
        dos.write(requestBody);
        dos.flush();
        DataInputStream dis = new DataInputStream(is);
        int length = dis.readInt();
        byte[] responseBody = new byte[length];
        dis.read(responseBody);
        //反序列化 responseBody => rpcResponse
        RpcResponse rpcResponse = serialization.deserialize(responseBody, RpcResponse.class);
        is.close();
        os.close();
        socket.close();

        System.out.println(rpcResponse.getRequestId());
        System.out.println(rpcResponse.getValue());
    }
}
