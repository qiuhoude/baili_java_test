package com.houde.command_chain.ls;

import com.houde.command_chain.CommandVO;
import com.houde.command_chain.FileManager;

/**
 * Created by I on 2018/3/26.
 */
public class LS_L extends AbstractLS {
    protected String echo(CommandVO vo) {
        return FileManager.ls_l("");
    }
    //l选项
    protected String getOperateParam() {
        return super.L_PARAM;
    }
}
