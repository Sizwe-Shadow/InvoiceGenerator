package com.example.invoicegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {

    ImageButton createInvoiceBtn, genStatementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        createInvoiceBtn =  (ImageButton) this.findViewById(R.id.invoiceimgBtn);
        genStatementBtn = findViewById(R.id.statementImgBtn);


        createInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, makePDF.class));
            }
        });


        //wanna generate a statement, will use the database to access statements or information in a statement this should take you to another class
//        genStatementBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Main2Activity.this, ));
//            }
//        });

    }
}
