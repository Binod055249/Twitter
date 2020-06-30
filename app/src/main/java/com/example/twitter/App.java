package com.example.twitter;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("YnQjJ6TGtGa0vyokUHycWrxi2lhoAYix97jmhPUy")
                .clientKey("4F96mBF0FN9XDGNhyFvUVbomVzThcdmJPW6ZNqTJ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
