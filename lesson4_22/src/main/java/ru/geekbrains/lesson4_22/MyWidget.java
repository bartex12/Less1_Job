package ru.geekbrains.lesson4_22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidget extends AppWidgetProvider {

    private final static String ExtraMsg = "msg";
    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";


//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        //Создаем новый RemoteViews
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
//
//        //Подготавливаем Intent для Broadcast
//        Intent active = new Intent(context, HelloWidget.class);
//        active.setAction(ACTION_WIDGET_RECEIVER);
//        active.putExtra("msg", "Hello Habrahabr");
//
//        //создаем наше событие
//        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
//
//        //регистрируем наше событие
//        remoteViews.setOnClickPendingIntent(R.id.widget_button, actionPendingIntent);
//
//        //обновляем виджет
//        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
//    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        // Здесь обновим текст, будем показывать номер виджета
        views.setTextViewText(R.id.appwidget_text, String.format("%s - %d", widgetText, appWidgetId));

                //Подготавливаем Intent для Broadcast
        Intent active = new Intent(context, MyWidget.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        active.putExtra("msg", "Привет Владимиру Морозову");

        //создаем наше событие
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);

        //регистрируем наше событие
        views.setOnClickPendingIntent(R.id.button_widget, actionPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Ловим наш Broadcast, проверяем и выводим сообщение
        final String action = intent.getAction();
        if (ACTION_WIDGET_RECEIVER.equals(action)) {
            String msg = "null";
            try {
                msg = intent.getStringExtra("msg");
            } catch (NullPointerException e) {
                Log.e("Error", "msg = null");
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
