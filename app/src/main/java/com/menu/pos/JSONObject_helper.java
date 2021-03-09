package com.menu.pos;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class JSONObject_helper {

    public Map[][] catch_account_and_password(){
        Map<Integer , String> operating_account = new HashMap();
        Map<String , String> operating_password = new HashMap();
        Map<Integer , String> client_account = new HashMap();
        Map<String , String> client_password = new HashMap();
        String path = "account.json";

        try (BufferedReader read_account = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            String account_datas = "";
            String count;
            while((count = read_account.readLine()) != null){
                account_datas += count;
            }
            org.json.JSONObject accounts_passwords = new org.json.JSONObject(account_datas);

            org.json.JSONObject operating = accounts_passwords.getJSONObject("operating");
            org.json.JSONObject client = accounts_passwords.getJSONObject("client");

            JSONArray operating_accounts = accounts_passwords.getJSONArray("account");
            org.json.JSONObject operating_passwords = accounts_passwords.getJSONObject("password");
            JSONArray client_accounts = accounts_passwords.getJSONArray("account");
            org.json.JSONObject client_passwords = accounts_passwords.getJSONObject("password");

            for(int i = 0 ; i < operating_accounts.length() ; i ++){
                operating_account.put(i , operating_accounts.getString(i));
                operating_password.put(operating_account.get(i) , operating_passwords.getString(operating_account.get(i)));
            }
            for(int i = 0 ; i < client_accounts.length() ; i ++){
                client_account.put(i , client_accounts.getString(i));
                client_password.put(client_account.get(i) , client_passwords.getString(client_account.get(i)));
            }

        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            return new Map[][]{{}};
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return new Map[][]{{operating_account , operating_password} , {client_account , client_password}};
        }
    }
}
