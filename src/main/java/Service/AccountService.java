package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();

    }
    public AccountService(AccountDAO aDAO){
        accountDAO = aDAO;
    }

    public Account addAccount(Account account){
        if((account.getUsername().length()>0)&&(account.getPassword().length()>=4)&&(checkForAccount(account)==null))
            return accountDAO.addAccount(account);
        return null;
    }
    public Account checkForAccount(Account account){
        List<Account> accounts = accountDAO.getAllAccounts();
        for (Account acc : accounts) {
            if (acc.getUsername().equals(account.getUsername()) && acc.getPassword().equals(account.getPassword())) {
                return acc; // Match found
            }
        }
        return null;
    }
    public Account checkForAccountbyID(int id){
        List<Account> accounts = accountDAO.getAllAccounts();
        for (Account acc : accounts) {
            if (acc.getAccount_id()==id) {
                return acc; // Match found
            }
        }
        return null;
    }
}
