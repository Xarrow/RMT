package io.github.xarrow.rmt.api.session;

import com.pty4j.PtyProcess;

import java.io.*;

public interface ProcessWrapper {
    PtyProcess ptyProcess();

    default OutputStream output() {
        return ptyProcess().getOutputStream();
    }

    default BufferedWriter write() {
        return new BufferedWriter(new OutputStreamWriter(output()));
    }


    default InputStream input() {
        return ptyProcess().getInputStream();
    }

    default BufferedReader read() {
        return new BufferedReader(new InputStreamReader(input()));
    }

    default InputStream error() {
        return ptyProcess().getErrorStream();
    }

    default BufferedReader errorRead() {
        return new BufferedReader(new InputStreamReader(error()));
    }
}
