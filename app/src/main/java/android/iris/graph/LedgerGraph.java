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
    // In Dp
    private int strokeWidth = 1;
    private int paddingInDp = 8;


    private Paint paintCredit;
    private Paint paintDebit;
    private Paint paintLines;

    private float xAxis=0;

    private int paddingInPixels;


    private ArrayList<Float> barCenters = new ArrayList<>();


    private ArrayList<BarData> datas = new ArrayList<>();

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


    public void setData(ArrayList<BarData> newDatas) {
        this.datas = newDatas;
    }


    public void draw() {


        paddingInPixels = (int) getPixels(paddingInDp);

        Log.v(TAG, "paddingInPixels " + paddingInPixels);


        paintCredit = new Paint();
        paintCredit.setColor(Color.CYAN);

        paintDebit = new Paint();
        paintDebit.setColor(Color.BLUE);

        paintLines = new Paint();
        paintLines.setColor(Color.GRAY);
        paintLines.setStrokeWidth(getPixels(strokeWidth));
        paintLines.setStyle(Paint.Style.STROKE);


        if (datas.isEmpty()) {
            Log.v(TAG, "No data set");
            return;
        }
        for (BarData bar : datas) {
            float currentValue = bar.getCredit() + bar.getDebit();
            if (maxValue <= currentValue) {
                maxValue = currentValue;
            }
        }
        Log.v(TAG, "MAX RANGE: " + maxValue);

        invalidate();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (datas.isEmpty()) {
            return;
        }
        Log.v(TAG, "onDraw width: " + width + " height: " + height);

         float unitHeight = height/100f;


        int topPadding = (int)(5*unitHeight);


        canvas.drawBitmap(drawBars(width, (int)(80*unitHeight), 0)
                , 0, topPadding, new Paint());


        // X Axis
        canvas.drawLine(0f, xAxis+topPadding, width, xAxis+topPadding, paintLines);
        // Y Axis
        canvas.drawLine((float) paddingInPixels, 0f, (float) paddingInPixels, height, paintLines);

    }

    private Bitmap drawBars(int width, int height, float left) {


        Log.v(TAG,"drawBars Width: "+width+" height:"+height+" left:"+left);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float oldLeft = left;

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        float widthWithSpace = width / datas.size();

        float barSpace = widthWithSpace * (percentageWitdh / 100f);
        float barWidth = widthWithSpace - barSpace;


        barCenters.clear();


        for (BarData barData : datas) {

            float barLeft = oldLeft + barSpace;
            float right = barLeft + barWidth;
            float creditBottom = height / 2;

            // Calculate the centres
            barCenters.add(right/2f);


            float unit = height / maxValue;

            float creditHeight = unit * barData.getCredit();
            float creditTop = creditBottom - creditHeight;

            float debitHeight = unit * barData.getDebit();
            float debitTop = creditBottom;
            float debitBottom = debitTop + debitHeight;

            xAxis = creditBottom;

            Log.v(TAG,"draw bar barLeft:"
                    +barLeft+" creditTop "+creditTop+" right:"+right+" creditBottom: "+creditBottom);

            // Draw credit
            canvas.drawRect(barLeft, creditTop, right, creditBottom, paintCredit);
            // Draw debit
            canvas.drawRect(barLeft, debitTop, right, debitBottom, paintDebit);

            oldLeft = right;

        }

        return bitmap;
    }


    public float getPixels(int dp) {

        //Resources r = boardContext.getResources();
        //float px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpis, r.getDisplayMetrics());

        final float scale = this.getContext().getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);

        Log.v(TAG, "getPixels: " + strokeWidth);

        return px;

    }


}
