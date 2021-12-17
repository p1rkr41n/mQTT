package Core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class User {
    Socket mSocket;
    BufferedReader mBufferReader;
    DataOutputStream mDataOutputStream;
    public String mUserName;
    public Topic mTopic;
    public Date mTimeConnect; //time connect to server
    public boolean mLogined = false;   //status of user
    public String mPubSub;

    public User(Socket socket) throws IOException {
        mSocket = socket;
        mBufferReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), StandardCharsets.UTF_8));
        mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());  //Method to send data to server
    }

    public String Read() throws IOException {
        if (mBufferReader.ready()) {
            return mBufferReader.readLine();
        }
        return null;
    }

    public boolean Ready() throws IOException {
        return mBufferReader.ready();
    }

    public Boolean Send(String actionType, String resultCode, String content) {
        try {
            mDataOutputStream.writeUTF(actionType + ";" + resultCode + ";" + content);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        }
    }

    public Boolean IsOnline() {
        return Send(ActionType.CHECK_ONLINE, ResultCode.OK, "");
    }
}
