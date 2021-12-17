package Core;

import java.util.ArrayList;

public class Topic {
    public String mTopicID;
    public String mTopicName;
    public int mMemsOfTopic;

    ArrayList<User> mListUser = new ArrayList<>();

    public void AddUser(User user) {
        mListUser.add(user);
    }

    public void RemoveUser(User user) {
        mListUser.remove(user);
    }

    public int CountUser() {
        return mListUser.size();
    }

    public void SendToAllUser(String sender, String content) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.Send(ActionType.SEND_MESSAGE, ResultCode.OK, sender + ";" + content) == false) {
                NotifyJustLeaveTopic(user);
            }
        }
    }

    public void UpdateNumberUser() {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user.Send(ActionType.UPDATE_NUMBER_USER, ResultCode.OK, size + "") == false) {
                NotifyJustLeaveTopic(user);
            }
        }
    }

    public void NotifyJustJoinTopic(User userJoin) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user != userJoin) {
                user.Send(ActionType.NOTIFY_JUST_JOIN_TOPIC, ResultCode.OK, userJoin.mUserName);
            }
        }
    }

    public void NotifyJustLeaveTopic(User userLeave) {
        int size = mListUser.size();
        for (int i = 0; i < size; i++) {
            User user = mListUser.get(i);
            if (user != userLeave) {
                user.Send(ActionType.NOTIFY_JUST_LEAVE_TOPIC, ResultCode.OK, userLeave.mUserName);
            }

        }
    }
}
