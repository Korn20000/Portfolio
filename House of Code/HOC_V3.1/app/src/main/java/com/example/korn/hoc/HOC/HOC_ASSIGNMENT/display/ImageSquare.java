package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.display;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/*
 * Created by KoRn on 11-03-2018.
 *
 * The squares for the pictures will always remain same size in respect to their width
 *
 */

final class ImageSquare extends ImageView
{
    public ImageSquare(Context context)
    {
        super(context);
    }

    public ImageSquare(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
