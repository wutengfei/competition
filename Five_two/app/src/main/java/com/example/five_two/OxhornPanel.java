package com.example.five_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 璐 on 2017/10/6.
 */

public class OxhornPanel  extends View {
    private int mPanelWidth;
    private float mLineHeight;
    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece;     //白棋的图片
    private Bitmap mBlackPiece;     //黑棋的图片
    private float ratioPieceOFLineHeight = 1 * 1.0f / 5;
    //标记，是执黑子还是白子 ,白棋先走
    private boolean mIsWhite = true;
    boolean focus = false;//当前是否有选中的棋子
    private  int []qizi = {1,1,0,2,0,0,0,0,0,0,0};
    int []x;
    int startI;//记录当前棋子的开始位置
    int endI;//记录当前棋子的目标位置
    //赢家
    private boolean mIsWhiteWin = false;
    private boolean mIsBlackWin = false;
    public OxhornPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(0x44ff0000);
        init();
    }


    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_white);//白色
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_black);//黑色
    }

    protected void onMeasure(int widthM, int heightM) {
        int widthsize = View.MeasureSpec.getSize(widthM);
        int widthmode = View.MeasureSpec.getMode(widthM);

        int heightsize = View.MeasureSpec.getSize(heightM);
        int heightmode = View.MeasureSpec.getMode(heightM);

        int width = Math.min(widthsize, heightsize);

        if (widthmode == View.MeasureSpec.UNSPECIFIED) {
            width = heightsize;
        } else if (heightmode == View.MeasureSpec.UNSPECIFIED) {
            width = widthsize;
        }
        setMeasuredDimension(width, width);
    }

    //为了匹配棋子的大小
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 3.0f / 5;

        int pieceWidth = (int) (mLineHeight * ratioPieceOFLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorad(canvas);
    }

    private void drawBorad(Canvas canvas) {
        int width = getWidth();//宽度
        int r = width / 2;//r=552
        Path path = new Path();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(243,179,89));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        int px1 = 1250;
        int py1 = 730;
        int px2 = 750;
        int py2 = 650;
        x = new int[11];
        int []y  ;
        y = new int[11];
        y[10]=90;
        y[0]=1370;
        y[1]=1450;
        x[10] = 350;
        x[0] = 100;
        x[1] = 750;
        path.moveTo(x[10],y[10]);
        path.quadTo(px1,py1, x[1],y[1]);
        path.lineTo(x[0],y[0]);
        path.quadTo(px2,py2,x[10],y[10]);

        int height = mBlackPiece.getHeight();
        int xx = height /2;

        for (int i=1 ; i<5 ;i++)
        {
            y[2*i] = 1370 - 270*i;
        }
        for (int i=1 ; i<5 ;i++)
        {
            y[2*i + 1] = 1450 - 270*i;
        }

        x[2]=310;
        x[3]=880;
        x[4]=450;
        x[5]=915;
        x[6]=507;
        x[7]=855;
        x[8]=455;
        x[9]=675;

        canvas.drawLine(x[0],y[0],x[3],y[3],paint);
        canvas.drawLine(x[2],y[2],x[3],y[3],paint);
        canvas.drawLine(x[2],y[2],x[5],y[5],paint);
        canvas.drawLine(x[4],y[4],x[5],y[5],paint);
        canvas.drawLine(x[4],y[4],x[7],y[7],paint);
        canvas.drawLine(x[6],y[6],x[7],y[7],paint);
        canvas.drawLine(x[6],y[6],x[9],y[9],paint);
        canvas.drawLine(x[8],y[8],x[9],y[9],paint);
        canvas.drawPath(path, paint);

        for (int i=0;i<11;i++)
        {
            if (qizi[i]==1)
                canvas.drawBitmap(mBlackPiece, x[i]-xx,y[i]-xx, paint);
            if (qizi[i]==2)
                canvas.drawBitmap(mWhitePiece, x[i]-xx,y[i]-xx, paint);
        }

    }


    public boolean onTouchEvent(MotionEvent event) {
        mIsWhiteWin = checkWhiteWin();
        mIsBlackWin = checkBlackWin();
        if (!mIsWhiteWin && !mIsBlackWin) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int i = -1;
                int pos = getPos(event);
                i = pos;
                //  String ll = " "+ i;
                //  Toast.makeText(getContext(), ll, Toast.LENGTH_SHORT).show();
                if (i == -1)
                {
                    Toast.makeText(getContext(), "请选择棋子位置点击", Toast.LENGTH_SHORT).show();
                }
                if ( i!=-1 && mIsWhite) {
                    //    Toast.makeText(getContext(), "白棋走棋", Toast.LENGTH_SHORT).show();
                    if (focus == false) {
                        if (qizi[i]==2)
                        {
                            startI = i;
                            focus = true;//标记当前有选中的棋子
                        }
                    }
                    else {//之前选中过棋子
                        if (qizi[i] == 0)
                        {//点击的位置没有棋子
                            endI = i;
                            if (startI >= 0 && startI <= 11 && endI >= 0 && endI <= 11)
                            {
                                if (canMove(qizi, startI, endI)) {//如果可以移动过去
                                    qizi[startI] = 0;//将原来处设空
                                    qizi[endI] = 2;
                                    startI = -1;
                                    endI = -1;//还原保存点
                                    focus = false;
                                    //刷新
                                    invalidate();
                                    //改变值
                                    mIsWhite = !mIsWhite;}//标记当前没有选中棋子
                            }

                        }
                    }
                }
                if ( i!=-1 && !mIsWhite)   {
                    if (focus == false) {
                        //     Toast.makeText(getContext(), "黑棋走棋", Toast.LENGTH_SHORT).show();
                        if (qizi[i]==1)
                        {
                            startI = i;
                            focus = true;//标记当前有选中的棋子
                        }
                    }
                    else {//之前选中过棋子
                        if (qizi[i] == 0)
                        {//点击的位置没有棋子
                            endI = i;
                            if (startI >= 0 && startI <= 11 && endI >= 0 && endI <= 11)
                            {
                                if (canMove(qizi, startI, endI)) {//如果可以移动过去
                                    qizi[startI] = 0;//将原来处设空
                                    qizi[endI] = 1;
                                    startI = -1;
                                    endI = -1;//还原保存点
                                    focus = false;
                                    //刷新
                                    invalidate();
                                    //改变值
                                    mIsWhite = !mIsWhite;
                                }//标记当前没有选中棋子
                            }

                        }
                    }
                }
            }

        }
        return super.onTouchEvent(event);
    }

    private boolean checkWhiteWin( )
    {
        if (qizi[0]==2 || qizi[1]==2) {
            Toast.makeText(getContext(), "白棋胜利", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean checkBlackWin( )
    {
        if (qizi[10]==2 ){
            Toast.makeText(getContext(), "黑棋胜利", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean canMove(int []qizi, int  moveChessID,int targetID )
    {
        switch(moveChessID)
        {
            case 0:
                switch(targetID)
                {
                    case 1: if (qizi[1]==0)
                        return true;
                    case 2: if (qizi[2]==0)
                        return true;
                    case 3: if (qizi[3]==0)
                        return true;
                    default:
                        return false;
                }
            case 1:
                switch(targetID)
                {
                    case 0: if (qizi[0]==0)
                        return true;
                    case 3: if (qizi[3]==0)
                        return true;
                    default:
                        return false;
                }
            case 2:
                switch(targetID)
                {
                    case 0: if (qizi[0]==0)
                        return true;
                    case 3: if (qizi[3]==0)
                        return true;
                    case 4: if (qizi[4]==0)
                        return true;
                    case 5: if (qizi[5]==0)
                        return true;
                    default:
                        return false;
                }
            case 3:
                switch(targetID)
                {
                    case 0: if (qizi[0]==0)
                        return true;
                    case 1: if (qizi[1]==0)
                        return true;
                    case 2: if (qizi[2]==0)
                        return true;
                    case 5: if (qizi[5]==0)
                        return true;
                    default:
                        return false;
                }
            case 4:
                switch(targetID)
                {
                    case 2: if (qizi[2]==0)
                        return true;
                    case 5: if (qizi[5]==0)
                        return true;
                    case 7: if (qizi[7]==0)
                        return true;
                    case 6: if (qizi[6]==0)
                        return true;
                    default:
                        return false;
                }
            case 5:
                switch(targetID)
                {
                    case 2: if (qizi[2]==0)
                        return true;
                    case 3: if (qizi[3]==0)
                        return true;
                    case 4: if (qizi[4]==0)
                        return true;
                    case 7: if (qizi[7]==0)
                        return true;
                    default:
                        return false;
                }
            case 6:
                switch(targetID)
                {
                    case 4: if (qizi[4]==0)
                        return true;
                    case 7: if (qizi[7]==0)
                        return true;
                    case 8: if (qizi[8]==0)
                        return true;
                    case 9: if (qizi[9]==0)
                        return true;
                    default:
                        return false;
                }
            case 7:
                switch(targetID)
                {
                    case 6: if (qizi[6]==0)
                        return true;
                    case 9: if (qizi[9]==0)
                        return true;
                    case 4: if (qizi[4]==0)
                        return true;
                    case 5: if (qizi[5]==0)
                        return true;
                    default:
                        return false;

                }
            case 8:
                switch(targetID)
                {
                    case 6: if (qizi[6]==0)
                        return true;
                    case 9: if (qizi[9]==0)
                        return true;
                    case 10: if (qizi[10]==0)
                        return true;
                    default:
                        return false;
                }
            case 9:
                switch(targetID)
                {
                    case 6: if (qizi[6]==0)
                        return true;
                    case 7: if (qizi[7]==0)
                        return true;
                    case 8: if (qizi[8]==0)
                        return true;
                    case 10: if (qizi[10]==0)
                        return true;
                    default:
                        return false;
                }
            case 10:
                switch(targetID)
                {
                    case 8: if (qizi[8]==0)
                        return true;
                    case 9: if (qizi[9]==0)
                        return true;
                    default:
                        return false;
                }
            default:return false;
        }

    }

    public int getPos(MotionEvent e)//将坐标换算成数组的维数
    {
        int pos = -1;
        int width = getWidth();//宽度
        int r = width / 2;//540
        double xsit = e.getX();//得到点击位置的x坐标
        double ysit = e.getY();//得到点击位置的y坐标

//0-10对应的position
        x = new int[11];
        int []y  ;
        y = new int[11];
        y[10]=70;
        y[0]=1370;
        y[1]=1450;
        x[10] = 350;
        x[0] = 100;
        x[1] = 750;
        for (int i=1 ; i<5 ;i++)
        {
            y[2*i] = 1370 - 280*i;
        }
        for (int i=1 ; i<5 ;i++)
        {
            y[2*i + 1] = 1450 - 280*i;
        }
        x[2]=310;
        x[3]=880;
        x[4]=450;
        x[5]=915;
        x[6]=507;
        x[7]=855;
        x[8]=455;
        x[9]=675;
        int height = mBlackPiece.getHeight();
        int xx = height /2;
        for (int i=0;i<11;i++)
        {
            if  (xsit > x[i]-xx && xsit < x[i]+xx && ysit > y[i]-xx && ysit < y[i]+xx)
                pos = i;
        }
        return pos;//将坐标返回
    }

    public void start()
    {
        qizi = new int[]{1,1,0,2,0,0,0,0,0,0,0};//棋盘棋子状态：1有棋，0无棋
        focus = false;//当前是否有选中的棋子
        mIsWhite = true;
        mIsWhiteWin = false;
        mIsBlackWin = false;
        invalidate();
        Toast.makeText(getContext(), "新局开始", Toast.LENGTH_SHORT).show();
    }


}
