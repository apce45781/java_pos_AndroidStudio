package com.menu.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Operating extends AppCompatActivity {

    private TextView company_name , show_quantity;
    private Button add_seat , remove_seat;
    private ListView list_scroll;

    private int unused_seat = 5;
    private int seat_in_use = 0;

    private final int add_seat_number = 0;
    private final int delete_seat_number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operating);

        findviewbyid();

        company_name.setText("Hello " + getIntent().getStringExtra("company_name"));
        show_quantity.setText("可使用座位 : " + (unused_seat + seat_in_use) + "\n未使用 : " + unused_seat + "\n使用中 : " + seat_in_use);

        List<String> seat_quantity = new ArrayList();
        for(int i = 0 ; i < 5 ; i ++){
            seat_quantity.add("seat" + (i + 1));
        }
        list_scroll.setAdapter(new ArrayAdapter<String>(Operating.this , android.R.layout.test_list_item , seat_quantity));

        add_seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_seat(seat_quantity);
            }
        });

        remove_seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_seat(seat_quantity);
            }
        });

        list_scroll.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list_scroll(position , id);
            }
        });
    }

    public void add_seat(List<String> seat_quantity){
        if(seat_quantity.size() == 0){
            remove_seat.setVisibility(View.VISIBLE);
        }
        seat_quantity.add("seat" + (seat_quantity.size() + 1));
        list_scroll.setAdapter(new ArrayAdapter<String>(Operating.this , android.R.layout.test_list_item , seat_quantity));
        seat_quantity_change(add_seat_number);
    }

    public void remove_seat(List<String> seat_quantity){
        if(seat_quantity.size() == 1){
            remove_seat.setVisibility(View.INVISIBLE);
        }
        seat_quantity.remove(seat_quantity.size() - 1);
        list_scroll.setAdapter(new ArrayAdapter<String>(Operating.this , android.R.layout.test_list_item , seat_quantity));
        seat_quantity_change(delete_seat_number);
    }

    public void list_scroll(int position , long id){
        company_name.setText(position + id + "");
    }

    public void seat_quantity_change(int change_quantity){
        if(change_quantity == add_seat_number){
            unused_seat ++;
        }else if(change_quantity == delete_seat_number){
            unused_seat --;
        }else{
            unused_seat --;
            seat_in_use ++;
        }
        show_quantity.setText("可使用座位 : " + (unused_seat + seat_in_use) + "\n未使用 : " + unused_seat + "\n使用中 : " + seat_in_use);
    }

    public void findviewbyid(){
        company_name = findViewById(R.id.text_company_name);
        show_quantity = findViewById(R.id.text_show_quantity);
        list_scroll = findViewById(R.id.list_scroll);
        add_seat = findViewById(R.id.button_add_seat);
        remove_seat = findViewById(R.id.button_remove_seat);
    }

    public int getUnused_seat() {
        return unused_seat;
    }

    public void setUnused_seat(int unused_seat) {
        this.unused_seat = unused_seat;
    }

    public int getSeat_in_use() {
        return seat_in_use;
    }

    public void setSeat_in_use(int seat_in_use) {
        this.seat_in_use = seat_in_use;
    }
}