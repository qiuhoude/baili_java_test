package com.houde.command_chain.ls;

import com.houde.command_chain.Command;
import com.houde.command_chain.CommandName;
import com.houde.command_chain.CommandVO;

/**
 * Created by I on 2018/3/26.
 */
public class LSCommand extends Command {
    public String execute(CommandVO vo) {
        // 返回链表的首节点
        CommandName firstNode = super.buildChain(AbstractLS.class).get(0);
        return firstNode.handleMessage(vo);
    }
}
