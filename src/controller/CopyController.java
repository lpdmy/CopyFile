package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import model.Config;

public class CopyController{
    public  Config readFileConfig(Config config) throws Exception {
        File configFile = new File("config.properties");
//        if (!configFile.exists()) {
            createFileConfig(config);
//        } else {
            
            Properties prop = new Properties();
            InputStream input = new FileInputStream("config.properties");
            prop.load(input);
            config.setCopyFolder(prop.getProperty("COPY_FOLDER"));
            config.setDataType(prop.getProperty("DATA_TYPE"));
            config.setPath(prop.getProperty("PATH"));
//        }"D:\FA23\LAB\CopyFile\demo"
        return config;
    }

    public  void createFileConfig(Config config) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Copy Folder:");
        config.setCopyFolder(scanner.nextLine());
        System.out.println("Enter Data Type:");
        config.setDataType(scanner.nextLine());
        System.out.println("Enter Path:");
        config.setPath(scanner.nextLine());

        Properties prop = new Properties();
        OutputStream output = new FileOutputStream("config.properties");
        prop.setProperty("COPY_FOLDER", config.getCopyFolder());
        prop.setProperty("DATA_TYPE", config.getDataType());
        prop.setProperty("PATH", config.getPath());
        prop.store(output, null);
    }

    public  void checkConfig(Config config) throws Exception {
        if (config.getCopyFolder() == null || config.getCopyFolder().isEmpty()) {
            throw new Exception("COPY_FOLDER is not set in the configuration file.");
        }
        if (config.getDataType() == null || config.getDataType().isEmpty()) {
            throw new Exception("DATA_TYPE is not set in the configuration file.");
        }
        if (config.getPath() == null || config.getPath().isEmpty()) {
            throw new Exception("PATH is not set in the configuration file.");
        }

        File copyFolder = new File(config.getCopyFolder());
        if (!copyFolder.exists()) {
            throw new Exception("The source folder does not exist.");
        }

        File path = new File(config.getPath());
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public  List<String> copyFile(Config config) throws IOException {
        List<String> copiedFiles = new ArrayList<>();
        File folder = new File(config.getCopyFolder());
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                if (config.getDataType().contains(extension)) {
                    FileInputStream fis = null;
                    FileOutputStream fos = null;
                    try {
                        fis = new FileInputStream(file);
                        fos = new FileOutputStream(new File(config.getPath(), fileName));
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                        copiedFiles.add(fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fis != null) {
                            fis.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    }
                }
            }
        }
        return copiedFiles;
    }

}

