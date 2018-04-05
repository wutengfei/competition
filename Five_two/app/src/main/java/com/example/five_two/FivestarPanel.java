package com.example.five_two;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 璐 on 2017/10/6.
 */

public class FivestarPanel extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece;     //白棋的图片
    private Bitmap mBlackPiece;     //黑棋的图片
    private Bitmap mGaryPiece;      //灰棋的图片
    private Bitmap mDPiece;      //灰棋的图片
    private float ratioPieceOFLineHeight = 1 * 1.0f / 5;

    int startI;//记录当前棋子的开始位置
    int endI;//记录当前棋子的目标位置
    //五角星棋
    int[] qizi = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 0};//棋盘棋子状态：1有棋，0无棋
    boolean focus = false;//当前是否有选中的棋子
    boolean IsGameover = false;//游戏是否结束
    int status = 0;//游戏状态。0游戏中，1为游戏结束
    int score;
    final MyUser myUser= new MyUser();
    final List<BmobObject> gameScores = new ArrayList<BmobObject>();

    public FivestarPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        // setBackgroundColor(0x44ff0000);
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
        mGaryPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_gray);//灰色
        mDPiece = BitmapFactory.decodeResource(getResources(), R.drawable.dizi);//灰色
    }

    protected void onMeasure(int widthM, int heightM) {
        int widthsize = MeasureSpec.getSize(widthM);
        int widthmode = MeasureSpec.getMode(widthM);

        int heightsize = MeasureSpec.getSize(heightM);
        int heightmode = MeasureSpec.getMode(heightM);

        int width = Math.min(widthsize, heightsize);

        if (widthmode == MeasureSpec.UNSPECIFIED) {
            width = heightsize;
        } else if (heightmode == MeasureSpec.UNSPECIFIED) {
            width = widthsize;
        }
        setMeasuredDimension(width, width);
    }

    //为了匹配棋子的大小
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / 2;

        int pieceWidth = (int) (mLineHeight * ratioPieceOFLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
        mGaryPiece = Bitmap.createScaledBitmap(mGaryPiece, pieceWidth, pieceWidth, false);
        mDPiece = Bitmap.createScaledBitmap(mDPiece, pieceWidth+20, pieceWidth+20, false);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorad(canvas);
    }


    private void drawBorad(Canvas canvas) {
        int width = getWidth();//宽度
        int r = width / 2;//r=552
        canvas.translate(r, r);//平移，将画布的坐标原点向左右方向移动x，向上下方向移动y.canvas的默认位置是在（0,0）.
        Path path = new Path();
        path.moveTo(0, -r + 250);//A
        path.lineTo(r * sin(36), r * cos(36) + 250);//C
        path.lineTo(-r * sin(72), -r * cos(72) + 250);//E
        path.lineTo(r * sin(72), -r * cos(72) + 250);//B
        path.lineTo(-r * sin(36), r * cos(36) + 250);//D
        path.close();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(243,179,89));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawPath(path, paint);
        canvas.drawBitmap(mDPiece, -65, -r + 190, paint);
        canvas.drawBitmap(mDPiece, r * sin(72) - 95, -r * cos(72) + 190, paint);
        canvas.drawBitmap(mDPiece, r * sin(36) - 50, r * cos(36) + 190, paint);
        canvas.drawBitmap(mDPiece, -r * sin(36) - 40, r * cos(36) + 180, paint);
        canvas.drawBitmap(mDPiece, -r * sin(72) - 25, -r * cos(72) + 190, paint);
        canvas.drawBitmap(mDPiece, r * cos(36) / 3 - 20, r * sin(36) / 3 + 150, paint);
        canvas.drawBitmap(mDPiece, r * cos(36 + 72) / 3 , r * sin(72 + 36) / 3 + 230, paint);
        canvas.drawBitmap(mDPiece, r * cos(36 + 144) / 3 - 80, r * sin(144 + 36) / 3 + 240, paint);
        canvas.drawBitmap(mDPiece, r * cos(36 + 72 * 3) / 3 - 120, r * sin(72 * 3 + 36) / 3 + 200, paint);
        canvas.drawBitmap(mDPiece, r * cos(36 + 72 * 4) / 3 - 80, r * sin(72 * 4 + 36) / 3 + 135, paint);
        //五个外点
        if (qizi[0] == 1)
            canvas.drawBitmap(mBlackPiece, -55, -r + 200, paint);
        if (qizi[1] == 1)
            canvas.drawBitmap(mBlackPiece, r * sin(72) - 85, -r * cos(72) + 200, paint);
        if (qizi[2] == 1)
            canvas.drawBitmap(mBlackPiece, r * sin(36) - 40, r * cos(36) + 200, paint);
        if (qizi[3] == 1)
            canvas.drawBitmap(mBlackPiece, -r * sin(36) - 30, r * cos(36) + 190, paint);
        if (qizi[4] == 1)
            canvas.drawBitmap(mBlackPiece, -r * sin(72) - 15, -r * cos(72) + 200, paint);
        //五个内点
        if (qizi[7] == 1)
            canvas.drawBitmap(mBlackPiece, r * cos(36) / 3 - 10, r * sin(36) / 3 + 160, paint);
        if (qizi[6] == 1)
            canvas.drawBitmap(mBlackPiece, r * cos(36 + 72) / 3 + 10, r * sin(72 + 36) / 3 + 240, paint);
        if (qizi[5] == 1)
            canvas.drawBitmap(mBlackPiece, r * cos(36 + 144) / 3 - 70, r * sin(144 + 36) / 3 + 250, paint);
        if (qizi[9] == 1)
            canvas.drawBitmap(mBlackPiece, r * cos(36 + 72 * 3) / 3 - 110, r * sin(72 * 3 + 36) / 3 + 210, paint);
        if (qizi[8] == 1)
            canvas.drawBitmap(mBlackPiece, r * cos(36 + 72 * 4) / 3 - 70, r * sin(72 * 4 + 36) / 3 + 145, paint);
        // 外点
        //        x=Rcos(72°*k)  y=Rsin(72°*k)   k=0,1,2,3,4
        // 内点(r*cos(72*i+36), r*sin(72*i+36)
        //         r=Rsin(18)/sin(180-36-18)
        // x=rcos(72°*k+36°)  y=rsin(72°*k+36°)   k=0,1,2,3,4
        // String s1 = "" + r;
        //String s2 = "" + y;
        // String s = s1 + " " + s2;
        // Toast.makeText(getContext(), s1 , Toast.LENGTH_SHORT).show();
        //五个外点

    }

    float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }

    float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }
/*

            * 然后再判断当然玩家是否已经有选中的棋子,如果没有则选中
			 * 如果之前有选中的棋子，再判断点击的位置是空地
			 * 是空地判断是否可走
 */

    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (checkGameOver() != -1) {
                int i = -1;
                int pos = getPos(event);
                i = pos;
                if (i == -1) {
                    Toast.makeText(getContext(), "请选择棋子点击", Toast.LENGTH_SHORT).show();
                } else {
                    if (focus == false) {//之前没有选中的棋子
                        if (qizi[i] == 1) {//点击的位置有棋子
                            if (!checkmove(qizi,i)) {
                                focus = false;
                                Toast.makeText(getContext(), "当前选中的棋子不可移动，请重新选择", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "已执棋", Toast.LENGTH_SHORT).show();
                                focus = true;//标记当前有选中的棋子
                                startI = i;
                            }

                    }
                } else {//之前选中过棋子
                        if(qizi[i] != 0){
                            Toast.makeText(getContext(), "您已举棋，不可换棋", Toast.LENGTH_SHORT).show();
                        }
                        if (qizi[i] == 0) {//点击的位置没有棋子
                            endI = i;
                            int mid = -1;
                            if (startI >= 0 && startI <= 9 && endI >= 0 && endI <= 9) {
                                mid = this.canMove(qizi, startI, endI);
                                if (mid != -1) {//如果可以移动过去
                                    Toast.makeText(getContext(), "挪棋成功", Toast.LENGTH_SHORT).show();
                                    qizi[startI] = 0;//将原来处设空
                                    qizi[endI] = 1;
                                    qizi[mid] = 0;
                                    startI = -1;
                                    endI = -1;//还原保存点
                                    focus = false;//标记当前没有选中棋子
                                }
                            }

                        }
                    }
                }
            }

        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    public boolean checkmove(int []qizi, int  moveChessID)
    {
        for (int i=0;i<10;i++)
        {
            if(canMove(qizi,moveChessID,i)!=-1)
                return true;
        }
        return false;
    }

    public int canMove(int []qizi, int  moveChessID,int targetID )
    {
        int mid = -1;
        switch(moveChessID)
        {
            case 0:
                switch(targetID)
                {
                    case 5: if (qizi[5]==0 && qizi[9]==1)
                        return 9;
                    case 7: if (qizi[7]==0 && qizi[8]==1)
                        return 8;
                    default:mid=-1;
                }
                break;
            case 1:
                switch(targetID)
                {
                    case 9: if (qizi[9]==0 && qizi[8]==1)
                        return 8;
                    case 6: if (qizi[6]==0 && qizi[7]==1)
                        return 7;
                    default:mid=-1;
                }
                break;
            case 2:
                switch(targetID)
                {
                    case 8: if (qizi[8]==0 && qizi[7]==1)
                        return 7;
                    case 5: if (qizi[5]==0 && qizi[6]==1)
                        return 6;
                    default:mid=-1;
                }
                break;
            case 3:
                switch(targetID)
                {
                    case 7: if (qizi[7]==0 && qizi[6]==1)
                        return 6;
                    case 9: if (qizi[9]==0 && qizi[5]==1)
                        return 5;
                    default:mid=-1;
                }
                break;
            case 4:
                switch(targetID)
                {
                    case 6: if (qizi[6]==0 && qizi[5]==1)
                        return 5;
                    case 8: if (qizi[8]==0 && qizi[9]==1)
                        return 9;
                    default:mid=-1;
                }
                break;
            case 5:
                switch(targetID)
                {
                    case 0: if (qizi[0]==0 && qizi[9]==1)
                        return 9;
                    case 2: if (qizi[2]==0 && qizi[6]==1)
                        return 6;
                    default:mid=-1;
                }
                break;
            case 6:
                switch(targetID)
                {
                    case 4: if (qizi[4]==0 && qizi[5]==1)
                        return 5;
                    case 1: if (qizi[1]==0 && qizi[7]==1)
                        return 7;
                    default:mid=-1;
                }
                break;
            case 7:
                switch(targetID)
                {
                    case 0: if (qizi[0]==0 && qizi[8]==1)
                        return 8;
                    case 3: if (qizi[3]==0 && qizi[6]==1)
                        return 6;
                    default:mid=-1;

                }
                break;
            case 8:
                switch(targetID)
                {
                    case 4: if (qizi[4]==0 && qizi[9]==1)
                        return 9;
                    case 2: if (qizi[2]==0 && qizi[7]==1)
                        return 7;
                    default:mid=-1;
                }
                break;
            case 9:
                switch(targetID)
                {
                    case 1: if (qizi[1]==0 && qizi[8]==1)
                        return 8;
                    case 3: if (qizi[3]==0 && qizi[5]==1)
                        return 5;
                    default:mid=-1;
                }
                break;
            default:mid=-1;
        }
        return mid;

    }

    public int checkGameOver() {
        int mid = -1;
        int num = Count();
        score = 9-num;
        //是否还有棋子可以挪动
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (qizi[i] == 1 && qizi[j] == 0)
                {
                    mid = this.canMove(qizi, i, j);
                    if (mid != -1) {
                        return mid;
                    }
                }
            }
        }
        if (mid == -1 && num == 1) {
            Toast.makeText(getContext(), "游戏胜利，继续下一关", Toast.LENGTH_SHORT).show();
            myUser.setValue("playscore",score);
            //Intent intent=getIntent();
            //String ob = getString("obj");
            String ob = (String) MyUser.getObjectByKey("objectId");
            myUser.update(ob, new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Toast.makeText(getContext(),"更新成功：" + myUser.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"更新失败" + myUser.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else  if (mid == -1 && num != 1)
        {
            Toast.makeText(getContext(), "游戏失败，再来一局", Toast.LENGTH_SHORT).show();
            Integer oldscore = (Integer) MyUser.getObjectByKey("playscore");
            if(oldscore<score) {
                myUser.setValue("playscore", score);
                //gameScores.add(gameScore);
            /*new BmobBatch().updateBatch(gameScores).doBatch(new QueryListListener<BatchResult>() {

                @Override
                public void done(List<BatchResult> o, BmobException e) {
                    if(e==null){
                        for(int i=0;i<o.size();i++){
                            BatchResult result = o.get(i);
                            BmobException ex =result.getError();
                            if(ex==null){
                                Toast.makeText(getContext(),"更新成功："+result.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(),"更新失败："+ex.getMessage()+","+ex.getErrorCode(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(getContext(), "游戏", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
                String ob = (String) MyUser.getObjectByKey("objectId");
                myUser.update(ob, new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getContext(), "更新成功：" + myUser.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "更新失败" + myUser.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        }
        return mid;
    }

    public int Count() {
        int number = 0;
        for (int i = 0; i < 10; i++)
            number += qizi[i];
        return number;
    }

    public int getPos(MotionEvent e)//将坐标换算成数组的维数
    {
        int pos = -1;
        int width = getWidth();//宽度
        int r = width / 2;//540
        double x = e.getX();//得到点击位置的x坐标
        double y = e.getY();//得到点击位置的y坐标
        double qix = mGaryPiece.getWidth();//108
        double qiy = mGaryPiece.getWidth();//108
//0-9对应的position
        if (x > -55 + r && x < 55 + qix + r && y > 200 && y < 200 + qiy)//(x:-55,163;y:-340,-220)
            pos = 0;
        if (x > r * sin(72) - 85 + r && x < r * sin(72) - 75 + qix + r && y > -r * cos(72) + 200 + r && y < -r * cos(72) + 200 + qiy + r)
            pos = 1;
        if (x > r * sin(36) - 40 + r && x < r * sin(36) - 40 + qix + r && y > r * cos(36) + 200 + r && y < r * cos(36) + 200 + qiy + r)
            pos = 2;
        if (x > -r * sin(36) - 30 + r && x < -r * sin(36) - 30 + qix + r && y > r * cos(36) + 190 + r && y < r * cos(36) + 190 + qiy + r)
            pos = 3;
        if (x > -r * sin(72) - 15 + r && x < -r * sin(72) - 15 + qix + r && y > -r * cos(72) + 200 + r && y < -r * cos(72) + 200 + qiy + r)
            pos = 4;
        if (x > r * cos(36 + 144) / 3 - 70 + r && x < r * cos(36 + 144) / 3 - 70 + qix + r && y > r * sin(144 + 36) / 3 + 250 + r && y < r * sin(144 + 36) / 3 + 250 + qiy + r)
            pos = 5;
        if (x > r * cos(36 + 72) / 3 + 10 + r && x < r * cos(36 + 72) / 3 + 10 + qix + r && y > r * sin(72 + 36) / 3 + 240 + r && y < r * sin(72 + 36) / 3 + 240 + qiy + r)
            pos = 6;
        if (x > r * cos(36) / 3 - 10 + r && x < r * cos(36) / 3 - 10 + qix + r && y > r * sin(36) / 3 + 160 + r && y < r * sin(36) / 3 + 160 + qiy + r)
            pos = 7;
        if (x > r * cos(36 + 72 * 4) / 3 - 70 + r && x < r * cos(36 + 72 * 4) / 3 - 70 + qix + r && y > r * sin(72 * 4 + 36) / 3 + 145 + r && y < r * sin(72 * 4 + 36) / 3 + 145 + qiy + r)
            pos = 8;
        if (x > r * cos(36 + 72 * 3) / 3 - 110 + r && x < r * cos(36 + 72 * 3) / 3 - 110 + qix + r && y > r * sin(72 * 3 + 36) / 3 + 210 + r && y < r * sin(72 * 3 + 36) / 3 + 210 + qiy + r)
            pos = 9;
        //String s1 = "" + pos;
        //String s2 = "" + y;
        //String s = s1 + " " + s2;
        //Toast.makeText(getContext(), s1 , Toast.LENGTH_SHORT).show();
        return pos;//将坐标返回
    }



    public void start()
    {
        qizi = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 0};//棋盘棋子状态：1有棋，0无棋
        focus = false;//当前是否有选中的棋子
        invalidate();
    }



}
