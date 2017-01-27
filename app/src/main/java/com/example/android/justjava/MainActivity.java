package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mQuantity = 1;
    private String mName;
    private boolean mHasWhippedCream = false;
    private boolean mHasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(mQuantity);
    }

    public void increment(View view){
        if(mQuantity >= 100) {
            Toast.makeText(this, "You cannot order more than 99 coffees!", Toast.LENGTH_SHORT).show();
            return;
        }
        mQuantity++;
        display(mQuantity);
    }

    public void decrement(View view){
        if(mQuantity > 1) {
            mQuantity--;
            display(mQuantity);
        } else {
            Toast.makeText(this, "You cannot order less than 1 coffee!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        getName();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, String.format("Order for %s",mName));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(mQuantity));
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(intent, "Send email..."));
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + this.mQuantity);
    }

    private void getName(){
        EditText quantityTextView = (EditText) findViewById(R.id.name_edit_text_view);
        mName = quantityTextView.getText().toString();
    }

    public void onCheckBoxClick(View v){

        switch (v.getId()){
            case R.id.chocolate_checkbox:{
                mHasChocolate = ((CheckBox)v).isChecked();
                return;
            }
            case R.id.cream_checkbox:{
                mHasWhippedCream = ((CheckBox)v).isChecked();
                return;
            }
            default:return;
        }
    }

    float calculatePrice(){
        float basePrice = 2;
        if(mHasWhippedCream){
            basePrice += 0.20;
        }
        if(mHasChocolate){
            basePrice += 0.20;
        }
        return basePrice * mQuantity;
    }

    private String createOrderSummary(int quantity){
        float price = calculatePrice();
        String priceMessage = getString(R.string.oreder_summary_name,mName);
        priceMessage += "\nAdd whipped cream? " + (mHasWhippedCream?"yes":"no");
        priceMessage += "\nAdd chocolate? " + (mHasChocolate?getString(R.string.yes):getString(R.string.thank_you));
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: " + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}