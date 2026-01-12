package com.example.a108222040_android_linear_regression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    Interpreter tflite;
    EditText et1,et2,et3;
    Button btnSure;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (Exception e){

        }
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        tv1 = (TextView) findViewById(R.id.tv1);
        btnSure = (Button) findViewById(R.id.bt);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long[][] inputvalue = new long[1][6];
                inputvalue[0][0] = Long.valueOf(et1.getText().toString());
                inputvalue[0][1] = Long.valueOf(et2.getText().toString());
                inputvalue[0][2] = Long.valueOf(et3.getText().toString());
                inputvalue[0][3] = inputvalue[0][0] * inputvalue[0][0];
                inputvalue[0][4] = inputvalue[0][1] * inputvalue[0][1];
                inputvalue[0][5] = inputvalue[0][2] * inputvalue[0][2];
                float[][] output = new float[1][1];
                tflite.run(inputvalue, output);
                tv1.setText("預估值為:" + output[0][0]);
            }
        });
    } //end onCreate

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor=this.getAssets().openFd("model.tflite");
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset=fileDescriptor.getStartOffset();
        long declareLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declareLength);
    }//end  MappedByteBuffer

}//end class