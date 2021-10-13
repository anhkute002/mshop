package my.ecommerceapp.ecommerceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import my.ecommerceapp.ecommerceapp.CategoryAdapter;
import my.ecommerceapp.ecommerceapp.NewProductAdapter;
import my.ecommerceapp.ecommerceapp.PopularAdapter;
import my.ecommerceapp.ecommerceapp.R;
import my.ecommerceapp.ecommerceapp.ShowAllAdapter;
import my.ecommerceapp.ecommerceapp.activities.ShowAllActivity;
import my.ecommerceapp.ecommerceapp.models.CategoryModel;
import my.ecommerceapp.ecommerceapp.models.NewProductsModel;
import my.ecommerceapp.ecommerceapp.models.PopularProductsModel;
import my.ecommerceapp.ecommerceapp.models.ShowAllModel;


public class HomeFragment extends Fragment {


    TextView catShowAll, popularShowAll, newProductShowAll;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;

    RecyclerView catRecyclerview , newProductRecyclerview , popularRecyclerview;

    //search
    EditText search_box;
    private List<ShowAllModel> showAllModelList;
    private RecyclerView recyclerViewSearch;
    private ShowAllAdapter showAllAdapter;

    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    NewProductAdapter newProductAdapter;
    List<NewProductsModel> newProductsModelList;

    PopularAdapter popularAdapter;
    List<PopularProductsModel> popularProductsModelList;

    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        progressDialog = new ProgressDialog(getActivity());

        catRecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        popularRecyclerview = root.findViewById(R.id.popular_rec);

        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);


        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });






        db = FirebaseFirestore.getInstance();
        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1,"Discount On Shoes Items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount On Perfume", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"70% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        progressDialog.setTitle("Welcome to my shop");
        progressDialog.setMessage("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList = new ArrayList<>();
        newProductAdapter = new NewProductAdapter(getContext(),newProductsModelList);
        newProductRecyclerview.setAdapter(newProductAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsModelList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getContext(),popularProductsModelList);
        popularRecyclerview.setAdapter(popularAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        //search
        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(getContext(),showAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(showAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    showAllModelList.clear();
                    showAllAdapter.notifyDataSetChanged();
                }else {
                    searchProduct(s.toString());
                }
            }
        });

        return  root;




    }

    private void searchProduct(String type) {

        if(!type.isEmpty()){
            db.collection("AllProducts").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful() && task.getResult()!=null){
                                showAllModelList.clear();
                                showAllAdapter.notifyDataSetChanged();
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}