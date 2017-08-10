package com.wuzhanglao.highlightphraseview;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wuzhanglao.library.HighlightPhraseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String CONTENT = "Having a suite of unit and integration tests has many benefits.In most cases they are there to provide confidence that changes have not broken existing behaviour.Starting off with the less complex data classes was the clear choice for me. They are being used throughout the project, yet their complexity is comparatively low. This makes them an ideal starting point to set off the journey into a new language.After migrating some of these using the Kotlin code converter, which is built into Android Studio, executing tests and making them pass, I worked my way up until eventually ending up migrating the tests themselves to Kotlin.Without tests, I would have been required to go through the touched features after each change, and manually verify them.By having this automated it was a lot quicker and easier to move through the codebase, migrating code as I went along.So, if you don’t have your app tested properly yet, there’s one more reason to do so right here";

    private String[] mHighlightPhraseArray = new String[10];
    private List<String> mCheckedPhraseList = new ArrayList<>();
    private List<String> mHighlightPhraseList = new ArrayList<>();

    private HighlightPhraseView mHighlightPhraseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHighlightPhraseList.add("a suite of");
        mHighlightPhraseList.add("has many benefits");
        mHighlightPhraseList.add("In most cases");
        mHighlightPhraseList.add("have not broken existing behaviour");
        mHighlightPhraseList.add("Starting off with");
        mHighlightPhraseList.add("choice for");
        mHighlightPhraseList.add("throughout the project");
        mHighlightPhraseList.add("starting point to");
        mHighlightPhraseList.add("move through the codebase");
        mHighlightPhraseList.add("one more reason to do");

        mCheckedPhraseList.addAll(mHighlightPhraseList);

        for (int i = 0; i < mHighlightPhraseList.size(); i++) {
            mHighlightPhraseArray[i] = mHighlightPhraseList.get(i);
        }

        mHighlightPhraseView = (HighlightPhraseView) findViewById(R.id.content);
        mHighlightPhraseView.setDisplayedText(CONTENT, mCheckedPhraseList);

        findViewById(R.id.modify_text_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHighlightPhraseView.setDefaultColor(generateRandomColor());
            }
        });

        findViewById(R.id.modify_highlight_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHighlightPhraseView.setHighlightColor(generateRandomColor());
            }
        });

        findViewById(R.id.modify_highlight_phrase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhraseDialog();
            }
        });
    }

    private int generateRandomColor() {
        Random random = new Random();
        return Color.argb(random.nextInt() % 255, random.nextInt() % 255, random.nextInt() % 255, random.nextInt() % 255);
    }

    private void showSelectPhraseDialog() {

        boolean[] checkedItems = new boolean[10];
        for (int i = 0; i < mHighlightPhraseList.size(); i++) {
            if (mCheckedPhraseList.contains(mHighlightPhraseList.get(i))) {
                checkedItems[i] = true;
            } else {
                checkedItems[i] = false;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择需要高亮的词组");
        builder.setMultiChoiceItems(mHighlightPhraseArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    mCheckedPhraseList.add(mHighlightPhraseArray[which]);
                } else {
                    mCheckedPhraseList.remove(mHighlightPhraseArray[which]);
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mHighlightPhraseView.setDisplayedText(CONTENT, mCheckedPhraseList);
            }
        });
        builder.create().show();
    }
}
