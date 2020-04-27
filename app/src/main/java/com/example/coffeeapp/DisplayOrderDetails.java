package com.example.coffeeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayOrderDetails extends AppCompatActivity {

    String name;
    String message;
    String strTotalPrice;

    //create an object of CoffeeDBHandler
    CoffeeDBHandler dbHandler = new CoffeeDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order_details);

        //create an intent to catch the String and display
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        message = intent.getStringExtra("message");
        strTotalPrice = intent.getStringExtra("saleAmount");

        //getting the final output ready
        String output = "Name: "+name +"\n"+ message;

        //create an object to the TextView to display
        TextView displayTextView = (TextView)findViewById(R.id.displayText);
        displayTextView.setText(output);
    }
    //a method that will open GMail
    public void openEmail (View view){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order for "+name);
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    //button to save data in SQLite database
    public void addButtonClicked(View view){
        //start the new intent here
        Order order = new Order(name, Integer.parseInt(strTotalPrice));
        dbHandler.addOrder(order);
        Toast.makeText(getApplicationContext(), "Data Saved!",Toast.LENGTH_SHORT).show();
        }

    //method to send the report details
    public void salesReport(View view) {
        //read the details from the database to produce the report
        String dbString = dbHandler.databaseToString();
        //start the new intent here
        Intent salesIntent = new Intent(this,  DisplaySalesDetails.class);
        salesIntent.putExtra("db", dbString);
        startActivity(salesIntent);
    }
}