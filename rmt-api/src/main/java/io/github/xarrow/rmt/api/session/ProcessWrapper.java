package io.github.xarrow.rmt.api.session;

import java.io.*;

public interface ProcessWrapper {
    Process process();

    default OutputStream output() {
        return process().getOutputStream();
    }

    default BufferedWriter write() {
        return new BufferedWriter(new OutputStreamWriter(output()));
    }


    default InputStream input() {
        return process().getInputStream();
    }

    default BufferedReader read() {
        return new BufferedReader(new InputStreamReader(input()));
    }

    default InputStream error() {
        return process().getErrorStream();
    }

    default BufferedReader errorRead() {
        return new BufferedReader(new InputStreamReader(error()));
    }
}
