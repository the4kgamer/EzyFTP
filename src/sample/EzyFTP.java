package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.*;

import javax.xml.soap.Text;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;
//import org.apache.*;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author The4kGamer
 */
public class EzyFTP extends Application {

    @FXML
    public MenuButton SavedMenu;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CONNECTION AWAITING");
        primaryStage.setScene(new Scene(root, 1200, 720));
        primaryStage.show();
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        primaryStage.getIcons().add(icon);






        File Sysfile = new File("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys");
        if (Sysfile.isFile()) {


        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SYS file is missing");
            Image image = new Image("https://img.4plebs.org/boards/pol/image/1487/71/1487719913812.gif");
            String imageSource = "https://img.4plebs.org/boards/pol/image/1487/71/1487719913812.gif";
            ImageView imageView = new ImageView(imageSource);
            alert.setGraphic(imageView);
            alert.setHeaderText("The SYS file is missing");
            alert.setContentText("Which means you have most likely attempted to decompile the program, and then run it in intellij where the path of the Sys file is the same, if you want to try learn JavaFX by using this program, I suggest you go to my github, (https://github.com/the4kgamer) and download the SRC and run it yourself that way ok!cause I will have this feature disabled in there");
            alert.showAndWait();
            Platform.exit();
            System.exit(0);

        }



        File file = new File("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys");



        //now read the file line by line...


        if(FileUtils.readFileToString(file).contains("HAS DISAGREED TRUE")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("WTF are you doing?");
            Image image = new Image("https://i.kym-cdn.com/entries/icons/original/000/026/913/excuse.jpg");

            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.setHeaderText("You recently disagreed to the terms and conditions, what are you trying to do????");
            alert.setContentText("if you are thinking of using the program, just read the terms and AGREE!, it isn't anything serious...");

            alert.showAndWait();










        }
        else {

        }
        if(FileUtils.readFileToString(file).contains("Agreed FALSE")) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("LEGAL");
            Image image = new Image("https://yt3.ggpht.com/-tLz_OkEzUe8/AAAAAAAAAAI/AAAAAAAAAAA/36Nh5H1VOGQ/s108-c-k-no-mo-rj-c0xffffff/photo.jpg");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);

            alert.setHeaderText("By pressing ok you are agreeing to adhere to the following terms ");
            alert.setContentText("Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                    "of this software and associated documentation files (the \"Software\"), to deal\n" +
                    "in the Software without restriction, including without limitation the rights\n" +
                    "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                    "copies of the Software, and to permit persons to whom the Software is\n" +
                    "furnished to do so, subject to the following conditions:\n" +
                    "\n" +
                    "The above copyright notice and this permission notice shall be included in all\n" +
                    "copies or substantial portions of the Software.\n" +
                    "\n" +
                    "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                    "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                    "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                    "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                    "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                    "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                    "SOFTWARE.\n" +
                    "\n" +
                    "Copyright (c) 2018 The4kGamer\n");



            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);

            if (button == ButtonType.OK) {

                Path path = Paths.get("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys");
                Charset charset = StandardCharsets.UTF_8;

                String content = new String(Files.readAllBytes(path), charset);
                content = content.replaceAll("Agreed FALSE", "Agreed TRUE");
                content = content.replaceAll("HAS DISAGREED TRUE", "HAS DISAGREED FALSE");
                Files.write(path, content.getBytes(charset));
            } else {

                Path path = Paths.get("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys");
                Charset charset = StandardCharsets.UTF_8;

                String content = new String(Files.readAllBytes(path), charset);
                content = content.replaceAll("HAS DISAGREED FALSE", "HAS DISAGREED TRUE");
                Files.write(path, content.getBytes(charset));
                Platform.exit();
                System.exit(0);

            }
            //alert.showAndWait();

            //I am aware it compiles at runtime, but I just have a habit of building it prior?!??!
            //I am trying to figure out why the alert is displayed twice tf








        }






    }
    @FXML
    private Node root;







    public static void main(String[] args) {
        launch(args);


    }
}
