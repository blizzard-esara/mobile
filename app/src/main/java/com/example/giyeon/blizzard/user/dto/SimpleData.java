package com.example.giyeon.blizzard.user.dto;

import android.util.Log;

import com.example.giyeon.blizzard.user.controller.NetworkController;

import java.util.List;
import java.util.Map;

public class SimpleData {

     //private String url = "http://10.0.2.2:8080"; localhost
     private String url = "http://13.112.214.29:8090/blizzard"; //AWS
     //private String imageUrl = "http://10.0.2.2/blizzard"; localhost
     private String imageUrl = "http://13.112.214.29/blizzard";
     public final long FINISH_INTERVAL_TIME = 2000;
     public long backPressedTime = 0;
     private int quizExp;


    private SimpleData() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuizExp() {
        return quizExp;
    }

    public void setQuizExp(int quizExp) {
        this.quizExp = quizExp;
    }

    public Map<String, Object> expReflection() {
        /** 경험치 서버로 전체 전송, 서버에서 응답이 오면 응답값을 클라이언트에게 보여줌
         *  결과창 : 어떤 몬스터의 레벨(몇), 경험치(몇), 몬스터의 알url,
         *
         *  서버로직 : 몬스터가 레벨이 올라간다 ==> 올린다 But 부화 할때(알 뽑기) 알 정보 제거, 콜렉션 추가**/
        Map<String, Object> map = NetworkController.getInstance().expReflection(quizExp);
        Log.e("exReflection Log", map.toString());
        return map;
    }


    private static class LazyHolder {
        public static final SimpleData INSTANCE = new SimpleData();
    }
    public static SimpleData getInstance() {
        return LazyHolder.INSTANCE;
    }

}
