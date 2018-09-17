import Abstract.NonActionableObject;
import Enums.EnumCSMessagePurpose;

public class CS_Message extends NonActionableObject{
    EnumCSMessagePurpose csMessagePurpose;
    String csMessageContents;
    int csMessageSenderId;
    int csMessageReceiverId;

    public CS_Message() {
        super();
        csMessagePurpose = null;
        csMessageContents = "noContents";
        csMessageSenderId = -1;
        csMessageReceiverId = -1;
    }
}
