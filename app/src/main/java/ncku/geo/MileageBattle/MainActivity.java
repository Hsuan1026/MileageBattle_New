package ncku.geo.MileageBattle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener, DialogInterface.OnClickListener{

    SensorManager sm;
    Sensor sr;
    ImageView character;
    ConstraintLayout layout;
    Boolean isgoup=false;
    float pasthorizontal;
    AlertDialog.Builder bdr;
    String ruleText="";
    Toast tos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sr=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sr,SensorManager.SENSOR_DELAY_NORMAL);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        character=findViewById(R.id.character1);
        ConstraintLayout.LayoutParams paras=(ConstraintLayout.LayoutParams) character.getLayoutParams();
        paras.horizontalBias=0.7f;
        paras.verticalBias=0.79f;
        pasthorizontal=paras.horizontalBias;
        character.setLayoutParams(paras);
        tos=Toast.makeText(this,"",Toast.LENGTH_SHORT);
        bdr=new AlertDialog.Builder(this);
        ruleText="1.藉由左右晃動手機使角色到達螢幕右方並進入遊戲\n"+
                "\n2.遊戲開始前，會先藉由抽籤來決定玩家先後順序及各自的起點國家(基地)\n"+
                "\n3.遊戲開始雙方會先獲得各三張卡牌，並由優先玩家先抽牌\n"+
                "\n4.抽牌後，玩家可以選擇是否要拿走抽出的牌，若是則同時要棄一張手上的卡牌，否則換另一位玩家抽牌\n"+
                "\n5.雙方抽牌結束後，則回合結束，若有一方的三張卡牌達成下一國的條件，則可以前進到下一國，在玩家移動的過程中將可以累積里程數\n"+
                "\n6.全部回合結束後，當在過程中某一方先到達另一方的起點國，則獲勝，若雙方皆無人到達另一方起點國，則以里程數多者獲勝";
        bdr.setTitle("Rule");
        bdr.setMessage(ruleText);
        bdr.setPositiveButton("確認",this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x=event.values[0];
        ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams)character.getLayoutParams();
        character=findViewById(R.id.character1);
        //control character
        params.horizontalBias=(-x+9)/18;
        if(params.horizontalBias<0){
            params.horizontalBias=0;
        }
        if(params.horizontalBias>1){
            params.horizontalBias=1;
        }
        if(isgoup){
            if(params.horizontalBias>0.135f){
                params.horizontalBias=0.135f;
            }
            params.verticalBias=(-20/6)*params.horizontalBias+0.44f;
            if(params.verticalBias>=0.44f){
                isgoup=false;
            }
        }else {
            if(params.horizontalBias<=0.7f){
                params.verticalBias=0.5f*params.horizontalBias+0.44f;
                if(params.verticalBias<=0.44f){
                    isgoup=true;
                }
            }else{
                params.verticalBias=0.79f;
            }
        }
        //change image
        if(pasthorizontal<params.horizontalBias+0.01){
            character.setImageDrawable(getResources().getDrawable(R.drawable.character1_nb));
        }else {
            character.setImageDrawable(getResources().getDrawable(R.drawable.character1));
        }
        pasthorizontal=params.horizontalBias;
        //message
        if(params.horizontalBias==1){
            tos.setText("Welcome!");
            tos.show();
            onPause();
        }
        if(params.verticalBias<0.05){
            onPause();
            bdr.show();
        }
        character.setLayoutParams(params);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,sr,SensorManager.SENSOR_DELAY_NORMAL);
        ConstraintLayout.LayoutParams paras=(ConstraintLayout.LayoutParams) character.getLayoutParams();
        paras.horizontalBias=0.7f;
        paras.verticalBias=0.79f;
        isgoup=false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            onResume();
        }
    }
}