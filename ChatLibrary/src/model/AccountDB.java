package model;

import java.io.*;
import java.util.ArrayList;

/**
 * In-memory database of the accounts that have been created.
 * Accounts are persisted to a file on server shutdown.
 */
public class AccountDB {
    private ArrayList<User> accounts;

    public AccountDB() {
        accounts = new ArrayList<User>();
        populateAccounts("AccountDB.txt");
    }

    /**
     * Looks through the accounts database to find an account that matches the given information
     * If it finds one it returns true, if not it returns false.
     *
     * @param filename the password of the account to find
     * @return true    if the account is found
     * false	if the account is NOT found
     */

    private void populateAccounts(String filename) {
        try {
            File file = new File(filename);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCredentials(String username, String password) {
        for (User acct : accounts) {
            if (acct.getName().equals(username) && acct.getPwd().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Appends new accounts to file
     */
    private void addNewAccount(String pwd, String name, String nick, String email) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("AccountDB.txt", true));
            bw.write(name + " " + pwd + " " + nick + " " + email);
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
            if (acct.getName().equals(username)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates an account and adds it to the database.
     */
    public synchronized void createAccount(String pwd, String name, String nick, String email) {
        User newAcct = new User(pwd, name, nick, email);
        accounts.add(newAcct);
        addNewAccount(pwd, name, nick, email);
    }
}
