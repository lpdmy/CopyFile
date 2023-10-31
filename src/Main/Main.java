package Main;



import controller.CopyController;
import model.Config;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        CopyController controller = new CopyController();
        try {
            config = controller.readFileConfig(config);
            controller.checkConfig(config);
            List<String> copiedFiles = controller.copyFile(config);
            System.out.println("Copied files: " + copiedFiles);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }}
