import java.io.*;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class PtyTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process start = new ProcessBuilder("cmd.exe".split("\\s+")).directory(new File(System.getProperty("user.home")))
            .start();
        InputStream stderr = start.getErrorStream();
        InputStream stdout = start.getInputStream();
        OutputStream stdin = start.getOutputStream();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(stdin));
        bufferedWriter.write("ping baidu.com");
        bufferedWriter.flush();
        bufferedWriter.close();

        String line;
        BufferedReader brCleanUp =
            new BufferedReader(new InputStreamReader(stdout));
        while ((line = brCleanUp.readLine()) != null) {
            System.out.println(">>> | " + line);
        }
    }
}
