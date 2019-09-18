package com.houde.hotfix.instrumentation;

import org.junit.Test;

/**
 * Created by I on 2017/12/27.
 */
public class AgentTest {
    @Test
    public void shouldInstantiateSleepingInstance() throws InterruptedException {

        Sleeping sleeping = new Sleeping();
        sleeping.randomSleep();
    }

}