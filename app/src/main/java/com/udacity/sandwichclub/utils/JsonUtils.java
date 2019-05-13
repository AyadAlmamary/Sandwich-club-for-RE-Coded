package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
     try {


        JSONObject dataObject = new JSONObject(json);

        JSONObject items= dataObject.getJSONObject("name");

        String mainNames=items.getString("mainName");

        JSONArray alsoKnownArray =items.getJSONArray("alsoKnownAs");

         ArrayList<String> alsoKnown =new ArrayList<>();

         for(int i=0;i<alsoKnownArray.length();i++){
             alsoKnown.add(alsoKnownArray.getString(i));
         }
         String placeOfOrigin = dataObject.getString("placeOfOrigin");
         String description = dataObject.getString("description");
         String image = dataObject.getString("image");

         JSONArray ingredientsArray = dataObject.getJSONArray("ingredients");
         ArrayList<String> ingredients = new ArrayList<>();
         for(int i = 0; i < ingredientsArray.length(); i++) {
             ingredients.add(ingredientsArray.getString(i));
         }
         return new Sandwich(mainNames,alsoKnown,placeOfOrigin,description,image,ingredients);
    }catch(JSONException e){
         e.printStackTrace();
     }
     return null;
}

}
