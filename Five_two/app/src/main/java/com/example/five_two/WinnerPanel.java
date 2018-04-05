package com.example.five_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by 璐 on 2017/10/10.
 */

public class WinnerPanel extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece;     //白棋的图片
    private Bitmap mBlackPiece;     //黑棋的图片
    private float ratioPieceOFLineHeight = 1 * 1.0f / 5;
    private List<Point> mWhiteArray = new ArrayList<>();
    private List<Point> mBlackArray = new ArrayList<>();
    private boolean mIsWhite = true;
    //int []x;
    //int []y;
    int s=0;


    //int width;
    //int  height;

    public WinnerPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        //setBackgroundColor(0x44ff0000);
        init();

    }

    private void init() {

        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_white);//白色
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_black);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

        int heightSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(widthMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }

        setMeasuredDimension(width, width);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = mPanelWidth * 3.0f /5;
        int pieceWidth = (int) (mLineHeight * ratioPieceOFLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {

                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    //封装成一个Point
                    Point p = getValidPoint(x, y);

                    //判断当前这个点是否有棋子了
                    if ((mWhiteArray.contains(p) && mWhiteArray.size()>3 )|| (mBlackArray.contains(p)&&mBlackArray.size()>3)) {

                        //点击不生效
                        return false;
                    }

                    //判断如果是白子就存白棋集合，反之则黑棋集合
                    if (mIsWhite) {
                        mWhiteArray.add(p);
                    } else {
                        mBlackArray.add(p);
                    }

                    //刷新
                    invalidate();

                    //改变值
                    mIsWhite = !mIsWhite;


        }


            return true;
        
    }

    private Point getValidPoint(int x, int y) {

        int[] xp = new int[9];
        int[] yp = new int[9];
        s = getHeight();
        xp[0] = 0;
        yp[0] = 0;
        xp[1] = 0;
        yp[1] = 29 * s / 40;
        xp[2] = 29 * s / 40;
        yp[2] = 0;
        xp[3] = 29 * s / 40;
        yp[3] = 29 * s / 40;
        xp[4] = 29 * s / 80;
        yp[4] = 29 * s / 80;
        xp[5] = 29 * s / 160;
        yp[5] = 29 * s / 80;
        xp[6] = 87 * s / 160;
        yp[6] = 29 * s / 80;
        xp[7] = 29 * s / 80;
        yp[7] = 29 * s / 160;
        xp[8] = 29 * s / 80;
        yp[8] = 87 * s / 160;
        for (int i = 0; i < 9; i++) {
            if (x > (xp[i] - 9/s) && x < (xp[i] + 9/s )&& y > (yp[i] - 9/s) && y <( yp[i] +9/s)) {
                x = i;
                y = i;
            }
        }

        return new Point( x,  y);
    }


    /*private int getPos(MotionEvent event) {
        int r = getWidth();//宽度
        int s = getHeight();//高度
        int pos = -1;
        x1 = (int) event.getX();
        y1 = (int) event.getY();//得到点击位置的y坐标
        int heightpiece = mBlackPiece.getHeight();
        int i;
        int[] x = new int[9];
        int[] y = new int[9];
        x[0] = 0;
        y[0] = 0;
        x[1] = 0;
        y[1] = 29 * s / 40;
        x[2] = 29 * s / 40;
        y[2] = 0;
        x[3] = 29 * s / 40;
        y[3] = 29 * s / 40;
        x[4] = 29 * s / 80;
        y[4] = 29 * s / 80;
        x[5] = 29 * s / 160;
        y[5] = 29 * s / 80;
        x[6] = 87 * s / 160;
        y[6] = 29 * s / 80;
        x[7] = 29 * s / 80;
        y[7] = 29 * s / 160;
        x[8] = 29 * s / 80;
        y[8] = 87 * s / 160;
        //z[0] = z[1] = z[2] = z[3] = z[4] = z[5] = z[6] = z[7] = z[8] = -1;
        for (i = 0; i < 9; i++) {
            if (x1 > x[i] - s/8 && x1 < x[i] + s/8 && y1 > y[i] - s/8 && y1 < y[i] + s/8) {
                pos = i;
                //z[i]=0;
            }

        }
            return pos;



    }*/


    private void drawPieces(Canvas canvas) {
        int heightpiec = mBlackPiece.getHeight();
        int xx=heightpiec/2;
        int [] xp = new int[9];
        int [] yp = new int[9];
        int iw;
        int jw;
        int ib;
        int jb;
        xp[0] = 0;
        yp[0] = 0;
        xp[1] = 0;
        yp[1] = 29 * s / 40;
        xp[2] = 29 * s / 40;
        yp[2] = 0;
        xp[3] = 29 * s / 40;
        yp[3] = 29 * s / 40;
        xp[4] = 29 * s / 80;
        yp[4] = 29 * s / 80;
        xp[5] = 29 * s / 160;
        yp[5] = 29 * s / 80;
        xp[6] = 87 * s / 160;
        yp[6] = 29 * s / 80;
        xp[7] = 29 * s / 80;
        yp[7] = 29 * s / 160;
        xp[8] = 29 * s / 80;
        yp[8] = 87 * s / 160;

        for (int i = 0; i < mWhiteArray.size(); i++) {
            //获取白棋子的坐标
            Point whitePoint = mWhiteArray.get(i);
            iw=whitePoint.x;
            jw=whitePoint.y;
            canvas.drawBitmap(mWhitePiece,xp[iw] - xx ,yp[jw] - xx , null);
        }

        for (int i = 0; i < mBlackArray.size(); i++) {
            //获取黑棋子的坐标
            Point blackPoint = mBlackArray.get(i);
            ib=blackPoint.x;
            jb=blackPoint.y;
            canvas.drawBitmap(mWhitePiece,xp[ib] - xx ,yp[jb] - xx , null);
        }



    }

    private void drawBoard(Canvas canvas) {//画棋盘

        int width = getWidth();//宽度
        int  r = width;
        int height = getHeight();
        s = height;
        canvas.translate(r/20, s/9);
        Path path = new Path();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(243,179,89));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        int [] x = new int[9];
        int[] y = new int[9];
        x[0] = 0;
        y[0] = 0;
        x[1] = 0;
        y[1] = 29*s/40;
        x[2] = 29*s/40;
        y[2] = 0;
        x[3] = 29*s/40;
        y[3] = 29*s/40;
        x[4] = 29*s/80;
        y[4] = 29*s/80;
        x[5] = 29*s/160;
        y[5] = 29*s/80;
        x[6] = 87*s/160;
        y[6] = 29*s/80;
        x[7] = 29*s/80;
        y[7] = 29*s/160;
        x[8] = 29*s/80;
        y[8] = 87*s/160;
        canvas.drawLine(x[0],y[0],x[1],y[1],paint);
        canvas.drawLine(x[0],y[0],x[2],y[2],paint);
        canvas.drawLine(x[2],y[2],x[3],y[3],paint);
        canvas.drawLine(x[1],y[1],x[3],y[3],paint);
        canvas.drawLine(x[0],y[0],x[5],y[5],paint);
        canvas.drawLine(x[0],y[0],x[7],y[7],paint);
        canvas.drawLine(x[2],y[2],x[6],y[6],paint);
        canvas.drawLine(x[2],y[2],x[7],y[7],paint);
        canvas.drawLine(x[1],y[1],x[5],y[5],paint);
        canvas.drawLine(x[1],y[1],x[8],y[8],paint);
        canvas.drawLine(x[3],y[3],x[6],y[6],paint);
        canvas.drawLine(x[3],y[3],x[8],y[8],paint);
        canvas.drawLine(x[5],y[5],x[6],y[6],paint);
        canvas.drawLine(x[7],y[7],x[8],y[8],paint);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);


    }

}
