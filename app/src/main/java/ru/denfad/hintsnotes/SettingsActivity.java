package ru.denfad.hintsnotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import ru.denfad.hintsnotes.dao.hintDao;
import ru.denfad.hintsnotes.models.Hint;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;


public class SettingsActivity extends AppCompatActivity {

    public hintDao hintsDao = hintDao.getInstance();
    public Hint hint;
    public EditText editTitle;
    public EditText editTime;
    public EditText editHintText;
    public ImageButton addButton;
    public boolean newHint=false;
    public String savingListHint;
    final String themefile = "themefile";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        editTitle = findViewById(R.id.editTitle);
        editTime = findViewById(R.id.editTime);
        editHintText = findViewById(R.id.editHintText);
        final ConstraintLayout view = (ConstraintLayout) findViewById(R.id.settings_layout);

            try {
                // открываем поток для чтения
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        openFileInput(themefile)));
                String str = "";
                // читаем содержимое
                while ((str = br.readLine()) != null) {

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (themefile.toString()=="light") {
                view.setBackgroundResource(R.drawable.fon_zadn_svetl);
                editTitle.setTextColor(getResources().getColor(R.color.grey));
                editTime.setTextColor(getResources().getColor(R.color.grey));
                editHintText.setTextColor(getResources().getColor(R.color.grey));
            }
            else {
                view.setBackgroundResource(R.drawable.fon_zadn);
                editTitle.setTextColor(getResources().getColor(R.color.white));
                editTime.setTextColor(getResources().getColor(R.color.white));
                editHintText.setTextColor(getResources().getColor(R.color.white));
            }






        final Intent intent = this.getIntent();
        int position = intent.getIntExtra("hint",Integer.MAX_VALUE);
        savingListHint = intent.getStringExtra("savingListHint");
        if(hintsDao.getMapSize()>0 && position<hintsDao.getMapSize()){
            hint=hintsDao.getHint(position);
        }
        if(hint!=null) {
            editTitle.setText(hint.getTitle());
            editTime.setText(String.valueOf(Double.valueOf(hint.getMillisInFuture())/60000));
            editHintText.setText(hint.getText());
            newHint=false;
        }
        else{
            hint=new Hint();
            hint.setPosition(hintsDao.getMapSize());
            newHint=true;
        }
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (!TextUtils.isEmpty(editTitle.getText().toString()) && !TextUtils.isEmpty(editTime.getText().toString()) && !TextUtils.isEmpty(editHintText.getText().toString())) {

                    hint.setText(editHintText.getText().toString());
                    hint.setTitle(editTitle.getText().toString());
                    hint.setMillisInFuture(Long.valueOf((long) (Double.valueOf(editTime.getText().toString())*60000)));
                    if (newHint) {
                        hintsDao.addHint(hint);
                        Log.e("Add", hint.getTitle()+ " "+hint.getPosition());
                    }else{
                        Log.e("Update", hint.getTitle()+ " "+hint.getPosition());
                        hintsDao.updateHint(hint);
                    }

                    Intent intent1 = new Intent(getApplicationContext(), HintListActivity.class);
                    intent1.putExtra("savingListHint",savingListHint);
                    startActivity(intent1);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введите данные во все поля!",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(getApplicationContext(),HintListActivity.class);
        intent1.putExtra("savingListHint",savingListHint);
        startActivity(intent1);
    }
}