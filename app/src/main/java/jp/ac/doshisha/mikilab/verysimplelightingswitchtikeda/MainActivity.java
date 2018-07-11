package jp.ac.doshisha.mikilab.verysimplelightingswitchtikeda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Light> lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocketClient.setEndpoint(new InetSocketAddress( "172.20.11.58",44344));

        new Thread(new Runnable(){
            public void run(){
                lights = SocketClient.getLights();
            }
        }).start();
    }

    public void onButtonClick(View v){
        final ArrayList<Light> mylights = new ArrayList<>();
        for (int i=6; i<=11; i++){
            mylights.add(lights.get(i));
        }
        switch (v.getId()){
            case R.id.button_on:
                for (int i=0; i< mylights.size(); i++) {
                    mylights.get(i).setLumPct(60.0);
                }
                new Thread(new Runnable(){
                    public void run(){
                        SocketClient.dimByLights(mylights);
                    }
                }).start();
                break;
            case R.id.button_off:
                for (int i=0; i< mylights.size(); i++){
                    mylights.get(i).setLumPct(0.0);
                }
                new Thread(new Runnable(){
                    public void run(){
                        SocketClient.dimByLights(mylights);
                    }
                }).start();
                break;
        }
    }
}
