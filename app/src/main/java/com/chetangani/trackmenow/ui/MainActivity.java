package com.chetangani.trackmenow.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chetangani.trackmenow.R;
import com.chetangani.trackmenow.utils.Constants;
import com.chetangani.trackmenow.utils.RecyclerItemClickListener;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements AddLocationDialog.LocationAddedHandler, AdapterView.OnItemClickListener {
    Firebase mBaseRef, mLocationRef;
    /*ListView listView;
    FirebaseListAdapter adapter;*/
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<String, MessageViewHolder> recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Delete Location").setMessage("Are you sure you want to delete this Location")
                                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i1) {
                                Firebase itemRef = recyclerAdapter.getRef(position);
                                itemRef.removeValue();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create().show();
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


        /*listView = (ListView) findViewById(R.id.listview);

        listView.setOnItemClickListener(this);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLocationDialog locationDialog = new AddLocationDialog();
                locationDialog.show(getSupportFragmentManager(), "location_dialog");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mBaseRef = new Firebase(Constants.BASE_URL);
        mLocationRef = new Firebase(Constants.LOCATION_URL);

        //region listview adapter
        /*adapter = new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, mLocationRef) {
            @Override
            protected void populateView(View view, String s, int i) {
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(s);
            }
        };
        listView.setAdapter(adapter);*/
        //endregion

        //region recycleradapter
        recyclerAdapter = new FirebaseRecyclerAdapter<String, MessageViewHolder>(
                String.class,
                android.R.layout.two_line_list_item,
                MessageViewHolder.class,
                mLocationRef) {
            @Override
            protected void populateViewHolder(MessageViewHolder messageViewHolder, String s, final int i) {
                messageViewHolder.textView.setText(s);
                messageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Delete Location").setMessage("Are you sure you want to delete this Location")
                                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i1) {
                                Firebase itemRef = recyclerAdapter.getRef(i);
                                itemRef.removeValue();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create().show();
                    }
                });
            }
        };
        recyclerView.setAdapter(recyclerAdapter);
        //endregion
    }

    @Override
    public void locationadded(String location) {
        mLocationRef.push().setValue(location);
    }

    //region OnClick for ListView
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Location").setMessage("Are you sure you want to delete this Location")
                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i1) {
                Firebase itemRef = recyclerAdapter.getRef(i);
                itemRef.removeValue();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    //endregion

    //region RecyclerView View Holder
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View mView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public interface DeleteLocation {
            public void onLocationDelete(int pos);
        }
    }
    //endregion
}
