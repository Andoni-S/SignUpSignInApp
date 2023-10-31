package libraries;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
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
