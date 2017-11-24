package wenge.com.coordinatorlayouttest;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        CollapsingToolbarLayout coll = (CollapsingToolbarLayout) findViewById(R.id.Coll_toolbar_layout);


    }
}
