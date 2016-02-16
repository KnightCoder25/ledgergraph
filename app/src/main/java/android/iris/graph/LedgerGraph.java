package android.iris.graph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by rohit on 16/2/16.
 */
public class LedgerGraph extends View {


    private static String TAG = "LedgerGraph";
    private float maxValue = 0;
    private int percentageWitdh = 20;

    private float oldLeft = 0;

    private Paint paint;



    private ArrayList<BarData> datas= new ArrayList<>();

    public LedgerGraph(Context context) {
        super(context);
    }

    public LedgerGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LedgerGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LedgerGraph(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setData(ArrayList<BarData> newDatas){
       this.datas = newDatas;
    }


    public void draw(){

        paint = new Paint();
        paint.setColor(Color.BLACK);


        if (datas.isEmpty()){
            Log.v(TAG,"No data set");
            return;
        }
        for (BarData bar:datas){
            float currentValue = bar.getCredit()+bar.getDebit();
            if (maxValue <=currentValue){
                maxValue =currentValue;
            }
        }
        Log.v(TAG, "MAX RANGE: "+ maxValue);

        invalidate();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();


        if (datas.isEmpty()){
            return;
        }
        Log.v(TAG,"onDraw width: "+width+" height: "+height);




         canvas.drawBitmap(drawBars(width,height),0,0,new Paint());

    }

    private Bitmap drawBars(int width, int height){

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.RED);
        float widthWithSpace = width/datas.size();

        float barSpace = widthWithSpace*(percentageWitdh/100f);
        float barWidth = widthWithSpace - barSpace;

        for (BarData barData: datas){

            float left = oldLeft+barSpace;
            float right = left+barWidth;
            float bottom = height;

            float unit = height/maxValue;

            float actualbarHeight = unit* (barData.getCredit()+barData.getDebit());
            float top = bottom-actualbarHeight;
          //  float top = 10;

            Log.v(TAG,"onDraw left: "+left+" right: "+right+" top: "+top+" bottom: "+bottom);

            canvas.drawRect(left,top,right,bottom,paint);
            //TODO: Do another call for bottom

            oldLeft = right;

        }

        return bitmap;
    }







}
