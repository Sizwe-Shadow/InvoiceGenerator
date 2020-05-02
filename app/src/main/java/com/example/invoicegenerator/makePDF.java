package com.example.invoicegenerator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class makePDF extends AppCompatActivity {

    int pageWidth = 1200;
    Bitmap bmp, bmpScaled;
    Button genInvoice;
    EditText nameView, addressView, productView, quantityView, priceView, discountView;
    Date date;
    DateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_p_d_f);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = new Date();
        ///dateFormat
        genInvoice = findViewById(R.id.genInvoiceBtn);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.greatmindsicon);
        bmpScaled = Bitmap.createScaledBitmap(bmp, 200, 200, false);
        nameView = findViewById(R.id.customerEditText);
        addressView = findViewById(R.id.addressEditText);
        productView= findViewById(R.id.prodEditText);
        quantityView = findViewById(R.id.quantityEditText);
        priceView = findViewById(R.id.priceEditText);
        discountView = findViewById(R.id.discountEditText);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                createPDF();

    }

    private void createPDF(){

        genInvoice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                if (nameView.getText().toString().isEmpty() || addressView.getText().toString().isEmpty()|| productView.getText().toString().isEmpty() || quantityView.getText().toString().isEmpty()) {
                    Toast.makeText(makePDF.this, "Empty fields", Toast.LENGTH_LONG).show();
                }

                else {
                    //create PDF document
                    PdfDocument document = new PdfDocument();
                    Paint paint = new Paint();
                    Paint titlePaint = new Paint();


                    //create a page description
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();

                    //start a page
                    PdfDocument.Page page = document.startPage(pageInfo);

                    Canvas canvas = page.getCanvas();
                    canvas.drawBitmap(bmpScaled, 10, 10, paint);


                    titlePaint.setColor(Color.rgb(0, 113, 110));
                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(Typeface.DEFAULT_BOLD);
                    titlePaint.setTextSize(70);
                    canvas.drawText("INVOICE", pageWidth/2, 180, titlePaint);

                    paint.setColor(Color.GREEN);
                    canvas.drawLine(0, 210, pageWidth, 210, paint);


                    paint.setColor(Color.rgb(0, 113, 110));
                    paint.setTextSize(30);
                    paint.setTextAlign(Paint.Align.LEFT);

                    canvas.drawText("Unit 17 Go Store It Park",20,240, paint);
                    canvas.drawText("14 Penkop Street",20,290,paint);
                    canvas.drawText("East London  5208",20,340,paint);
                    canvas.drawText("Ph:  043 012 5015 ",20,390,paint);
                    canvas.drawText("Cell: 073 552 9377 / 082 566 0946",20,440,paint);
                    canvas.drawText("Email: info.greatmindz@gmail.com",20,490,paint);


                    dateFormat = new SimpleDateFormat("dd/MM/yy");
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("Date: " + dateFormat.format(date), pageWidth-20, 240,paint);
                    canvas.drawText("Invoice #: 00001", pageWidth-20, 290, paint);
                    canvas.drawText("VAT Number: 12345678", pageWidth-20, 340, paint);

                    paint.setColor(Color.GREEN);
                    canvas.drawLine(0, 540, pageWidth, 540, paint);

                    paint.setColor(Color.BLACK);
                    paint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText(nameView.getText().toString().trim(), 20, 570, paint);
                    canvas.drawText(addressView.getText().toString().trim(), 20, 620, paint);


                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2);
                    canvas.drawRect(0,780,pageWidth, 860, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText("QTY", 40, 830, paint);
                    canvas.drawText("Item Description", 200, 830, paint);
                    canvas.drawText("Unit Price ", 700, 830, paint);
                    canvas.drawText("Discount", 900, 830, paint);
                    canvas.drawText("Line Total", 1050, 830, paint);
                    canvas.drawLine(180, 790, 180, 840, paint);
                    canvas.drawLine(680, 790, 680, 840, paint);
                    canvas.drawLine(880, 790, 880, 840, paint);
                    canvas.drawLine(1030, 790, 1030, 840, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText(quantityView.getText().toString().trim(), 40, 890, paint);
                    canvas.drawText(productView.getText().toString().trim(), 200, 890, paint);
                    canvas.drawText(priceView.getText().toString().trim(), 700, 890, paint);
                    canvas.drawText(discountView.getText().toString().trim(), 900, 890, paint);
                    canvas.drawText(priceView.getText().toString().trim(), 1050, 890, paint);


                    document.finishPage(page);

                    File file = new File(Environment.getExternalStorageDirectory(), "/newInvoice.pdf");

                    try {
                        document.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    document.close();

                    Toast.makeText(makePDF.this, "Invoice Generated", Toast.LENGTH_LONG).show();
                    nameView.setText(" ");
                    addressView.setText(" ");
                    discountView.setText(" ");
                    productView.setText(" ");
                    priceView.setText(" ");
                    quantityView.setText(" ");
                }


            }
        });




    }


}
