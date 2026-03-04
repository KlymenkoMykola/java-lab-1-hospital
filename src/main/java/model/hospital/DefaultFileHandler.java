package model.hospital;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DefaultFileHandler implements FileHandler{
    @Override
    public void writeToFile(String filePath, String content) throws IOException {
        Files.writeString(Paths.get(filePath), content);
    }

    @Override
    public String readFromFile(String filePath) throws IOException{
        return Files.readString(Paths.get(filePath));
    }
}
