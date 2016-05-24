package com.elsy.rynder.modules.budget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.edmodo.rangebar.RangeBar;
import com.elsy.rynder.R;
import com.elsy.rynder.utils.Injection;
import com.elsy.rynder.utils.preferences_manager.BudgetPreferencesManager;


public class BudgetActivity extends AppCompatActivity {

    private Button btn_search;
    private RangeBar ranger_budget;
    private TextView ranger_right;
    private TextView ranger_left;
    private final int MIN_VALUE=0;
    private final int MAX_VALUE=2000;
    private final String CURRENCY="MX";
    private BudgetPreferencesManager budgetPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        initUI();
       budgetPreferences = Injection.provideBudgetPreferencesManager(BudgetActivity.this);
        initRangerBudget();
        ranger_budget.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                updateValues(leftThumbIndex,rightThumbIndex);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMap();
            }
        });

    }

    private void goMap(){
        budgetPreferences.registerBudgetValues(getMin(),getMax());
        finish();
    }

    private void updateValues(int leftThumbIndex, int rightThumbIndex){
        if(leftThumbIndex<MIN_VALUE) leftThumbIndex=MIN_VALUE;
        if(rightThumbIndex>MAX_VALUE) rightThumbIndex=MAX_VALUE;
        ranger_left.setText(leftThumbIndex+CURRENCY);
        ranger_right.setText(rightThumbIndex+CURRENCY);
    }

    private void initRangerBudget(){
        int min,max;

        if(budgetPreferences.hasAlreadyChooseBudget()){
            min=budgetPreferences.getBudgetMin();
            max=budgetPreferences.getBudgetMax();
        }else{
            min=getMin();
            max=getMax();
        }
        ranger_budget.setThumbIndices(min,max);
        ranger_left.setText(min+CURRENCY);
        ranger_right.setText(max+CURRENCY);
    }

    private int getMin(){
        int leftThumbIndex;
        leftThumbIndex=ranger_budget.getLeftIndex();
        if(leftThumbIndex<MIN_VALUE){
            leftThumbIndex=MIN_VALUE;
        }
        return leftThumbIndex;
    }

    private int getMax(){
        int rightThumbIndex;
        rightThumbIndex=ranger_budget.getRightIndex();
        if(rightThumbIndex>MAX_VALUE){
            rightThumbIndex=MAX_VALUE;
        }
        return rightThumbIndex;
    }

    private void initUI(){
        btn_search = (Button) findViewById(R.id.search);
        ranger_budget = (RangeBar) findViewById(R.id.budget);
        ranger_right = (TextView) findViewById(R.id.right);
        ranger_left = (TextView) findViewById(R.id.left);
    }

}
