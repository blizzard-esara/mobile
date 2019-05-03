package com.example.giyeon.blizzard.user.controller;

import android.util.Log;

import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.dto.SimpleData;
import com.example.giyeon.blizzard.user.dto.UserData;
import com.example.giyeon.blizzard.user.model.NetworkTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.HashMap;
import java.util.List;
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
                UserData.getInstance().setMoney(Integer.parseInt(jsonObject.get("money").toString()));
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

    public int initalCheck(String id) {
        String result = "";
        int returnInt = 0;

        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/initalCheck",
                            "id="+id);

            try {
                result = networkTask.execute().get();
                if(result.equals("[]")) {
                    returnInt = -1;
                } else {


                    jsonConvertToList(result);


                    JSONArray jsonArray = new JSONArray(result);
                    returnInt = jsonArray.length();

                    if(jsonArray.length() == 1) {

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        MonsterData.getInstance().setMainMonster(jsonObject.get("monster").toString());
                        MonsterData.getInstance().setExp(Integer.parseInt(jsonObject.get("exp").toString()));
                        MonsterData.getInstance().setLevel(Integer.parseInt(jsonObject.get("level").toString()));

                        returnInt = 1;
                    }
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }




        return returnInt;
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

    public String mainMonsterImageURL(String id, String mainMonster) {
        String result = "";
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/mainMonsterImageURL",
                "id="+id+"&mainMonster="+mainMonster);
        try {
            result = networkTask.execute().get();
            MonsterData.getInstance().setMonsterUrl(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void buyItem(String itemKind, int price) {
        String result = "";

        switch (itemKind) {
            case "스타크래프트 알":
                itemKind = "starCraft";
                break;
            case "오버워치 알":
                itemKind = "overWatch";
                break;
            case "디아블로 알":
                itemKind = "diablo";
                break;
        }
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/buyItem",
                "id="+UserData.getInstance().getId()+"&item="+itemKind+"&price="+price);
        try {
            result = networkTask.execute().get();

            jsonConvertToList(result);

            UserData.getInstance().setMoney(UserData.getInstance().getMoney() - price);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void jsonConvertToList(String result) {
        Gson gson = new Gson();
        List<Map<String, Object>> monsterList = gson.fromJson(result, new TypeToken<List<Map<String,Object>>>(){}.getType());
        MonsterData.getInstance().setMonsterList(monsterList);

        for(int i = 0 ; i < monsterList.size() ; i++) {
            monsterList.get(i).put("url",mainMonsterImageURL(
                    UserData.getInstance().getId(),
                    MonsterData.getInstance().getMonsterList().get(i).get("monster").toString()
            ));
        }

    }
}
