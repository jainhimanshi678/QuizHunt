package com.sk.quizhunt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    private GridView gridView;
    public static List<String> oplist = new ArrayList<>();
    public static List<Integer> imglist = new ArrayList<Integer>();
    private FirebaseFirestore firestore;

    private List<itemAdapterTextbook> mList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
      //  gridView=(GridView)container.findViewById(R.id.option);


        
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        gridView=(GridView)view.findViewById(R.id.option);

        List<String> oplist= new ArrayList<>();

        itemAdapterTextbook itemAdapter = new itemAdapterTextbook();
        itemAdapter = new itemAdapterTextbook();
        itemAdapter.setImage(R.drawable.subjects);
        mList.add(itemAdapter);
        itemAdapter = new itemAdapterTextbook();
        itemAdapter.setImage(R.drawable.maths1);
        mList.add(itemAdapter);
        itemAdapter = new itemAdapterTextbook();
        itemAdapter.setImage(R.drawable.science1);
        mList.add(itemAdapter);
        itemAdapter = new itemAdapterTextbook();
        itemAdapter.setImage(R.drawable.english1);
        mList.add(itemAdapter);
        firestore=FirebaseFirestore.getInstance();
        // loadData();
        new Thread()
        {
            public void run()
            {
                loadData();


            }
        }.start();

    }

    private void loadData() {
        oplist.clear();
        firestore.collection("QUIZ").document("DAILY CHALLENGE").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot.exists())
                    {
                        long count = (long)documentSnapshot.get("CATNO");
                        for (int i=1 ; i<= count;i++)
                        {
                            String catName = documentSnapshot.getString("CAT"+ String.valueOf(i));
                            oplist.add(catName);

                        }
                        optionadapter optionadapter = new optionadapter(oplist,mList);
                        gridView.setAdapter(optionadapter);

                    }
                    else {
                        Toast.makeText(getActivity(),"No category",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
