package model;

import main.Constant;

import java.io.*;
import java.util.ArrayList;

/**
 * In-memory database of the accounts that have been created.
 */
public class AccountDB {

    private static final String ACCOUNT_PATH = String.format("%s/AccountDB.txt", Constant.DEFAULT_PATH);
    private ArrayList<User> accounts;

    public AccountDB() {
        accounts = new ArrayList<>();
        populateAccounts(ACCOUNT_PATH);
    }

    /**
     * Looks through the accounts database to find an account that matches the given information
     * If it finds one it returns true, if not it returns false.
     *
     * @return true    if the account is found
     * false	if the account is NOT found
     */

    private void populateAccounts(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String trimmed = line.trim();
                    String[] splitted = trimmed.split(" ");
                    User u = new User(splitted[1], splitted[0], splitted[2], splitted[3]);
                    accounts.add(u);
                }
                fileReader.close();
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User checkCredentials(String username, String password) {
        for (User acct : accounts) {
            if (acct.getUsername().equals(username) && acct.getPwd().equals(password)) {
                return acct;
            }
        }
        return null;
    }

    /**
     * Appends new accounts to file
     */
    private void addNewAccount(String pwd, String name, String nick, String email) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ACCOUNT_PATH, true));
            bw.write("username: " + name + "password: " + pwd + "nickname: " + nick + "email: " + email);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
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
            if (acct.getUsername().equals(username)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates an account and adds it to the database.
     */
    public User createAccount(String pwd, String name, String nick, String email) {
        User newAcct = new User(pwd, name, nick, email);
        accounts.add(newAcct);
        addNewAccount(pwd, name, nick, email);
        return newAcct;
    }
}
