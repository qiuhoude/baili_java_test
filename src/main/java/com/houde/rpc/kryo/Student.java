package com.houde.rpc.kryo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author qiukun
 * @create 2019-03-13 18:52
 */
@Data
@NoArgsConstructor
public class Student implements Serializable {



    public Student(String name) {
        this.name = name;
    }

    private String name;
}
