package API;

import java.io.IOException;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ConnectionController;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.CheckBox;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import API.easyFTP;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//I know this isn't needed, but I thought I will just do it ;/

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import static java.lang.System.out;
import static java.lang.System.*;
//import com.adeel.library.easyFTP;
import API.AbstractFTPClient;
import API.FTPFileInfoVO;
import API.FTPLogCommandListener;
import API.FTPTransferMode;
import API.ServerToClient;
import API.ServerToServer;
/**
 * AbstractFTPClient abstract a basic work flow when store or retrieve file
 * from an FTP server, and defined some necessary functionality in the flow.
 * Some common functionality are provided, and the others are need to implement.
 *
 * @author venson
 */
public abstract class AbstractFTPClient {

    /**
     * The logger of this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractFTPClient.class);

    /**
     * The regular expression of FTP URL.
     */
    private static final String REGEX_FTP_URL = "^ftp://(.*):(.*)@(\\d+\\.\\d+\\.\\d+\\.\\d+):?(\\d+)?(/.+[^/])$";

    /**
     * The separator of directory.
     */
    private static final String STR_DIR_SEPARATOR = "/";

    /**
     * The max count of retry time.
     */
    private int retryTimes = 0;

    /**
     * The count of retry time.
     */
    private int currentRetryTimes = 0;

    /**
     * The interval wait time between retries.(1 second)
     */
    private long retryWaitTime = 1L * 1000L;

    /**
     * The resume broken flag.
     */
    private boolean resumeBroken = true;

    /**
     * Get the max count of retry time.
     *
     * @return The max count of retry time.
     */
    public int getRetryTimes() {
        return retryTimes;
    }

    /**
     * Set the max count of retry time.
     *
     * @param retryTimes The max count of retry time.
     */
    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    /**
     * Get the count of retry time.
     *
     * @return The count of retry time.
     */
    public int getCurrentRetryTimes() {
        return currentRetryTimes;
    }

    /**
     * Get the interval wait time between retries.(second)
     *
     * @return The interval wait time between retries.(second)
     */
    public long getRetryWaitTime() {
        return retryWaitTime / 1000L;
    }

    /**
     * Set the interval wait time between retries.(second)
     *
     * @param retryWaitTime The interval wait time between retries.(second)
     */
    public void setRetryWaitTime(long retryWaitTime) {
        this.retryWaitTime = retryWaitTime * 1000L;
    }

    /**
     * Get the resume broken flag.
     *
     * @return The resume broken flag.
     */
    public boolean isResumeBroken() {
        return resumeBroken;
    }

    /**
     * Set the resume broken flag.
     *
     * @param resumeBroken The resume broken flag.
     */
    public void setResumeBroken(boolean resumeBroken) {
        this.resumeBroken = resumeBroken;
    }

    /**
     * The basic work flow when store or retrieve file from an FTP server.
     *
     * @return The result of work flow.
     */
    protected boolean doTransferFlow() {

        boolean result = false;

        while (true) {

            try {

                // Connect to FTP server.
                if (!connect()) {
                    continue;
                }

                // Login to FTP server.
                if (!login()) {
                    continue;
                }

                // Transfer the files
                if (!doTransfer()) {
                    continue;
                } else {
                    result = true;
                }

            } catch (IOException e) {
                logger.error("DoTransferFlow failed.", e);
            } finally {

                logoutAndDisconnect();

                if (!result && shouldRetry()) {
                    waitForRetry();
                } else {
                    return result;
                }
            }
        }
    }

    /**
     * Connect to FTP server.
     *
     * @throws IOException
     */
    protected abstract boolean connect() throws IOException;

    /**
     * Login to FTP server.
     *
     * @throws IOException
     */
    protected abstract boolean login() throws IOException;

    /**
     * Logout and disconnect from FTP server.
     */
    protected abstract void logoutAndDisconnect();

    /**
     * Store or retrieve file from FTP server.
     *
     * @return The result of file transfer.
     * @throws IOException
     */
    protected abstract boolean doTransfer() throws IOException;

    /**
     * Parse the FTP URL to value object.
     *
     * @param url The FTP URL.
     * @return The value object with FTP information.
     */
    public static FTPFileInfoVO convertFTPUrlToVO(String url) {


        FTPFileInfoVO vo = new FTPFileInfoVO();

        Matcher matcher = Pattern.compile(REGEX_FTP_URL).matcher(url);
        //oh shit, so the matcher is pretty much determining if you can connect to the server or not, shit I didn't realise, I thought "Pattern.compile(REGEX_FTP_URL).matcher(url);" was just checking if it were a URL

        if (matcher.find()) { //invalid address type ?! :(
            vo.setUserName(matcher.group(1));
            vo.setPassword(matcher.group(2));
            vo.setIp(matcher.group(3));
            vo.setPort(matcher.group(4));
            vo.setUrl(url);
            String fileFullPath = matcher.group(5);
            vo.setFileFullPath(fileFullPath);
            if (fileFullPath.lastIndexOf(STR_DIR_SEPARATOR) > 0) {
                vo.setFileDirectory(fileFullPath.substring(0, fileFullPath.lastIndexOf(STR_DIR_SEPARATOR)));
                vo.setFileName(fileFullPath.substring(fileFullPath.lastIndexOf(STR_DIR_SEPARATOR) + 1));
            } else {
                vo.setFileDirectory(null);
                vo.setFileName(fileFullPath);
            }

        } else {
            logger.error(url + "is invalid.");
            throw new IllegalArgumentException(url + "is invalid.");













        }

        return vo;
    }

    /**
     * Connect to FTP server.
     *
     * @param ftp The instance of FTP client.
     * @param vo The value object with FTP information.
     * @return The result of connection.
     * @throws IOException
     */
    protected boolean connect(FTPClient ftp, FTPFileInfoVO vo) throws IOException {

        // When the port is not set, use the default value to connect FTP server.
        if (vo.getPort() == null || vo.getPort().length() == 0) {
            ftp.connect(vo.getIp());

        // Then use the specified port.
        } else {
            ftp.connect(vo.getIp(), Integer.parseInt(vo.getPort()));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Connected to " + vo.getIp() + " on " + ftp.getRemotePort());
        }

        // Check the connection status.
        if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            logger.error("FTP server refused connection.");
            return false;
        }

        return true;
    }

    /**
     * Change the working directory to the given path.
     *
     * @param ftp The instance of FTP client.
     * @param path The absolute path on FTP server.
     * @return The result of changing directory.
     */
    protected boolean changeDir(FTPClient ftp, String path) {

        if (path == null || path.length() == 0) {
            return true;
        }

        try {

            // Because of using the absolute path, change the working directory to root first.
            if (!ftp.changeWorkingDirectory(STR_DIR_SEPARATOR)) {
                return false;
            }

            String[] dirs = path.split(STR_DIR_SEPARATOR);

            // Change the path step by step.
            for (String dir : dirs) {

                // When the path begin with "/" or "//", after the split will get empty string,
                // must be skipped.
                if (dir.length() == 0) {
                    continue;
                }

                // If failed to change the directory, create it.
                if (!ftp.changeWorkingDirectory(dir)) {

                    if (ftp.makeDirectory(dir) && ftp.changeWorkingDirectory(dir)) {
                        continue;
                    } else {
                        return false;
                    }
                }
            }

        } catch (IOException e) {
            logger.error("Failed to ChangeDir to " + path + " .", e);
            return false;
        }

        return true;
    }

    /**
     * Determine whether to retry.
     *
     * @return The result of whether to retry.
     */
    protected boolean shouldRetry() {

        if (currentRetryTimes < retryTimes) {
            currentRetryTimes++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Wait and retry.
     */
    protected void waitForRetry() {

        try {
            Thread.sleep(retryWaitTime);
        } catch (InterruptedException e) {
            logger.error("WaitForRetry failed." ,e);
        }
    }
}
