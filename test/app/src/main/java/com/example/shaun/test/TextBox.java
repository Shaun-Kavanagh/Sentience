package com.example.shaun.test;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class TextBox extends AppCompatActivity {
    TextView myTextView;

    //create global array
    public static  int[] array=new int[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_box);

        final Button Next= (Button) findViewById(R.id.buttonNext);
        final Button Save= (Button)findViewById(R.id.buttonSave);
        final Button Fav= (Button)findViewById(R.id.buttonFav);

        //set a font
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Chromia Bold Italic.otf");
        myTextView = (TextView) findViewById(R.id.textView1);
        myTextView.setTypeface(myTypeFace);


        String Q= GetQuestion();
        //reset text size if over 30 chars
        if(Q.length()>43) {

            myTextView.setTextSize(30.0f);
        }
        else{
             myTextView.setTextSize(40.0f);
        }
        //sets question showing
        myTextView.setText(Q);

        Next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Temp= GetQuestion();
                myTextView.setText(Temp);
                if(Temp.length()>41&& Temp.length()<60) {

                    myTextView.setTextSize(34.0f);
                }
                else if(Temp.length()>41){
                    myTextView.setTextSize(28.0f);
                }
                else{
                    myTextView.setTextSize(40.0f);
                }
                Save.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetSaved(Temp);
                    }
                });

            }
        });

        Fav.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
               String fav= GetFav();
                myTextView.setText(fav);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String GetQuestion() {
        try { String Question="";
            AssetManager asset= this.getAssets();
            InputStream stream= asset.open("QuestionFile");
            InputStreamReader readStream= new  InputStreamReader(stream);
            BufferedReader br = new BufferedReader(readStream);
            int Rand= (int) (Math.random() *68);

            for(int x=0; x<Rand;x++)
            br.readLine();

          Question = br.readLine();
           //Question = Integer.toString(Rand);
            int num = Rand;
            boolean check= CheckNum(num);
            if(check){
                //puts line number in array

                PutInArray(num);
               System.out.print (array[0]);
                return Question;
            }
            else{
                //recursive so that aline from the array cannot be called
                 return GetQuestion();
            }

        }

        catch ( Exception e ) {
            e.printStackTrace();
            String Question="IO error";
            return Question;
        }

    }
    public void SetSaved(String Save) {
        InputStream stream2 = null;
        //opened written file
        try {
            final AssetManager asset = this.getAssets();
            stream2 = asset.open("SavedQuestions");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("cant find file");
        }
        try {
            String entered = Save;
            FileOutputStream outputStream = openFileOutput("SavedQuestions", Context.MODE_APPEND);
            outputStream.write(entered.getBytes());
            System.getProperty("line.saparator");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String GetFav(){
        try { String Question="";
            AssetManager asset=this.getAssets();
            InputStream stream= asset.open("SavedQuestions");
            InputStreamReader readStream= new  InputStreamReader(stream);
            BufferedReader br = new BufferedReader(readStream);

           int Rand= (int) (Math.random() * 2);
            for(int i=0;i<Rand;i++){
            br.readLine();}
            Question=br.readLine();
            return Question;
        }

        catch ( Exception e ) {
            e.printStackTrace();
            String Question="IO error";
            return Question;
        }

    }
    public static void  PutInArray(int num)
    {
        //sort array
        int temp=0;
        for(int i=0; i<array.length;i++){
            for(int j=1; j<array.length-1;j++){
                if(array[j]>array[j-1]){
                    temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
        for(int i=0;i<array.length;i++){
        if(array[i]==0) {
            array[i] = num;
        }
    }}
    public boolean CheckNum(int num){
        for(int i=0;i<array.length;i++){
            if(array[i] != num){
                return true;
            }
        }
            return false;
    }
}