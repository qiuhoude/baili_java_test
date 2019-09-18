package com.houde.command_chain.ls;

import com.houde.command_chain.CommandVO;
import com.houde.command_chain.FileManager;

/**
 * Created by I on 2018/3/26.
 */
public class LS extends AbstractLS {
    //最简单的ls命令
    protected String echo(CommandVO vo) {
        return FileManager.ls("");
    }
    //参数为空
    protected String getOperateParam() {
        return super.DEFAULT_PARAM;
    }
}
