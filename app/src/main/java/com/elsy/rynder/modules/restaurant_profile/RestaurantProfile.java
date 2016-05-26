package com.elsy.rynder.modules.restaurant_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.elsy.rynder.R;
import com.elsy.rynder.domain.Element;
import com.elsy.rynder.domain.Menu;
import com.elsy.rynder.domain.Restaurant;
import com.elsy.rynder.domain.RestaurantPhone;
import com.elsy.rynder.domain.Schedule;
import com.elsy.rynder.modules.menu.MenuActivity;
import com.elsy.rynder.utils.ActivityHelper;
import com.elsy.rynder.utils.Injection;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class RestaurantProfile extends AppCompatActivity implements RestaurantProfileContract.View {

    private Toolbar toolbar;
    private TextView addressTextView;
    private TextView scheduleTextView;
    private TextView typeTextView;
    private TextView phoneTextView;
    private ImageView restaurantImageView;
    private LinearLayout informationLinearLayout;
    private TextView menusHeaderTextView;
    private CollapsingToolbarLayout mCollapsinToolbarLayout;
    private CoordinatorLayout mCoordinator;
    private String mRestaurantID;
    private Restaurant mRestaurant;
    private ProgressDialog mProgressDialog;
    private RestaurantProfileContract.UserActionsListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        setSupportActionBar(toolbar);
        initUI();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionsListener = new RestaurantProfilePresenter(
                this, Injection.provideRestaurantInteractor(), Injection.provideUserSessionManager(this)
        );

        mRestaurantID = getIntent().getStringExtra("restaurantID");
        Gson gson = new Gson();
        String restaurantStr = getIntent().getStringExtra("restaurant");
        mRestaurant = gson.fromJson(restaurantStr, Restaurant.class);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setData(mRestaurant);
        mProgressDialog = ActivityHelper.createModalProgressDialog(this, "Obteniendo información");
        mActionsListener.loadRestaurantInformation(mRestaurantID);
    }

    @Override
    public void setProgressIndicator(boolean active){
        if(active){
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showRestaurant(Restaurant restaurant) {
        setData(restaurant);
    }

    @Override
    public void showFailedLoadMessage() {
        Snackbar.make(mCoordinator, "Error al actualizar datos", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(mCoordinator, message, Snackbar.LENGTH_SHORT).show();
    }

    private void setData(Restaurant restaurant) {
        if(restaurant.getAddress() != null){
            addressTextView.setText(restaurant.getAddress());
        }
        typeTextView.setText(restaurant.getType());
        mCollapsinToolbarLayout.setTitle(restaurant.getName());
        setImage(restaurant.getProfileImage());
        setPhoneNumbers(restaurant.getRestaurantPhoneNumbers());
        setSchedules(restaurant.getSchedules());
        setMenus(restaurant.getMenus());
    }


    private void setImage(String imageURL){
        Picasso.with(getApplicationContext())
                .load(imageURL)
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(restaurantImageView);
    }


    private void setPhoneNumbers(ArrayList<RestaurantPhone> phoneNumbers) {
        if(phoneNumbers != null &&  !phoneNumbers.isEmpty()){
            phoneTextView.setText(phoneNumbers.get(0).getNumber());
        } else {
            phoneTextView.setText("No disponible");
        }
    }

    private void setSchedules(ArrayList<Schedule> schedules) {
        if(schedules != null && !schedules.isEmpty()){
            Schedule schedule = schedules.get(0);
            scheduleTextView.setText(
                    schedule.getWeekDay() +
                            " de " + String.valueOf(schedule.getOpenHour()) +
                            " hasta " + String.valueOf(schedule.getCloseHour())
            );
        } else {
            scheduleTextView.setText("No disponible");
        }
    }

    private void setMenus(ArrayList<Menu> menus) {
            if(menus != null && !menus.isEmpty()){
                menusHeaderTextView.setVisibility(View.VISIBLE);
                for(Menu menu : menus){
                    setMenu(menu);
                }
            }

    }

    private void setMenu(final Menu menu) {
        final int FIRST_ELEMENT=0;
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.adapter_menu, null, false);
        TextView tx_menu_name = (TextView) layout.findViewById(R.id.menu_name);
        Button btn_show_menu = (Button) layout.findViewById(R.id.show_menu);
        tx_menu_name.setText(menu.getName());

        btn_show_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantProfile.this, MenuActivity.class);
                intent.putExtra("Menu",menu);
                startActivity(intent);
                //ActivityHelper.begin(RestaurantProfile.this, MenuActivity.class);
            }
        });

        if (!menu.getSections().isEmpty()) {
            if (!menu.getSections().get(FIRST_ELEMENT).getElements().isEmpty()) {
                setElement(menu.getSections().get(FIRST_ELEMENT).getElements().get(FIRST_ELEMENT), layout);
            }
        }

        informationLinearLayout.addView(layout);

    }

        private void setElement(Element element, RelativeLayout layout){
            ImageView img_menu_item = (ImageView) layout.findViewById(R.id.menu_item);
            TextView tx_menu_item_name = (TextView) layout.findViewById(R.id.menu_item_name);
            TextView menu_item_price = (TextView) layout.findViewById(R.id.menu_item_price);
            TextView menu_item_description = (TextView) layout.findViewById(R.id.menu_item_description);

            tx_menu_item_name.setText(element.getName());
            menu_item_description.setText(element.getDescription());
            menu_item_price.setText(element.getPrice());
            Picasso.with(getApplicationContext())
                    .load(element.getImage())
                    .placeholder(R.drawable.restaurant_image_placeholder)
                    .error(R.drawable.restaurant_image_error)
                    .into(img_menu_item);
        }

    private void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        scheduleTextView = (TextView) findViewById(R.id.schedule);
        typeTextView =  (TextView) findViewById(R.id.type);
        phoneTextView =  (TextView) findViewById(R.id.phone);
        restaurantImageView = (ImageView) findViewById(R.id.restaurant);
        informationLinearLayout = (LinearLayout) findViewById(R.id.rl_principal);
        menusHeaderTextView = (TextView) findViewById(R.id.menusHeader);
        mCollapsinToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.restaurant_detail_coordinator_layout);
    }

}
