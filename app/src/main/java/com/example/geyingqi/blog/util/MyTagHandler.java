package com.example.geyingqi.blog.util;

import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ProgressBar;

import org.xml.sax.XMLReader;

/**
 * Created by geyingqi on 12/15/15.
 */
public class MyTagHandler implements Html.TagHandler {

    boolean first = true;
    String parent = "ul";
    int index = 1;
    private int sIndex = 0;
    private int eIndex = 0;
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.equals("ul")) parent = "ul";
        else if (tag.equals("ol")) parent = "ol";
        if (tag.equals("li")){
            if (parent.equals("ul")){
                if (first) {
                    output.append("\n• ");
                    first = false;
                } else {
                    first = true;
                }
            }
        }


        if (tag.equals("blod")){
            System.out.println("tag"+tag);
            if (opening){
                sIndex = output.length();
                System.out.println("sIndex"+sIndex);
            } else {
                eIndex = output.length();
                System.out.println("eIndex"+eIndex);
                output.setSpan(new ForegroundColorSpan(Color.BLACK),sIndex,eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }


    }
}
