package com.example.speeachap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageButton speeachstart;
    TextView result;
    SpeechRecognizer recognizer;
    Intent intent;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speeachstart = (ImageButton) findViewById(R.id.SpeeachStart);
        result = (TextView) findViewById(R.id.Result);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 5);
            Toast.makeText(getApplicationContext(), "음성인식 권한을 허용해주세요", Toast.LENGTH_SHORT).show();

        }

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);

        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                //사용자가 말하기 시작할 때 호출
                Toast.makeText(getApplicationContext(),"음성인식을 시작합니다",Toast.LENGTH_SHORT).show();
                speeachstart.setImageResource(R.drawable.ic_mic_black_24dp);
                databaseReference.getDatabase().getReference();
                databaseReference.getDatabase().getReference().removeValue();


            }

            @Override
            public void onBeginningOfSpeech() {


            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                //음성인식 종료시
                Toast.makeText(getApplicationContext(),"음성인식 종료",Toast.LENGTH_SHORT).show();
                speeachstart.setImageResource(R.drawable.ic_mic_none_black_24dp);

            }

            @Override
            public void onError(int error) {
              //  recognizer.destroy();
            }

            @Override
            public void onResults(Bundle results) {
                String arry =" ";
                arry = SpeechRecognizer.RESULTS_RECOGNITION;
                ArrayList<String> mResult = results.getStringArrayList(arry);
                String[] rs = new String[mResult.size()];
                mResult.toArray(rs);

                result.setText(" "+rs[0]);
               // recognizer.destroy();

              //  TextDB textdb= new TextDB(" "+rs[0]);
                databaseReference.push().setValue(rs[0]);





            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        speeachstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.startListening(intent);
            }
        });


    }
}
