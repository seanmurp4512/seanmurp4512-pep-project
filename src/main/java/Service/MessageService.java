package Service;

import Model.Message;
import DAO.MessageDAO;
import Service.AccountService;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    public MessageService(MessageDAO mDAO){
        messageDAO = mDAO;
    }

    public Message addMessage(Message message){
        if((message.getMessage_text().length()>0) && (message.getMessage_text().length() <= 255))
            return messageDAO.addMessage(message);
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessagebyID(int id){
        return messageDAO.getMessagebyID(id);
    }

    public Message deleteMessagebyID(int id){
        Message message = messageDAO.getMessagebyID(id);
        messageDAO.deleteMessagebyID(id);
        return message;
    }
    
    public List<Message> getAllMessagesFromUser(int id){
        return messageDAO.getAllMessagesFromUser(id);
    }

    public Message patchMessagebyID(String text, int id){
        if((text.length()>0)&&(text.length()<256)){
            boolean f = messageDAO.patchMessagebyID(text, id);
            Message message = getMessagebyID(id);
            if((f)&& message.getMessage_text().equals(text))
                return message;
        }
        return null;
    }
}
    

