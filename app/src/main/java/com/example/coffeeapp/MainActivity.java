package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int noOfCoffee = 0;
    int priceOfCoffee = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //submitOrder button to display order

    public void submitOrder(View v){
        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox)
                findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox)
                findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //call te method
        int totalPrice = calculateTotalPrice(hasWhippedCream,hasChocolate);

        //get the name of the user
        EditText nameOfUser = (EditText)findViewById(R.id.nameOfCust);
        String name = nameOfUser.getText().toString();

        //bundle of the rest of the information into a String
        String message = "Add whipped cream? " +hasWhippedCream+"\n"+
                "Add Chocolate? " +hasChocolate+"\n"+
                "Quantity: " +noOfCoffee+"\n"+
                "Total: $" +totalPrice+"\n"+
                "Thank you!";

    //create a new intent to bundle off the values to the new intent

    Intent intent = new Intent(this, DisplayOrderDetails.class);
    intent.putExtra("name", name);
    intent.putExtra("message", message);
    intent.putExtra("saleAmount", Integer.toString(totalPrice));
    startActivity(intent);
    }

    //create a method which calculates the total price taking the booleans as parameters
    public int calculateTotalPrice(boolean  hasWhippedCream, boolean hasChocolate){
      if(hasWhippedCream == true) {
          priceOfCoffee = priceOfCoffee + 1;
      }
      if(hasChocolate == true) {
          priceOfCoffee = priceOfCoffee + 2;
      }

    //calculate the total price
       int totalPrice = priceOfCoffee * noOfCoffee;
      return totalPrice;
    }

    public void display(int number){
        //display total number

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }

    //method to increase the number of coffee by 1
        public void increment(View v){
        noOfCoffee = noOfCoffee+1;
            if(noOfCoffee >= 10) {
                noOfCoffee = 10;
            }
        display(noOfCoffee);
    }

    //method to decrease the number of coffee by 1
    public void decrement(View v){
        noOfCoffee = noOfCoffee-1;
        if(noOfCoffee <= 0) {
            noOfCoffee = 0;
        }
        display(noOfCoffee);
    }

    CoffeeDBHandler dbHandler = new CoffeeDBHandler(this, null, null, 1);

    public void salesReport(View view) {
        //read the details from the database to produce the report
        String dbString = dbHandler.databaseToString();
        //start the new intent here
        Intent salesIntent = new Intent(this,  DisplaySalesDetails.class);
        salesIntent.putExtra("db", dbString);
        startActivity(salesIntent);
    }

}



