package Controller;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterUser);
        app.post("/login",this::postLoginUser);
        app.post("/messages", this::postCreateMessage);
        app.get("/messages",this::getAllMessages);
        app.get("/messages/{message_id}",this::getMessagebyID);
        app.delete("/messages/{message_id}",this::deleteMessagebyID);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUser);
        app.patch("/messages/{message_id}",this::patchMessagebyID);
        return app;
    }

    /**
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterUser(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }

    }

    private void postLoginUser(Context context)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account caccount = accountService.checkForAccount(account);

        if(caccount != null){
            context.json(mapper.writeValueAsString(caccount));
        }else{
            context.status(401);
        }
    }

    private void postCreateMessage(Context context)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = messageService.addMessage(mapper.readValue(context.body(), Message.class));
        if((message != null)&& (accountService.checkForAccountbyID(message.posted_by))!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(400);
        }
    }
    private void getAllMessages(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessagebyID(Context context)throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessagebyID(id); 
        if(message.getMessage_id()==id)
            context.json(message);
        else
            context.status(200).result("");
    }

    private void deleteMessagebyID(Context context)throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessagebyID(id); 
        if(message.getMessage_id()==id)
            context.json(message);
        else
            context.status(200).result("");
    }

    private void getAllMessagesFromUser(Context context)throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromUser(id);
        context.json(messages);
    }

    private void patchMessagebyID(Context context)throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        message = messageService.patchMessagebyID(message.getMessage_text(), id);
        if(message!=null){
            context.json(message);
        }else{
            context.status(400);
        }
    }
}