package com.example.giyeon.blizzard.user.controller;

import android.util.Log;

import com.example.giyeon.blizzard.user.dto.EggData;
import com.example.giyeon.blizzard.user.dto.Explanation;
import com.example.giyeon.blizzard.user.dto.Quiz;
import com.example.giyeon.blizzard.user.dto.SimpleData;
import com.example.giyeon.blizzard.user.dto.UserData;
import com.example.giyeon.blizzard.user.model.NetworkTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NetworkController {


    private NetworkTask networkTask;

    private NetworkController() {

    }

    private static class LazyHolder {
        public static final NetworkController INSTANCE = new NetworkController();
    }
    public static NetworkController getInstance() {
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
                UserData.getInstance().setDate(jsonObject.get("date").toString());
                UserData.getInstance().setEmail(jsonObject.get("email").toString());
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


                    List<Map<String, Object>> monsterList = jsonConvertToList(result);
                    setUrl(monsterList);

                    JSONArray jsonArray = new JSONArray(result);
                    returnInt = jsonArray.length();

                    if(jsonArray.length() == 1) {

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        EggData.getInstance().setMainEgg(jsonObject.get("monster").toString());
                        EggData.getInstance().setMainExp(Integer.parseInt(jsonObject.get("exp").toString()));
                        EggData.getInstance().setMainLevel(Integer.parseInt(jsonObject.get("level").toString()));

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
            EggData.getInstance().setMainUrl(result);
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

;

            List<Map<String, Object>> monsterList = jsonConvertToList(result);
            setUrl(monsterList);


            UserData.getInstance().setMoney(UserData.getInstance().getMoney() - price);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> monsterCollection() {
        String result = "";
        List<Map<String, Object>> collectionList;
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/monsterCollection",
                                        "id="+UserData.getInstance().getId());
        try {
            result = networkTask.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        collectionList = jsonConvertToList(result);

        return collectionList;
    }

    public int monsterCount() {
        String result = "";
        double monsterAllCount = 0;
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/monsterCount",
                                        "id="+UserData.getInstance().getId());
        try {
            result = networkTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        List<Map<String, Object>> monsterC = jsonConvertToList(result);
        for(int i= 0 ; i < monsterC.size() ; i++) {
            //monsterC.get(i).get("monster_type"); a몬스터 종류별 개수뽑을떄 이름 가져오기

             monsterAllCount += Double.valueOf(monsterC.get(i).get("cnt").toString());
        }

        return (int)monsterAllCount;
    }

    public void modifyUser(String email,String phone) {

        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/modifyUser",
                "id="+UserData.getInstance().getId()+"&email="+email+"&phone="+phone);

        UserData.getInstance().setEmail(email);
        UserData.getInstance().setPhone(phone);
    }

    public List<Explanation> explanationList() {

        String result = "";
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl() + "/explanationList",
                "id=" + UserData.getInstance().getId());

        try {
            result = networkTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(result, new TypeToken<List<Explanation>>() {}.getType());
    }

    public List<Quiz> quizList(String theme, String level) {
        String result = "";
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/getQuizList",
                "id="+UserData.getInstance().getId()+"&theme="+theme+"&level="+level);
        try {
            result = networkTask.execute().get();
            Log.e("network",result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.equals("[]")) {
            return new ArrayList<Quiz>();
        }
        Gson gson = new Gson();
        return gson.fromJson(result, new TypeToken<List<Quiz>>() {}.getType());
    }

    public Explanation explanationShow(int col) {
        String result = "";
        networkTask = new NetworkTask(SimpleData.getInstance().getUrl()+"/explanationShow",
                                        "id="+UserData.getInstance().getId()+"&col="+col);

        try {
            result = networkTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(result, new TypeToken<Explanation>(){}.getType());
    }


    private List<Map<String, Object>> jsonConvertToList(String result) {
        Gson gson = new Gson();
        return gson.fromJson(result, new TypeToken<List<Map<String,Object>>>(){}.getType());
    }

    private void setUrl(List<Map<String, Object>> monsterList) {
        EggData.getInstance().setMonsterList(monsterList);

        for(int i = 0 ; i < monsterList.size() ; i++) {
            monsterList.get(i).put("url",SimpleData.getInstance().getImageUrl()+
                    mainMonsterImageURL(UserData.getInstance().getId(),
                            EggData.getInstance().getMonsterList().get(i).get("monster").toString()
                    ));
        }
    }
}
