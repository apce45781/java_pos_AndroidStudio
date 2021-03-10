package com.menu.pos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toast_show toast;

    private EditText Etext_account , Etext_password;
    private Button sign_in , leave , forget , registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findviewbyid();

        toast = new Toast_show(this);

        JSONObject_helper json = new JSONObject_helper();
        Map[][] account_and_password = json.catch_account_and_password();


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in(account_and_password);
            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registered(account_and_password);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    public void sign_in(Map[][] account_and_password){
        String user_account = Etext_account.getText().toString();
        String user_password = Etext_password.getText().toString();

        if(user_account.isEmpty()){
            toast.show("請輸入帳號").show();
        }else if(user_password.isEmpty()){
            toast.show("請輸入密碼").show();
        }else{
            int operating_number = 0;
            int client_number = 1;

            boolean switch_operating = judgment_account(account_and_password[operating_number] , user_account , user_password);
            boolean switch_client = judgment_account(account_and_password[client_number] , user_account , user_password);

//            if(switch_operating){
                if(true){
                operating();
            }else if(switch_client){
                client();
            }else{
                toast.show("帳號密碼錯誤").show();
            }
        }
    }

    public void operating(){
        Intent intent = new Intent();
        EditText name = new EditText(MainActivity.this);

        new AlertDialog.Builder(MainActivity.this).setTitle("請輸入公司名號").setView(name).setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String company_name = name.getText().toString();
                if(!company_name.isEmpty()){
                    intent.putExtra("company_name" , company_name);
                    intent.setClass(MainActivity.this , Operating.class);
                    startActivity(intent);
                }
            }
        }).show();
    }

    public void client(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this , Clinet.class);
        startActivity(intent);
    }

    public void registered(Map[][] account_and_password){
        View view = getLayoutInflater().inflate(R.layout.registered , null);
        EditText user_account = view.findViewById(R.id.edit_new_account);
        EditText user_password = view.findViewById(R.id.edit_new_password);
        EditText user_password_again = view.findViewById(R.id.edit_new_password_again);
        new AlertDialog.Builder(MainActivity.this).setTitle("申請").setView(view).setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String String_account = user_account.getText().toString();
                String String_password = user_password.getText().toString();
                String String_password_again = user_password_again.getText().toString();
                if(String_account.isEmpty()){
                    toast.show("請輸入帳號").show();
                }else if(String_password.isEmpty()){
                    toast.show("請輸入密碼").show();
                }else if(String_password_again.isEmpty()){
                    toast.show("請再次輸入密碼").show();
                }else if(!String_password.equals(String_password_again)){
                    toast.show("密碼不相符").show();
                }else{
                    int account_number = 0;
                    int operating_number = 0;
                    int client_number = 1;
                    boolean switch_account = false;
                    for(int i = 0 ; i < account_and_password[0][0].size(); i ++){
                        switch_account = account_and_password[operating_number][account_number].get(i).equals(String_account);
                        switch_account = account_and_password[client_number][account_number].get(i).equals(String_account);
                        if(switch_account){
                            break;
                        }
                    }
                    if(switch_account){
                        toast.show("帳號已被申請過").show();
                    }else{
                        new JSONObject_helper().add(String_account , String_password);
                    }
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    public boolean judgment_account(Map[] account_and_password , String user_account , String user_password){
        int account_number = 0;
        int password_number = 1;
        for(int i = 0 ; i < account_and_password[0].size() ; i ++){
            String account = account_and_password[account_number].get(i).toString();
            String password = account_and_password[password_number].get(account).toString();
            if(account.equals(user_account) && password.equals(user_password)){
                return true;
            }
        }
        return false;
    }

    public void findviewbyid(){
        Etext_account = findViewById(R.id.edit_account);
        Etext_password = findViewById(R.id.edit_password);
        sign_in = findViewById(R.id.button_sign_in);
        leave = findViewById(R.id.button_leave);
        forget = findViewById(R.id.button_forget);
        registered = findViewById(R.id.button_registered);
    }
}