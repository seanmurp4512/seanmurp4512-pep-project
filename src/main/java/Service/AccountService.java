package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();

    }
    public AccountService(AccountDAO aDAO){
        accountDAO = aDAO;
    }

    public Account addAccount(Account account){
        if((account.getUsername().length()>0)&&(account.getPassword().length()>=4))
            return accountDAO.addAccount(account);
        return null;
    }
}
