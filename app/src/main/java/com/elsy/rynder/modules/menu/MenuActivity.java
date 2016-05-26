package com.elsy.rynder.modules.menu;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.elsy.rynder.R;
import com.elsy.rynder.domain.Menu;
import com.elsy.rynder.ui.adapters.SectionsPagerAdapter;


public class MenuActivity extends AppCompatActivity implements MenuContract.View {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MenuContract.UserActionsListener mActionsListener;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu = (Menu) getIntent().getSerializableExtra("Menu");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(menu.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),menu,this);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mActionsListener = new MenuPresenter(this, this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    protected void onPause() {
        mActionsListener.unRegisterListener();
        super.onPause();
    }


    public void changeLeftPage(){
        int size =menu.getSections().size();
        int postion = mViewPager.getCurrentItem();
        if(postion < size){
            mViewPager.setCurrentItem(postion+1);
            Toast.makeText(this, "Avanzaste una página",Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "No hay más paginas",Toast.LENGTH_LONG).show();
    }

    public void changeRightPage(){
        int size =menu.getSections().size();
        int postion = mViewPager.getCurrentItem();
        if(postion > 0){
            mViewPager.setCurrentItem(postion-1);
            Toast.makeText(this, "Retrocediste una página",Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Estas en la primera pagina",Toast.LENGTH_LONG).show();

    }


}
