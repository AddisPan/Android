package com.example.a10822040_readwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectActivity extends AppCompatActivity {
    ArrayList<HashMap<String,String>> aryData = new ArrayList<>();
    ListView lvobject;
    String filename = "fileObject.ser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);
        lvobject = (ListView) findViewById(R.id.lvobject);
//        InitialData();
//        fnWriteObjectToFile(aryData, filename);
        aryData = fnReadObjectFromFile(filename);
        SimpleAdapter ap = new SimpleAdapter(this, aryData, R.layout.customerlayout,
                new String[]{"Data", "Description","pic"},
                new int[]{R.id.cuslayout_name, R.id.cuslayout_price,
                R.id.cuslayout_iv});
        lvobject.setAdapter(ap);
    }//end onCreate

    private ArrayList<HashMap<String,String>> fnReadObjectFromFile(String _filename){
        ArrayList<HashMap<String,String>> al = new ArrayList<>();
        ReadSequentialFile rsf = new ReadSequentialFile(getApplicationContext(), _filename);
        try {
            al = (ArrayList<HashMap<String,String>>)rsf.getRSF();
            Toast.makeText(getApplicationContext(), "Read Success",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return al;
    }

    private void fnWriteObjectToFile(ArrayList<HashMap<String,String>> _aryData,
                                    String _filename){
        WriteSequentialFile wsf = new WriteSequentialFile(getApplicationContext(), _filename);
        try {
            wsf.fnWSF(_aryData);
            Toast.makeText(getApplicationContext(), "Write Success",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void InitialData() {
        String[] sData = {"元智", "明道", "世界", "高科", "台東", "成功"};
        String[] sDataDescription = {"50", "40", "20", "20", "20", "25"};
        int[] iPic = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo3
                , R.drawable.logo4, R.drawable.logo5, R.drawable.logo6};

        for(int i = 0 ; i<sData.length ; i++){
            HashMap<String,String> hashMap= new HashMap<>();
            hashMap.put("Data", sData[i]);
            hashMap.put("Description", sDataDescription[i]);
            hashMap.put("pic", String.valueOf(iPic[i]));
            aryData.add(hashMap);
        }
    }//end InitialData


}//end class