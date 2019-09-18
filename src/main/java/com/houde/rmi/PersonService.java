package com.houde.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 创建远程接口PersonService,注意远程接口需要继承Remote
 * Created by I on 2017/6/29.
 */
public interface PersonService extends Remote {
    public List<PersonEntity> GetList() throws RemoteException;
}
