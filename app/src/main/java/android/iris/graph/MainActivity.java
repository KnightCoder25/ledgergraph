package android.iris.graph;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LedgerGraph ledgerGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ledgerGraph = (LedgerGraph)findViewById(R.id.ledgerGraph);


        ArrayList<BarData> datas = new ArrayList<>();

        datas.add(new BarData(6f,15f,0,"Jan"));
        datas.add(new BarData(15f,10f,0,"Feb"));
        datas.add(new BarData(12f,6f,0,"Mar"));
        datas.add(new BarData(11f,7f,0,"Apr"));
        datas.add(new BarData(10f,13f,0,"May"));



        ledgerGraph.setData(datas);
        ledgerGraph.draw();




    }

}
