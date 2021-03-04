package com.example.bottomsheetandroid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.bottomsheetandroid.behavior.BehaviorBottomSheet;
import com.example.bottomsheetandroid.models.Student;
import com.example.bottomsheetandroid.services.NotificationReceiver;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;
import static com.example.bottomsheetandroid.services.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterRecyclerView adapterRecyclerView;
    private List<Student> studentList;
    private Button btnPushNotification, btnHidden;
    private  BehaviorBottomSheet bottomSheetBehavior;
    private View bottomSheet;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        btnPushNotification.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });

        btnHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BehaviorBottomSheet.STATE_HIDDEN);
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BehaviorBottomSheet.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BehaviorBottomSheet.STATE_COLLAPSED:
                        Log.d("bottomsheet-", "STATE_COLLAPSED");
                        break;
                    case BehaviorBottomSheet.STATE_DRAGGING:
                        Log.d("bottomsheet-", "STATE_DRAGGING");
                        break;
                    case BehaviorBottomSheet.STATE_EXPANDED:
                        Log.d("bottomsheet-", "STATE_EXPANDED");
                        break;
                    case BehaviorBottomSheet.STATE_ANCHOR_POINT:
                        //bottomSheetBehavior.setAnchorPoint(300);
                        break;
                    case BehaviorBottomSheet.STATE_HIDDEN:
                        bottomSheet.setVisibility(View.GONE);
                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void initData() {
        studentList = new ArrayList<>();
        studentList.add(new Student(1, "Đỗ Văn Nam"));
        studentList.add(new Student(2, "Lê Thị Hà"));
        studentList.add(new Student(3, "Trần Quang Hiếu"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterRecyclerView =new AdapterRecyclerView(studentList, this);
        recyclerView.setAdapter(adapterRecyclerView);
    }

    private void initView() {
         recyclerView = findViewById(R.id.recyclerView);
         coordinatorLayout =findViewById(R.id.coordinatorlayout);

         btnPushNotification = findViewById(R.id.btnPushNotification);
         btnHidden = findViewById(R.id.btnHidden);

         bottomSheet = findViewById(R.id.bottom_sheet);
         bottomSheetBehavior = BehaviorBottomSheet.from(bottomSheet);

         int height = (Resources.getSystem().getDisplayMetrics().heightPixels)/2;
         bottomSheetBehavior.setAnchorPoint(height);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showNotification(){

        @SuppressLint("RemoteViewLayout") RemoteViews notificationLayout =
                new RemoteViews(getPackageName(), R.layout.custom_layout_extend);

        Intent clickIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0, clickIntent, 0);
        notificationLayout.setOnClickPendingIntent(R.id.main_layout, clickPendingIntent);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification
                notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(NotificationCompat.DEFAULT_SOUND| DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(uri)
                .setCustomBigContentView(notificationLayout)
                .build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(2, notification);
    }

    //close bottom sheet
    @Override public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {
                Rect outRect = new Rect();
                bottomSheet.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }

        return super.dispatchTouchEvent(event);
    }

}
