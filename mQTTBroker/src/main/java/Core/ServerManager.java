package Core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerManager extends Observable {
    // Code in here
    int mPort = 6789;
    ServerSocket mServerSocket;
    Thread mThreadAccept, mThreadProcess;
    ArrayList<User> mListUser = new ArrayList<>();
    ArrayList<User> mListSubcriber = new ArrayList<>();
    ArrayList<Topic> mListTopic = new ArrayList<>();
    ArrayList<User> mListUserWaitLogout = new ArrayList<>();

    public ServerManager(Observer obs)  //Create a server socket when you don't have a socket
    {
        this.addObserver(obs);
    }

    public ServerManager(ServerSocket serverSocket, Observer obs)   //Create a server socket when you have a socket
    {
        this.addObserver(obs);
        mServerSocket = serverSocket;
    }

    public void Dispose() throws IOException {
        if (mThreadAccept != null) {
            mThreadAccept.stop();
            mThreadProcess.stop();
            mServerSocket.close();
        }
    }

    public boolean StartServer() //run server

    {
        try {
            mServerSocket = new ServerSocket(mPort);
            StartThreadAccept();
            StartThreadProcess();
            notifyObservers("Broker start!");
            return true;
        } catch (IOException ex) {
            notifyObservers("Can't start broker( maybe port in use)");
            notifyObservers(ex);
            return false;
        }
    }

    void StartThreadAccept()   //Start thread to accept client
    {
        mThreadAccept = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Socket socket = mServerSocket.accept();
                        User newUser = new User(socket);
                        newUser.mTimeConnect = new Date();
                        mListUser.add(newUser);
                    }
                } catch (IOException ex) {
                    notifyObservers("Connection error!");
                }
            }
        });
        mThreadAccept.start();
    }

    void StartThreadProcess()  //Start thread to process request
    {
        mThreadProcess = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        CheckRequest();
                        CheckTimeConnect();

                        if (mListUserWaitLogout.size() > 0)  //Remove user waitting
                            RemoveUserLoggedOut();

                        Thread.sleep(0);  //Server can receive request
                    }

                } catch (IOException ex) {
                    notifyObservers("Connection error!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mThreadProcess.start();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    void CheckRequest() throws IOException {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            //Don't use dataInputStream() from a client
            String request = user.Read();
            if (request != null)
                ProcessRequest(user, request);
        }
    }

    void CheckTimeConnect() {
        Date now = new Date();
        int size = mListUser.size();
        long second = 0;
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.mLogined == false) {
                second = (now.getTime() - user.mTimeConnect.getTime()) / 1000;
                if (second > 5) //Delay 5s cho ket noi
                {
                    mListUserWaitLogout.add(user);
                }
            }
        }
    }

    void RemoveUserLoggedOut() {
        int size = mListUserWaitLogout.size();
        for (int i = 0; i < size; i++) {
            User user = mListUserWaitLogout.get(i);
            mListUser.remove(user);
        }
        mListUserWaitLogout.clear();
    }

    void ProcessRequest(User user, String request) {
        String[] lines = request.split(";");
        String actionType = lines[0];
        switch (actionType) {
            // Reqest login
            case ActionType.LOGIN: {
                String userName = lines[1];  //request form : actionType;username
                if (CheckUserName(userName)) {
                    user.mUserName = userName;
                    user.mLogined = true;
//                    user.mPubSub = "Publisher";
                    user.Send(actionType, ResultCode.OK, "OK");
                    notifyObservers(user.mUserName + " sucsessfully login!");
                } else {
                    user.Send(actionType, ResultCode.ERROR, "Name is existed!");
                }
                break;
            }
            case ActionType.PUBSUB: {
                String pubsub = lines[1];  //Request form: actionType;pubsub
                if (pubsub.equals("Publisher")) {
                    user.mPubSub = "Publisher";
                    user.Send(actionType, ResultCode.OK, "OK");
                    notifyObservers(user.mUserName + " is a Publisher");
                } else if (pubsub.equals("Subcriber")) {
                    user.mPubSub = "Subcriber";
                    user.Send(actionType, ResultCode.OK, "OK");
                    notifyObservers(user.mUserName + " is a Subcriber");
                } else {
                    user.Send(actionType, ResultCode.ERROR, "Choose Pub/Sub.");
                }
                break;
            }
            case ActionType.CREATE_TOPIC: {
                if (user.mPubSub.equals("Publisher")) {
                    String topicName = lines[1];  //Request form: actionType;topicName
                    Topic topic = GeneralTopic(topicName);
                    mListTopic.add(topic);
                    user.mTopic = topic;
                    if (user.Send(actionType, ResultCode.OK, topic.mTopicID))
                        topic.AddUser(user);
                    notifyObservers(user.mUserName + " created " + topicName);
                } else {
                    user.Send(actionType, ResultCode.ERROR, "Can't create topic");
                }
                break;
            }
            case ActionType.GET_LIST_TOPIC: {
                int size = mListTopic.size();//Request form: actionType;
                int rowsPerBlock = 500;  //Max 500 topic per block (if size > 500 then send more than 1 block)
                if (size > 0) {
                    String listTopic = "";
                    int start = 0;
                    int end = 0;
                    int numberBlock = (int) Math.floor(size / (double) rowsPerBlock);
                    for (int i = 0; i < numberBlock; i++) {
                        start = i * rowsPerBlock;
                        end = start + rowsPerBlock;
                        listTopic = "";
                        for (int j = start; j < end; j++) {
                            Topic topic = mListTopic.get(j);
                            listTopic += topic.mTopicID + "<col>" + topic.mTopicName + "<col>" + topic.CountUser() + "<col>" + "<row>";
                        }
                        System.out.print("Gửi lần thứ: " + i);
                        user.Send(actionType, ResultCode.OK, listTopic);
                    }

                    listTopic = "";
                    for (int i = end; i < size; i++) //Send last block
                    {
                        Topic topic = mListTopic.get(i);
                        listTopic += topic.mTopicID + "<col>" + topic.mTopicName + "<col>" + topic.CountUser() + "<col>" + "<row>";
                    }
                    user.Send(actionType, ResultCode.OK, listTopic);
                } else {
                    user.Send(actionType, ResultCode.OK, "");
                }
                notifyObservers(user.mUserName + " get list topic");
                break;
            }
            case ActionType.JOIN_TOPIC: {
                String topicID = lines[1];   //Request form: actionType;topicID
                int size = mListTopic.size();
                boolean success = false;
                for (int i = 0; i < size; i++) {
                    Topic topic = mListTopic.get(i);
                    if (topic.mTopicID.equals(topicID)) {
                        topic.AddUser(user);
                        user.mTopic = topic;
                        user.Send(actionType, ResultCode.OK, topicID);
                        notifyObservers(user.mUserName + " joined topicID:" + topic.mTopicID);
                        user.mTopic.UpdateNumberUser();
                        user.mTopic.NotifyJustJoinTopic(user);
                        success = true;
                    }
                }
                if (success == false) {
                    user.Send(actionType, ResultCode.ERROR, "Topic not found");
                    notifyObservers(user.mUserName + " can't join " + topicID);
                }

                break;
            }
            case ActionType.SEND_MESSAGE: {
                if (user.mPubSub.equals("Publisher")) {
                    String contentMess = "";
                    if (lines.length >= 2)
                        contentMess = lines[1];   //Request form: actionType;contentMess
                    user.mTopic.SendToAllUser(user.mUserName, contentMess);
                    notifyObservers(user.mUserName + " send message to topicID:" + user.mTopic.mTopicID);
                } else {
                    user.Send(actionType, ResultCode.ERROR, "You are not a Publisher!");
                }
                break;
            }
            case ActionType.LEAVE_TOPIC:    //Request form: actionType;
            {
//                // Check topic list to remove user
                int size = mListTopic.size();
                for (int i = 0; i < size; i++) {
                    Topic topic = mListTopic.get(i);
                    if (topic != null) {
                        topic.RemoveUser(user);
                        if (topic.CountUser() > 0) {
                            topic.NotifyJustLeaveTopic(user);
                            topic.UpdateNumberUser();
                        } else
                            mListTopic.remove(topic);
                    }
                }
                notifyObservers(user.mUserName + " leave " + user.mTopic.mTopicID);
                break;
            }
            case ActionType.LOGOUT:    //Request form: actionType;
            {
                int size = mListTopic.size();
                for (int i = 0; i < size; i++) {
                    Topic topic = mListTopic.get(i);
                    if (topic != null) {
                        topic.RemoveUser(user);
                        if (topic.CountUser() > 0) {
                            topic.NotifyJustLeaveTopic(user);
                            topic.UpdateNumberUser();
                        } else
                            mListTopic.remove(topic);
                    }
                }
                // Same with leave topic
                mListUserWaitLogout.add(user);
                notifyObservers(user.mUserName + " logout.");
                break;

            }
        }
    }

    boolean CheckUserName(String userName) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            if (userName.equals(mListUser.get(i).mUserName))  //User name is exist
                return false;
        }
        return true;
    }

    //Create new topic
    Topic GeneralTopic(String topicName) {
        Topic topic = new Topic();
        topic.mTopicName = topicName;
        topic.mMemsOfTopic = 1;
        topic.mTopicID = GeneralTopicID();
        return topic;
    }

    int maxChar = 3;

    //Create new topic ID
    String GeneralTopicID() {
        int countRandom = 0;
        //int maxChar = 3;
        String topicID = "";
        do {
            if (countRandom > 50) //if id unavaiable after 50 times then scale up
                maxChar++;

            topicID = RandomString(maxChar);
            countRandom++;  //tăng số lần đềm random
        } while (CheckTopicID(topicID) == false);
        return topicID;

    }

    //Check topic ID is exist
    boolean CheckTopicID(String topicID) {
        int size = mListTopic.size();
        for (int i = 0; i < size; i++) {
            Topic topic = mListTopic.get(i);
            if (topic.mTopicID.equals(topicID))
                return false;
        }
        return true;
    }

    //Random string
    String RandomString(int length) {
        String data = "1234567890qwertyuiopasdfghjklzxcvbnm";
        int sizeData = data.length();
        String result = "";
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            result += data.charAt(rd.nextInt(sizeData));
        }
        return result;
    }

    public int CountUser() {
        return mListUser.size();
    }

    public int CountTopic() {
        return mListTopic.size();
    }
}
