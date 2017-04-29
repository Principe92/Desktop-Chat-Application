package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * In-memory database of the accounts that have been created.
 * Accounts are persisted to a file on server shutdown.
 */
public class AccountDB {
    private ArrayList<User> accounts;

    public AccountDB() {
        accounts = new ArrayList<User>();
    }

    /**
     * Looks through the accounts database to find an account that matches the given information
     * If it finds one it returns true, if not it returns false.
     *
     * @param username the username of the account to find
     * @param password the password of the account to find
     * @return true    if the account is found
     * false	if the account is NOT found
     */
    public boolean checkCredentials(String username, String password) {
        for (User acct : accounts) {
            if (acct.getName().equals(username) && acct.getPwd().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Writes all accounts in the database to a file
     */
    public void persist() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("AccountDB.dat"));
            out.writeObject(accounts);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Looks through the accounts database to find an account that matches the given information
     *
     * @param username the username of the account to find
     * @return true    if the username isn't in the database
     * false if the username is in the database
     */
    public boolean userIsAvailable(String username) {
        for (User acct : accounts) {
            if (acct.getName().equals(username)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates an account and adds it to the database.
     */
    public synchronized void createAccount(int id, String pwd, String name, String nick, String email) {
        User newAcct = new User(id, pwd, name, nick, email);
        accounts.add(newAcct);
        // persist();
    }
}
