package libraries;

import java.io.Serializable;

/**
 * The PDU used to communicate between Server and Client, it contais the 
 * message type and the sent User.
 *
 * @author Andoni Sanz
 */
public class ApplicationPDU implements Serializable{
    private MessageType messageType;
    private User user;

    public ApplicationPDU() {
    }

    public ApplicationPDU(MessageType messageType, User user) {
        this.messageType = messageType;
        this.user = user;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public User getUser() {
        return user;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
