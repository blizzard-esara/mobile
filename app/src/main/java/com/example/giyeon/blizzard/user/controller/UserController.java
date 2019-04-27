package com.example.giyeon.blizzard.user.controller;

import android.util.Log;

import com.example.giyeon.blizzard.user.dto.SimpleData;
import com.example.giyeon.blizzard.user.dto.UserData;
import com.example.giyeon.blizzard.user.model.NetworkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserController {


    private NetworkTask networkTask;

    private UserController() {

    }

    private static class LazyHolder {
        public static final UserController INSTANCE = new UserController();
    }
    public static UserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    public boolean checkOverlapId(String id) {
        String result = "";
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/checkId",
                "id="+id);

            try {
                result = networkTask.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        return Boolean.valueOf(result);
    }

    public boolean signUp(String id, String pw, String email, String phone, String age, String gender, boolean subscription) {
        int sub;
        String result = "";
        if(subscription) sub = 1;
        else sub = 0;
        String parms = "id="+id+"&pw="+pw+"&email="+email+"&phone="+phone+"&age="+age+"&gender="+gender+"&subscription="+sub;
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/signUp", parms);

        try {
            result = networkTask.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return Boolean.parseBoolean(result);
    }

    public boolean checkLogin(String id, String pw) {

        String result = "";
        boolean loginCheck = false;


        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/loginUser",
                "id="+id+"&pw="+pw);

        try {
            result = networkTask.execute().get();

            if(!result.equals("[]")) {
                JSONArray jsonArray = new JSONArray(result);

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                UserData.getInstance().setId(jsonObject.get("id").toString());
                UserData.getInstance().setPhone(jsonObject.get("phone").toString());
                UserData.getInstance().setGender(jsonObject.get("gender").toString());
                UserData.getInstance().setAge(jsonObject.get("age").toString());
                loginCheck = true;
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loginCheck;
    }

    public boolean initalCheck(String id) {
        String result = "";

        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/initalCheck",
                            "id="+id);

            try {
                result = networkTask.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return Boolean.parseBoolean(result);
    }

    public boolean eggChoise(String id, String monster) {
        String result = "";

        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/eggChoise",
                                        "id="+id+"&monster="+monster);

        try {
            result = networkTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Boolean.parseBoolean(result);
    }
}
