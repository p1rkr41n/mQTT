/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientManager extends Observable {

    public String mUsername;
    String serverName = "localhost";
    int port = 6789;
    Socket mSocket;
    BufferedWriter mBufferWriter;
    DataInputStream mDataInputStream;
    Thread mThread;

    public ClientManager(Observer obs)  //Create a server socket when you don't have a socket
    {
        this.addObserver(obs);
    }

    public ClientManager(Socket socket, Observer obs) //Create a server socket when you have a socket
    {
        this.addObserver(obs);
        mSocket = socket;
    }

    public void Dispose() {
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (mThread != null)
            mThread.stop();
    }

    public boolean StartConnect() {
        try {
            mSocket = new Socket(serverName, port);
            //use data input stream to read data from server
            mDataInputStream = new DataInputStream(mSocket.getInputStream());
            mBufferWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), StandardCharsets.UTF_8));
            StartThreadWaitResult();
            return true;
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server");
            notifyObservers(result);
            return false;
        }
    }

    void StartThreadWaitResult() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String[] lines = mDataInputStream.readUTF().split(";", -1);
                        Result result;
                        if (lines.length == 3)
                        {
                            result = new Result(lines[0], lines[1], lines[2]);
                        } else
                        {
                            String content = "";
                            for (int i = 2; i < lines.length; i++)
                            {
                                content += lines[i] + ";";
                            }
                            result = new Result(lines[0], lines[1], content);
                        }
                        notifyObservers(result);
                    }
                } catch (IOException ex) {
                    Result result = new Result("", ResultCode.ERROR, "Can't connect to server");
                    notifyObservers(result);
                }
            }
        });
        mThread.start();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    public void SendMess(String mess) {
        mess = mess.replaceAll("\\n", "<br>");
        String line = ActionType.SEND_MESSAGE + ";" + mess;
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }

    public void Login(String userName) throws UnsupportedEncodingException
    {
        String line = ActionType.LOGIN + ";" + userName;
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }

    public void Pubsub(String pubsub) {
        String line = ActionType.PUBSUB + ";" + pubsub;
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }

    public void GetTopicList() {
        String line = ActionType.GET_LIST_TOPIC + ";";
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }

    public void CreateTopic(String topicName) {
        String line = ActionType.CREATE_TOPIC + ";" + topicName;
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }

    public void JoinTopic(String topicID) {
        String line = ActionType.JOIN_TOPIC + ";" + topicID;
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }

    public void LeaveTopic() {
        String line = ActionType.LEAVE_TOPIC + ";null";
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }

    public void Logout() {
        String line = ActionType.LOGOUT + ";null";
        try {
            mBufferWriter.write(line + "\n");
            mBufferWriter.flush();
        } catch (IOException ex) {
            Result result = new Result("", ResultCode.ERROR, "Can't connect to server!");
            notifyObservers(result);
        }
    }


}
