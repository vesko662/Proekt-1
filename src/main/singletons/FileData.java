package main.singletons;

public class FileData {
    private static FileData instance = null;

    private String fileData;
    private String filePath;
    private String fileName;
    private boolean isFileOpen=false;
    private FileData()
    {
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileData() {
        return fileData;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFileOpen(boolean fileOpen) {
        isFileOpen = fileOpen;
    }

    public boolean isFileOpen() {
        return isFileOpen;
    }

    public static synchronized FileData getInstance()
    {
        if (instance == null)
            instance = new FileData();

        return instance;
    }
}


