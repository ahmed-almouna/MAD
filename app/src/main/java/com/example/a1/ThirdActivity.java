package com.example.a1;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class ThirdActivity extends ComponentActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
    }
}
