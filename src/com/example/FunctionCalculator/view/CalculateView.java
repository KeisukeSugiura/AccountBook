package com.example.FunctionCalculator.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import com.example.FunctionCalculator.activity.CalculateFragment;

/**
 * Created by yoshida keisuke on 2015/02/23.
 */
public class CalculateView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    /*
    メンバ
     */
    public Activity mActivity;
    private SurfaceHolder mHolder;
    private boolean isAttached;
    private Thread mThread;
    public static int sWindowWidth;
    public static int sWindowHeight;
    private RectF mNumberButtonRect;
    private RectF mCenterButtonRect;
    private RectF mActionButtonRect;
    public Rect mControlPanelRect;
    private Point mCenterOfCircle;
    private double mShiftRadius;
    private double mMinNumRadius;
    private double mMaxNumRadius;
    private double mMinActRadius;
    private double mMaxActRadius;
    public float mOnTouchStartX;
    public float mOnTouchStartY;
    public double mOnTouchRadius;
    public boolean mModeShift;

    public CalculateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO 自動生成されたコンストラクター・スタブ


        initStatus(context);
    }

    public CalculateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO 自動生成されたコンストラクター・スタブ

        initStatus(context);

    }

    public CalculateView(Context context) {
        super(context);
        // TODO 自動生成されたコンストラクター・スタブ

        initStatus(context);
    }

    /**
     * Runnableインターフェース
     */
    @Override
    public void run() {

    }

    /**
     * SurfaceHolder.Callbackインターフェース
     *
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setBackgroundColor(Color.WHITE);
        drawInitialView();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // drawInitialView();
    }

    /**
     * タッチイベント
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int act = event.getAction();
        float x = event.getX();
        float y = event.getY();
        int positionR;
        int positionD;
        switch (act) {
            case MotionEvent.ACTION_DOWN:
                mOnTouchStartX = x;
                mOnTouchStartY = y;
                mOnTouchRadius = Math.sqrt((x - mCenterOfCircle.x) * (x - mCenterOfCircle.x) + (y - mCenterOfCircle.y) * (y - mCenterOfCircle.y));

                // Log.d("position", String.valueOf(isNumItemPositionA(x, y, mOnTouchRadius)));
                if (mOnTouchRadius <= mShiftRadius) {
                    mModeShift = true;
                } else {
                    mModeShift = false;
                }
                positionR = isPositionR(mOnTouchRadius);
                switch (positionR) {
                    case 0:
                        positionD = isNumItemPositionD(x, y, mOnTouchRadius);
                        drawOnActiveEvent(positionR, positionD);
                        break;
                    case 1:
                        positionD = isNumItemPositionD(x, y, mOnTouchRadius);
                        //選択draw
                        drawOnActiveEvent(positionR, positionD);
                        break;
                    case 2:
                        positionD = isActItemPositionD(x, y, mOnTouchRadius);
                        //選択draw
                        drawOnActiveEvent(positionR, positionD);

                        break;
                    case 3:
                        break;

                }


                break;
            case MotionEvent.ACTION_MOVE:
                mOnTouchRadius = Math.sqrt((x - mCenterOfCircle.x) * (x - mCenterOfCircle.x) + (y - mCenterOfCircle.y) * (y - mCenterOfCircle.y));

                positionR = isPositionR(mOnTouchRadius);
                positionD = 10;
                switch (positionR) {
                    case 0:
                    case 1:
                        positionD = isNumItemPositionD(x, y, mOnTouchRadius);
                        //選択draw
                        drawOnActiveEvent(positionR, positionD);
                        break;
                    case 2:
                        positionD = isActItemPositionD(x, y, mOnTouchRadius);
                        //選択draw
                        drawOnActiveEvent(positionR, positionD);

                        break;
                    case 3:
                        drawOnActiveEvent(positionR,10);
                        break;

                }


                break;

            case MotionEvent.ACTION_UP:

                mOnTouchRadius = Math.sqrt((x - mCenterOfCircle.x) * (x - mCenterOfCircle.x) + (y - mCenterOfCircle.y) * (y - mCenterOfCircle.y));

                positionR = isPositionR(mOnTouchRadius);
                if (!mModeShift) {
                    switch (positionR) {
                        case 0:

                            break;
                        case 1:
                            //リスナーにアイテム番号を渡す
                            positionD=isNumItemPositionD(x,y,mOnTouchRadius);
                            onClickNumber(positionD);

                            break;
                        case 2:
                            //リスナーにアイテム番号を渡す
                            positionD=isActItemPositionD(x, y, mOnTouchRadius);
                            onClickAction(positionD);
                            break;
                    }
                } else {
                    switch (positionR) {
                        case 0:
                            //イコール処理
                            onClickEqual();
                            break;
                        case 1:
                            //shiftNumのリスナー
                            positionD=isNumItemPositionD(x,y,mOnTouchRadius);
                            onClickShiftNumber(positionD);
                            break;
                        case 2:
                            break;
                    }

                }

                mModeShift = false;
                drawInitialView();
                break;

        }

        return true;
    }

    //メソッド

    public void initStatus(Context context) {
        mActivity = (Activity) context;
        mModeShift = false;

        //ウィンドウサイズの取得
        Window window = mActivity.getWindow();
        // WindowManager manager = (WindowManager)mActivity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager manager = window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        sWindowWidth = size.x;
        sWindowHeight = size.y;
        determineComponentSize();

        //Log.d("size",sWindowHeight+":"+sWindowWidth);

        //サーフェースビューホルダー
        mHolder = getHolder();
        mHolder.addCallback(this);

        //ビューの設定
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        setFocusable(true);
        setZOrderOnTop(true);
        requestFocus();
    }

    public Rect getmControlPanelRect() {
        return mControlPanelRect;
    }

    private void determineComponentSize() {
        mCenterOfCircle = new Point(sWindowWidth / 2, sWindowHeight - sWindowWidth / 2);

        mNumberButtonRect =
                new RectF((float) (sWindowWidth / 6f), (float) (sWindowHeight - sWindowWidth * 5 / 6f), (float) (sWindowWidth * 5 / 6), (float) (sWindowHeight - sWindowWidth / 6));
        mCenterButtonRect = new RectF(mNumberButtonRect.left + sWindowWidth / 4, mNumberButtonRect.top + sWindowWidth / 4, mNumberButtonRect.right - sWindowWidth / 4, mNumberButtonRect.bottom - sWindowWidth / 4);

        mActionButtonRect = new RectF(-sWindowWidth / 12, sWindowHeight - sWindowWidth * 13 / 12, sWindowWidth * 13 / 12, sWindowHeight + sWindowWidth / 12);

        mControlPanelRect = new Rect(0, 0, sWindowWidth, sWindowHeight - sWindowWidth * 5 / 4);

        mShiftRadius = sWindowWidth / 6;
        mMinNumRadius = sWindowWidth * 3 / 12;
        mMaxNumRadius = sWindowWidth * 5 / 12;
        mMinActRadius = sWindowWidth / 2;
        mMaxActRadius = sWindowWidth * 2 / 3;

    }

    private int isPositionR(double radius) {
        int posi = 3;
        if (radius <= mShiftRadius) {
            posi = 0;
        } else if (mMinNumRadius <= radius && radius <= mMaxNumRadius) {
            posi = 1;
        } else if (mMinActRadius <= radius && radius <= mMaxActRadius) {
            posi = 2;
        } else {
            posi = 3;
        }
        return posi;

    }

    private int isNumItemPositionD(float x, float y, double radius) {
        int posi = 10;
        double thetax = 0.0;
        double thetay = 0.0;
        double fx = x - mCenterOfCircle.x;
        double fy = y - mCenterOfCircle.y;
        thetax = Math.toDegrees(Math.acos((fx / radius)));
        thetay = Math.toDegrees(Math.asin((fy / radius)));
        //  Log.d("posiX", String.valueOf(thetax));
        // Log.d("posiY", String.valueOf(thetay));

        if (thetay < 0) {
            if (thetax < 36) {
                posi = 2;
            } else if (thetax < 72) {
                posi = 1;
            } else if (thetax < 108) {
                posi = 0;

            } else if (thetax < 144) {
                posi = 9;

            } else {
                posi = 8;

            }

        } else {
            if (thetax < 36) {
                posi = 3;


            } else if (thetax < 72) {
                posi = 4;

            } else if (thetax < 108) {
                posi = 5;

            } else if (thetax < 144) {

                posi = 6;
            } else {
                posi = 7;

            }

        }


        return posi;
    }

    private int isActItemPositionD(float x, float y, double radius) {
        int posi = 10;
        double thetax = 0.0;
        double thetay = 0.0;
        double fx = x - mCenterOfCircle.x;
        double fy = y - mCenterOfCircle.y;
        thetax = Math.toDegrees(Math.acos((fx / radius)));
        thetay = Math.toDegrees(Math.asin((fy / radius)));

        if (thetay < 0) {
            if (44 <= thetax && thetax <= 136) {
                if (thetax < 67) {
                    posi = 3;
                } else if (thetax < 90) {
                    posi = 2;

                } else if (thetax < 113) {
                    posi = 1;

                } else {
                    posi = 0;

                }

            }
        }
        // Log.d("posi",String.valueOf(posi));

        return posi;
    }


    /*
    ドロワー
     */

    private void drawInitialView() {
        Canvas canvas = mHolder.lockCanvas();
        Log.d("draw", "drawing");
        drawInitialView(canvas);
        drawNumber(canvas);
        drawAction(canvas);
        mHolder.unlockCanvasAndPost(canvas);
    }

    private void drawInitialView(Canvas canvas) {


        Paint paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


/*
        paint.setStrokeWidth(sWindowWidth / 6);
        // paint.setColor(Color.parseColor("#3333dd"));
        paint.setColor(Color.WHITE);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //NumCircle
        for (int i = 0; i < 10; i++) {
            canvas.drawArc(mNumberButtonRect, (float) i * 36, (float) 30, false, paint);
        }
        //ShiftCircle
        canvas.drawArc(mCenterButtonRect, 0f, 360f, true, paint);

        //ActCircle
        for (int i = 0; i < 4; i++) {
            canvas.drawArc(mActionButtonRect, i * 23 + 224f, 20f, false, paint);

        }
*/


// デバッグ用描画
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(2f);
        //canvas.drawRect(mActionButtonRect, paint);
        //canvas.drawRect(mCenterButtonRect, paint);
        //canvas.drawRect(mNumberButtonRect, paint);
        //canvas.drawLine(mCenterOfCircle.x, 0, mCenterOfCircle.x, sWindowHeight, paint);
        //canvas.drawLine(0, mCenterOfCircle.y, sWindowWidth, mCenterOfCircle.y, paint);
        //  canvas.drawCircle(mCenterOfCircle.x, mCenterOfCircle.y, 5f, paint);

        canvas.drawCircle(mCenterOfCircle.x, mCenterOfCircle.y, (float) mShiftRadius, paint);
        //canvas.drawCircle(mCenterOfCircle.x, mCenterOfCircle.y, (float) mMinNumRadius, paint);
       // canvas.drawCircle(mCenterOfCircle.x, mCenterOfCircle.y, (float) mMaxNumRadius, paint);
       // canvas.drawCircle(mCenterOfCircle.x, mCenterOfCircle.y, (float) mMinActRadius, paint);
        //canvas.drawCircle(mCenterOfCircle.x, mCenterOfCircle.y, (float) mMaxActRadius, paint);


        // canvas.drawCircle(mCenterOfCircle.x, mCenterOfCircle.y, (float) (mMaxNumRadius + mMinNumRadius) / 2, paint);


        //テキスト
        //drawNumber(canvas);
        //drawShiftNumber(canvas);
        //drawAction(canvas);
        //  canvas.drawRect(new Rect(0,0,200,200),paint);
    }


    private void drawNumber(Canvas canvas) {

        double radius = (mMinNumRadius + mMaxNumRadius) / 2;
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(30f);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        for (int i = 0; i < 10; i++) {
            float x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
            float y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
            canvas.drawText(String.valueOf(i), x, y, paint);

        }


    }

    private void drawShiftNumber(Canvas canvas) {

        double radius = (mMinNumRadius + mMaxNumRadius) / 2;
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(30f);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        int i = 0;
        float x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        float y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText(".", x, y, paint);
        i++;

        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("×", x, y, paint);
        i++;

        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("÷", x, y, paint);
        i++;

        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("＾", x, y, paint);
        i++;
        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("log", x, y, paint);
        i++;
        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("tan", x, y, paint);
        i++;
        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("cos", x, y, paint);
        i++;
        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("sin", x, y, paint);
        i++;

        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("＋", x, y, paint);
        i++;
        x = (float) (radius * Math.cos(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 36 - 90)) + mCenterOfCircle.y + 10f);
        canvas.drawText("－", x, y, paint);
        i++;

        canvas.drawText("＝", mCenterOfCircle.x, mCenterOfCircle.y, paint);


    }

    private void drawAction(Canvas canvas) {

        double radius = (mMinActRadius + mMaxActRadius) / 2;
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(30f);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        int i = 0;
        float x = (float) (radius * Math.cos(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.x);
        float y = (float) (radius * Math.sin(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.y + 10f);
        canvas.drawText("AC", x, y, paint);
        i++;

        x = (float) (radius * Math.cos(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.y + 10f);
        canvas.drawText("C", x, y, paint);
        i++;

        x = (float) (radius * Math.cos(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.y + 10f);
        canvas.drawText("(", x, y, paint);
        i++;
        x = (float) (radius * Math.cos(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.x);
        y = (float) (radius * Math.sin(Math.toRadians(i * 23 - 123.5)) + mCenterOfCircle.y + 10f);
        canvas.drawText(")", x, y, paint);
        i++;
    }

    private void drawItem(int posiR, int posiD, Canvas canvas) {

        switch (posiR) {
            case 0:
                drawItem0(canvas);
                break;
            case 1:
                drawItem1(posiD, canvas);
                break;
            case 2:
                if (!mModeShift) {
                    drawItem2(posiD, canvas);
                }
                break;

        }

    }

    private void drawItem0(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(150, 0, 200, 200));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(sWindowWidth / 6);
        // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawArc(mCenterButtonRect, 0f, 360f, true, paint);

    }

    private void drawItem1(int posiD, Canvas canvas) {
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(sWindowWidth / 6);
        paint.setColor(Color.argb(200, 0, 200, 200));
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //NumCircle
        canvas.drawArc(mNumberButtonRect, (float) posiD * 36 - 108, (float) 36, false, paint);
    }

    private void drawItem2(int posiD, Canvas canvas) {
        Paint paint = new Paint();

        if (posiD < 4) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(sWindowWidth / 6);
            paint.setColor(Color.argb(200, 0, 200, 200));
            //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawArc(mActionButtonRect, posiD * 23 + 224f, 23f, false, paint);
        }
    }

    private void drawOnActiveEvent(int posiR, int posiD) {
        Canvas canvas = mHolder.lockCanvas();
        drawInitialView(canvas);
        if (mModeShift) {
            drawShiftNumber(canvas);
            drawItem0(canvas);
        } else {
            drawNumber(canvas);
            drawAction(canvas);
        }
        drawItem(posiR, posiD, canvas);

        mHolder.unlockCanvasAndPost(canvas);
    }

    /*
    リスナー
     */

    public void onClickNumber(int num) {
        CalculateFragment calculateFragment = (CalculateFragment)mActivity.getFragmentManager().findFragmentByTag("calculator");
        if (calculateFragment instanceof CalculateView.CalculateViewListener) {
            CalculateViewListener listener = (CalculateViewListener) calculateFragment;
            listener.onSelectNumberItem(num);
        }
    }

    public void onClickShiftNumber(int num) {
        CalculateFragment calculateFragment = (CalculateFragment)mActivity.getFragmentManager().findFragmentByTag("calculator");

        if (calculateFragment instanceof CalculateView.CalculateViewListener) {
            CalculateViewListener listener = (CalculateViewListener) calculateFragment;
            listener.onSelectShiftNumberItem(num);
        }
    }

    public void onClickAction(int num) {
        CalculateFragment calculateFragment = (CalculateFragment)mActivity.getFragmentManager().findFragmentByTag("calculator");

        if (calculateFragment instanceof CalculateView.CalculateViewListener) {
            CalculateViewListener listener = (CalculateViewListener) calculateFragment;
            listener.onSelectActionItem(num);
        }
    }
    public void onClickEqual() {
        CalculateFragment calculateFragment = (CalculateFragment)mActivity.getFragmentManager().findFragmentByTag("calculator");

        if (calculateFragment instanceof CalculateView.CalculateViewListener) {
            CalculateViewListener listener = (CalculateViewListener) calculateFragment;
            listener.onSelectEqual();
        }
    }

    /*
    インターフェース
     */
    public interface CalculateViewListener {
        public void onSelectNumberItem(int item);

        public void onSelectShiftNumberItem(int item);

        public void onSelectActionItem(int item);

        public void onSelectEqual();

    }

}

