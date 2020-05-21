package ru.denfad.hintsnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedWriter;

import java.io.OutputStreamWriter;

import java.io.FileNotFoundException;

import java.io.IOException;


public class RLSettingsActivity extends AppCompatActivity {
    public View.OnClickListener radioButtonClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ConstraintLayout view = (ConstraintLayout) findViewById(R.id.lyt);
        setContentView(R.layout.rl_settings_activity);

        final String themefile = "themefile";
        final RadioButton dark = findViewById(R.id.radioButton);
        final RadioButton light = findViewById(R.id.radioButton3);
        final TextView theme = findViewById(R.id.theme);
        final TextView se = findViewById(R.id.textVie);
        final ImageButton bck = findViewById(R.id.backToLectures);

        dark.setOnClickListener(radioButtonClickListener);
        light.setOnClickListener(radioButtonClickListener);
        bck.setOnClickListener(bckClickListener);
        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;
                switch (rb.getId()) {
                    case R.id.radioButton:

                        view.setBackgroundResource(R.drawable.fon_zadn);
                        dark.setTextColor(getResources().getColor(R.color.grey));
                        light.setTextColor(getResources().getColor(R.color.grey));
                        theme.setTextColor(getResources().getColor(R.color.grey));
                        se.setTextColor(getResources().getColor(R.color.grey));
                        bck.setColorFilter(getResources().getColor(R.color.grey));

                        try {
                            // отрываем поток для записи
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                                    openFileOutput(themefile, MODE_PRIVATE)));
                            // пишем данные
                            bw.write("dark");
                            // закрываем поток
                            bw.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;


                    case R.id.radioButton3:
                        view.setBackgroundResource(R.drawable.fon_zadn_svetl);
                        dark.setTextColor(getResources().getColor(R.color.white));
                        light.setTextColor(getResources().getColor(R.color.white));
                        theme.setTextColor(getResources().getColor(R.color.white));
                        se.setTextColor(getResources().getColor(R.color.white));
                        bck.setColorFilter(getResources().getColor(R.color.white));
                        try {
                            // отрываем поток для записи
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                                    openFileOutput(themefile, MODE_PRIVATE)));
                            // пишем данные
                            bw.write("light");
                            // закрываем поток
                            bw.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;

                    default:
                        break;
                }

            }
        };
        View.OnClickListener bckClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), LecturesListActivity.class);
                startActivity(intent1);
            }

        };
    }
}