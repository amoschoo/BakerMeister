//package com.example.android.backend;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.example.android.slidingtabsbasic.R;
//
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.net.URISyntaxException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//
//public class Test extends Activity {
//
//    private HashMap<String, Double> values = new HashMap<>();
//    private Socket mSocket;
//    private List<String> sensors = Arrays.asList("sensor1", "sensor2", "sensor3");
//    {
//        try {
//            mSocket = IO.socket("http://128.199.213.105:3000");
//        } catch (URISyntaxException e) {
//            System.out.println("error here");
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.on("new message", onNewMessage);
//        mSocket.connect();
//
//        startSignIn();
//    }
//
//    ///////
//
//    private void startSignIn() {
//        mSocket.emit("add user", "App");
//    }
//
//    private Emitter.Listener onConnectError = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            Toast.makeText(getApplicationContext(), "Error connecting to server", Toast.LENGTH_LONG).show();
//        }
//    };
//
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            JSONObject data = (JSONObject) args[0];
//            for (String sensor: sensors
//                 ) {
//                try{
//                    JSONObject message = data.getJSONObject("message");
//                    String value = message.getString(sensor);
//                    values.put(sensor,Double.parseDouble(value));
//                } catch (JSONException e){
//
//                }
//            }
//            System.out.println(values);
//        }
//    };
//}
