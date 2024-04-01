package main.File;

import java.io.*;

public class FileWrite {

    public void writeFile(String filePath,String content)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
