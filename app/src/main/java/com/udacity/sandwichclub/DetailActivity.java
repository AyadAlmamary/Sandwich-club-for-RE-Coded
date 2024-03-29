package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void showTexts(int showId,String text){

        TextView show= findViewById(showId);
        show.setText(text);

    }

    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        StringBuilder alsoKnown = new StringBuilder();
        if(alsoKnownList != null){
            for(int i = 0; i < alsoKnownList.size(); i++){
                alsoKnown.append(alsoKnownList.get(i));
                if(i != (alsoKnownList.size() - 1)){
                    alsoKnown.append(", ");
                }
            }
        }
        showTexts(R.id.also_known_tv, alsoKnown.toString());
        showTexts(R.id.origin_tv,sandwich.getPlaceOfOrigin());
        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder ingredientsName = new StringBuilder();
        for(int i = 0; i < ingredientsList.size(); i++){
            ingredientsName.append("- ");
            ingredientsName.append(ingredientsList.get(i));
            if(i != (ingredientsList.size() - 1)){
                ingredientsName.append("\n");
            }
        }
        showTexts(R.id.ingredients_tv,ingredientsName.toString());
        showTexts(R.id.description_tv,sandwich.getDescription());


    }
}
