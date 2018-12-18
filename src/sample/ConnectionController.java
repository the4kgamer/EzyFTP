package sample;

import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.chilkatsoft.CkFtp2;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.annotation.Resource;
import javax.lang.model.type.ErrorType;
import javax.naming.NamingException;
//import javax.management.Notification;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import API.easyFTP;

import javafx.util.Duration;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.Notifications;
import org.omg.CORBA.INTERNAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//I know this isn't needed, but I thought I will just do it ;/

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;

import static java.lang.System.out;
import static java.lang.System.*;
//import static sample.ConnectionController.ChangeSource.EXTERNAL;
//import com.adeel.library.easyFTP;
import API.AbstractFTPClient;
import API.FTPFileInfoVO;
import API.FTPLogCommandListener;
import API.FTPTransferMode;
import API.ServerToClient;
import API.ServerToServer;

import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.EzyFTP;
import java.util.*;
import org.controlsfx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.fxmisc.livedirs.*;
import com.chilkatsoft.CkZip;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


import API.*;
import sun.plugin2.main.client.DisconnectedExecutionContext;

/**
 *
 * @author The4kGamer
 */
public class ConnectionController {


    @FXML
    private TextField TxtUsername;



    @FXML
    private TextField TxtHost;

    @FXML
    private PasswordField TxtPassword;


    @FXML
    private TextField Port;


    @FXML
    private CheckBox CheckBoxSSLonly;



    @FXML
    private Label Label;

    @FXML
    private Button ConnectBtn;



    @FXML

    private TextArea Console;


    @FXML

    private Button ConsoleTest;

    @FXML


    private MenuButton MethodOfTransfer; //kk i am retarted.....


    @FXML
    private Button LocalDirBtn;

    @FXML
    private Node root;


    @FXML

    private Button DisconnectButton;


    @FXML

    private Label UsernameLabel;

    @FXML

    private Label PasswordLabel;

    @FXML

    private CheckBox SaveInfoCheckBtn;

    @FXML

    private MenuButton SavedMenu;


    // define menu items
    MenuItem menuItem1 = new MenuItem("Transfer from server to local directory");
    MenuItem menuItem2 = new MenuItem("Transfer from local directory to server");
    MenuItem menuItem3 = new MenuItem("Transfer from server to server??!?!?");



    //demonstreigtion of current client functionality...





    public int AttemptedReconnect = 1;




    public void ChooseDirectory(ActionEvent event) {

        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);







    }

    //ill be adding GUI features such as drop and drag files in, I already have a directory tree view lib located.
    //when method 'servertoserver' transfer is selected
    public void ServerToServerTransfer(ActionEvent event) {

    }

    //define DLL lib path

    public static void setDllLibraryPath(String resourceStr) {
        try {
            System.setProperty("java.library.path", resourceStr);

            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    public int UniqueConnectionCount;


    public void TestConsole() {


        Console.setStyle("-fx-text-inner-color: green");



        Console.setText(Console.getText() + "CONNECTED TO: \n");
        ConsoleUpdate();

    }

    public int disconnect = 1;
    public int i = 0;
    //connect to FTP (now that I am going to be using chilkat)
    public void TestConnect(ActionEvent event) throws Exception{
        String Username = TxtUsername.getText();
        String Password = TxtPassword.getText();
        String IP = TxtHost.getText();
        disconnect = 1;


        //CheckFields();


        setDllLibraryPath("C:\\Users\\admin\\Desktop\\DLL PATH");


        try {
            System.load("C:\\Users\\admin\\Desktop\\DLL PATH\\chilkat.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);

        }





        CkFtp2 ftp = new CkFtp2();

        String ErrorType = "";
        if (ftp.lastErrorText().contains("530 Login authentication failed")) {
            ErrorType = " Password required for user:" + Username + " \n Login or Password was incorrect";
        }
        else if (ftp.lastErrorText().contains("Failed to get host address info. ")) {
            ErrorType = " Hostname specified does not exist, please insure that the server is up before connecting \n ";

        }



        boolean success;





        boolean isSelected = CheckBoxSSLonly.isSelected();

        boolean SaveShizz = SaveInfoCheckBtn.isSelected();


        if(isSelected == true){







            ftp.put_Ssl(true);





        }

        ftp.put_Hostname(IP);
        ftp.put_Username(Username);
        ftp.put_Password(Password);






        success = ftp.Connect();
        if (success == true) {


            if (SaveShizz == true) {


                File file = new File("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\ConnectionHistory");
                if(FileUtils.readFileToString(file).contains(IP + " " + Username + " " + Password)) {
                    //ill place a 'you have just entered saved credentials, next time for your ease, select your desired server's address from the "Saved Credentials" bar




                } else
                {
                    File Sysfile = new File("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys");

                    Stream<String> lines = Files.lines(Paths.get("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys"));
                    String SavedConnectionNumber = lines.skip(7).findFirst().get();

                    int SavedConnectINTEGAR = Integer.parseInt(SavedConnectionNumber);



                    SavedConnectINTEGAR++;



                    String NumberToPutInThere = String.valueOf(SavedConnectINTEGAR);

                    String HowManySavedConnections = String.valueOf(SavedConnectINTEGAR--);


















                    String OOFT = "ooft";

                    //String SavedConnectionNumber = Files.readAllLines(Paths.get("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys")).get(8);








                    System.out.print("New connection details detected, OOFT");
                    Path SysPath = Paths.get("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\Sys");

                    Charset charset = StandardCharsets.UTF_8;
                    String ConnectionNumeralValue = Integer.toString(UniqueConnectionCount);



                    String SafeConnectionNumeral = (SavedConnectionNumber + "1024718237409182740927834023");




                    String SysContent = new String(Files.readAllBytes(SysPath), charset);

                    SysContent = SysContent.replaceAll(SavedConnectionNumber, NumberToPutInThere);
                    Files.write(SysPath, SysContent.getBytes(charset));



                    Path path = Paths.get("C:\\Users\\admin\\Desktop\\EzyFTPClient\\src\\sample\\ConnectionHistory");


                    String HistoryContent = new String(Files.readAllBytes(path), charset);

                    HistoryContent = HistoryContent.replaceAll(SafeConnectionNumeral, IP + " " + Username + " " + Password);


                    System.out.print("New connection details detected, OOFT");




                    //1024718237409182740927834023 is something I hope no one ever has as either their username, password or host address for an FTP server

                    System.out.print(SafeConnectionNumeral);

                    //find out how many saved connections there are







                    MenuItem m1 = new MenuItem(IP + " " + Username);







                    SavedMenu.getItems().add(m1);








                    //String content = new String(Files.readAllBytes(path), charset);




                    HistoryContent = HistoryContent.replaceAll(SafeConnectionNumeral, IP + " " + Username + " " + Password);
                    Files.write(path, HistoryContent.getBytes(charset));


                }

            }
            //add to fast connection (connection without having to re enter in all your details)










            i = 0;

            UsernameLabel.setText(Username);
            UsernameLabel.setTextFill(Color.web("008000"));
            //I know I named the host button 'passwordlabel', I am too lazy to change it back..
            PasswordLabel.setText(IP);

            PasswordLabel.setTextFill(Color.web("008000"));

            Console.setText("Connection to " + IP + " Was successful :)");
            Console.setStyle("-fx-text-inner-color: green");



            Console.setText(Console.getText() + "CONNECTED TO: \n" + IP + " ");
            ConsoleUpdate();


            TimeUnit.SECONDS.sleep(5);

            RepeteTick();














        }
        else
        {


            Console.setText(Console.getText() + ErrorType + "\n");
            System.out.print(ErrorType);
            System.out.print(ftp.lastErrorText());

            System.out.print(ErrorType);
            String CHUGONTHATMILKBOY="FALSE";
            System.out.println("Exception");

            if (TxtHost.getText().equals("")) {
                Label.setText("CONNECTION TO (>)(>)NULL(<)(<) Failed!");
                CHUGONTHATMILKBOY="TRUE";

            }
            else
            {
                Label.setText("CONNECTION TO  " + IP + "  Failed!");

            }

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("CONNECTION ERROR");
            alert.setHeaderText("CONNECTION FAILED");
            Console.setStyle("-fx-text-inner-color: red");



            Console.setText(Console.getText() + "CONNECTION FAILED TO HOSTNAME: \n" + IP + " ");

            Image img = new Image("/Images/download.jpg");
            Notifications notificationBuilder = Notifications.create()

                    .title("EzyFTP")
                    .text("Connection to server   " + IP + "   Failed....")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            System.out.print("Notification was clicked");

                        }

                    });
            notificationBuilder.show();
            ConsoleUpdate();










            if (CHUGONTHATMILKBOY=="TRUE") {
                alert.setContentText(" \n Connection has failed NULL (you didn't specify host address.....) due to incorrect field properties, please insure that you have entered all the correct info in, and make sure you have an active internet connection");

            }
            else
            {

                alert.setContentText(" \n Connection has failed " + IP +  " due to incorrect field properties, please insure that you have entered all the correct info in, and make sure you have an active internet connection");

            }




            alert.showAndWait();

            Console.textProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue,
                                    Object newValue) {
                    Console.setScrollTop(Double.MAX_VALUE);

                }
            });

            Console.appendText("");
        }









        // I know why, cause 'Sysfile' is not a valid URL no matter what cause the URl does not contain the Username and pass oof stupid me but then I cannot test password or username...




















    }






    //tell console to scroll to bottom
    public void ConsoleUpdate() {
        Console.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                Console.setScrollTop(Double.MAX_VALUE);

            }
        });

        Console.appendText("");
    }


    //starting attempts
    public int Attempts = 20;
    //repeat the ConnectionTick function, which checks server connection

    public void RepeteTick(){


        class RepeteTick extends TimerTask {




            public void run() {
                if (Attempts>0) {
                    if (disconnect>0) {
                        ConnectionTick();

                    }
                    else
                    {
                        if (DisconnectMSGDisplayed==1) {
                            String IP = TxtHost.getText();
                            Console.setStyle("-fx-text-inner-color: red");

                            Console.setText(Console.getText() + "\n ");
                            Console.setText(Console.getText() + "Clearing...\n");

                            Console.setText("");
                            Console.setText(Console.getText() + " Disconnected from " + IP + "");


                            DisconnectMSGDisplayed--;

                        }


                    }






                }

            }
        }




        if (disconnect>0) {
            Timer timer = new Timer();
            timer.schedule(new RepeteTick(), 0, 5000);

        }






    }




    public int DisconnectMSGDisplayed = 1;

    public void Disconnect(ActionEvent event) {

        if(UsernameLabel.getText().equals("(not connected) ")){


            Console.setStyle("-fx-text-inner-color: orange");
            Console.setText(Console.getText() + " No connected to a server has been established ");
            ConsoleUpdate();

        }
        else
        {

            Console.setStyle("-fx-text-inner-color: orange");
            Console.setText(Console.getText() + " Disconnecting..... ");
            UsernameLabel.setTextFill(Color.web("0x0000FF"));

            PasswordLabel.setTextFill(Color.web("0x0000FF"));

            UsernameLabel.setText("Disconnected");

            PasswordLabel.setText("Disconnected");

            disconnect--;

            ConsoleUpdate();

        }








    }
    //check connection to server every 5 seconds :)
    public void ConnectionTick(){
        //define Strings to inputs/textfields
        String Username = TxtUsername.getText();
        String Password = TxtPassword.getText();
        String IP = TxtHost.getText();

        setDllLibraryPath("C:\\Users\\admin\\Desktop\\DLL PATH");

        //text chilkat lib

        try {
            System.load("C:\\Users\\admin\\Desktop\\DLL PATH\\chilkat.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);

        }




        // create chilkat FTP connection for testing (only for testing)
        CkFtp2 ftp = new CkFtp2();

        boolean success;

        if (disconnect<=0) {
            ftp.Disconnect();

        }
















        //specify IP, Username and Host for connection
        ftp.put_Hostname(IP);
        ftp.put_Username(Username);
        ftp.put_Password(Password);




        //define 'success'...
        success = ftp.Connect();
        //if connection is successful
        if (success == true) {


            i++;
            int AttemptedReconnect = 0;
            int Attempts = 20;










            //Console.setText("Ticked Connection to " + IP + " Was successful ");
            Console.setStyle("-fx-text-inner-color: green");

            if (i == 1) {


                Console.setText(Console.getText() + "\n ");
                Console.setText(Console.getText() + "connection Checked:" + IP + " for the " + i + "st time  \n");

            }
            if(i == 2) {
                Console.setText(Console.getText() + "\n ");


                Console.setText(Console.getText() + "connection Checked: " + IP + " for the " + i + "nd time \n ");

            }
            if(i == 3) {
                Console.setText(Console.getText() + "\n ");


                Console.setText(Console.getText() + "connection Checked: " + IP + " for the " + i + "rd time \n");

            }
            if(i>= 4) {
                Console.setText(Console.getText() + "\n ");


                Console.setText(Console.getText() + "connection Checked: " + IP + " for the " + i + "th time \n");

            }


            ConsoleUpdate();


        }
        //if it isn't
        else
        {
            System.out.print(ftp.lastErrorText());


            Console.setStyle("-fx-text-inner-color: red");




            if (AttemptedReconnect>0 && Attempts>0) {
                Attempts--;


                Console.setText(Console.getText() + "\n ");

                Console.setText(Console.getText() + "Attempt at reconnecting to " + IP + "Failed.... " + Attempts + " attempts remaining!");


                UsernameLabel.setTextFill(Color.web("FF8C00"));
                PasswordLabel.setTextFill(Color.web("FF8C00"));




                UsernameLabel.setText("Contended");

                PasswordLabel.setText("Contended");



            }

            if(Attempts>0) {


                Console.setText(Console.getText() + "\n ");
                Console.setText(Console.getText() + "Connection to  \n" + IP + " is no longer active, please ensure you have an active internet connection and that the FTP server is up...");
                Console.setText(Console.getText() + "\n ");
                Console.setText(Console.getText() + "Attempting reconnect to \n" + IP + " ");
                AttemptedReconnect++;
                UsernameLabel.setTextFill(Color.web("FF8C00"));
                PasswordLabel.setTextFill(Color.web("FF8C00"));




                UsernameLabel.setText("Contended");

                PasswordLabel.setText("Contended");


            }
            else
            {



                Console.setText(Console.getText() + "\n ");
                Console.setText(Console.getText() + " Attempts have timed out, please manually reinitialise connection to the server ");

                UsernameLabel.setStyle("-fx-text-inner-color: red");

                PasswordLabel.setStyle("-fx-text-inner-color: red");

                UsernameLabel.setText("*invalid*");

                PasswordLabel.setText("*invalid");


                Label.setText("Connection has officially timed out");

                Notifications notificationBuilder = Notifications.create()

                        .title("EzyFTP")
                        .text("Connection to " + IP + " has timed out....")
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT)
                        .onAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                System.out.print("S");


                            }

                        });
                notificationBuilder.show();

            }


            ConsoleUpdate();
        }



    }















        //looks like I am going to have to find a way to solve this myself, but for now, ima go do something else, hope anyone who has watched the stream has enjoyed it so far and watching me be a retard, but I am new to Java so expect to see some improvements. For now, Cya..




















    //check what has been left empty
    public void CheckFields() {

        if (TxtUsername.getText().equals("")) {
            Label.setText("Connection Failed");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("!!!!Error!!!!");
            alert.setHeaderText("Error");
            alert.setContentText(" \n No Username provided");

            alert.showAndWait();
            System.out.print(" \n Username Null Error...");
        }

        if (TxtPassword.getText().equals("")) {
            Label.setText("Connection Failed");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("!!!!Error!!!!");
            alert.setHeaderText("Error");
            alert.setContentText(" \n No Password provided");

            alert.showAndWait();

            System.out.print(" \n Password Null Error...");

        }
        if (TxtHost.getText().equals("")) {
            Label.setText("Connection Failed");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("!!!!Error!!!!");
            alert.setHeaderText("Error");
            alert.setContentText(" \n No host IP provided");

            alert.showAndWait();

            System.out.print("\n Host Null Error...");

        }

        if (Port.getText().equals("")) {
            Label.setText("Connection Failed");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("!!!!Error!!!!");
            alert.setHeaderText("Error");
            alert.setContentText("\n No port specified");

            alert.showAndWait();

            System.out.print("\n Port Null Error...");

        }

































    }




    //when SSL only checkbox is selected
    public void SSLSelected(ActionEvent event) {


    }



}












