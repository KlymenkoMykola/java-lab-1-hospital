package model.hospital;

import java.io.IOException;

public interface FileHandler {
    void writeToFile(String filePath, String content) throws IOException;
    String readFromFile(String filePath) throws  IOException;
}
