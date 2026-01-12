package com.example.a108222040_listview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageDialogFragment extends DialogFragment {
    static String [] strName = {"LBJ", "Lillard", "AD", "KT", "SC", "Lavine"};
    static int [] ipic = {R.drawable.b01, R.drawable.b02, R.drawable.b03,
            R.drawable.b04, R.drawable.b05, R.drawable.b06};
    static ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    static
    {
        for (int i = 0 ; i < strName.length ; i++)
        {
            HashMap <String, String> hashMap = new HashMap();
            hashMap.put("name", strName[i]);
            hashMap.put("pic", String.valueOf(ipic[i]));
            arrayList.add(hashMap);
        }//end for
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SimpleAdapter ap = new SimpleAdapter(getContext(), arrayList, R.layout.pic_text_layout,
                new String[]{"name","pic"},
                new int[]{R.id.layout_textView, R.id.LayoutPic});
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setAdapter(ap, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((CustomerActivityUpdate)getContext()).getSelected(which);
            }
        }).setNegativeButton("取消",null).setTitle("Pick one picture");
        return builder.create();
    }//end Dialog
}//end class ImageDialogFragment
