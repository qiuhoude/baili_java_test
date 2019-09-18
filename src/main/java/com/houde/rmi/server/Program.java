package com.houde.rmi.server;

import com.houde.rmi.PersonService;
import com.houde.rmi.PersonServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created by I on 2017/6/29.
 */
public class Program {
    public static void main(String[] args) {
        try {
            PersonService personService = new PersonServiceImpl();
            // 注册通讯端口
            LocateRegistry.createRegistry(6600);
            // 注册通讯路径
            Naming.rebind("rmi://127.0.0.1:6600/PersonService", personService);
            System.out.println("Service Start!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
