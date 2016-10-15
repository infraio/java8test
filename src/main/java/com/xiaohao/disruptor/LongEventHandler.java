package com.xiaohao.disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {

  public void onEvent(LongEvent arg0, long arg1, boolean arg2) throws Exception {
    System.out.println("Event : " + arg0);
  }

}
