//package io.github.xarrow.rmt.expand.listener;
//
//import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
//import io.github.xarrow.rmt.api.protocol.TerminalMessage;
//import io.github.xarrow.rmt.api.websocket.TerminalRQ;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @Author: helix
// * @Time:2022/7/30
// * @Site: https://github.com/xarrow
// */
//@Slf4j
//public class TestLocalListener implements TerminalProcessListener {
//    @Override
//    public String listenerName() {
//        return this.getClass().getSimpleName();
//    }
//
//
//    StringBuilder sb = new StringBuilder();
//
//    @Override
//    public void beforeCommand(TerminalMessage message) {
//        String command = ((TerminalRQ) message).getCommand();
//        sb.append(command);
//        log.info(sb.toString());
//    }
//}
