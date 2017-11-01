package com.demo.biodata;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText edit_name;
    EditText edit_course;
    EditText edit_address;
    EditText edit_phone;
    EditText edit_email;
    Button btn_generatepdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intializing the edit texts and button
        edit_name= (EditText) findViewById(R.id.edit_name);
        edit_course= (EditText) findViewById(R.id.edit_course);
        edit_address= (EditText) findViewById(R.id.edit_address);
        edit_phone= (EditText) findViewById(R.id.edit_phone);
        edit_email= (EditText) findViewById(R.id.edit_email);
        btn_generatepdf= (Button) findViewById(R.id.btn_generatepdf);

        //onclick triggers when button is clicked
        btn_generatepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeneratePDF();
            }
        });
    }


    String filename;
    String name;
    String course;
    String address;
    String phone;
    String email ;

    public void GeneratePDF()
    {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int sec = c.get(Calendar.SECOND);

         filename = edit_name.getText().toString()+"0"+hour+"0"+sec;
         name=edit_name.getText().toString();
         course = edit_course.getText().toString();
         address = edit_address.getText().toString();
         phone = edit_phone.getText().toString();
         email = edit_email.getText().toString();

        if (write()) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
            callpdf();

        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();

        }
    }


    private void callpdf(){
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(filepath), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    File filepath;
    public Boolean write() {
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//            String fpath = "/sdcard/" + fname + ".pdf";
            String fpath = filename + ".pdf";
             filepath = new File(dir,fpath);

            if (!filepath.exists()) {
                filepath.createNewFile();
            }

            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(filepath.getAbsoluteFile()));
            document.open();

            document.add(new Paragraph(name));
            document.add(new Paragraph(course));
            document.add(new Paragraph(address));
            document.add(new Paragraph(phone));
            document.add(new Paragraph(email));

            document.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
