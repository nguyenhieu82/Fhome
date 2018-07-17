package vn.itplus.AD0318E.Fhome.uis.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import vn.itplus.AD0318E.Fhome.R;
import vn.itplus.AD0318E.Fhome.adapter.ContactAdapter;
import vn.itplus.AD0318E.Fhome.model.Contact;
import vn.itplus.AD0318E.Fhome.model.ContactResponse;
import vn.itplus.AD0318E.Fhome.rest.RestClient;
import vn.itplus.AD0318E.Fhome.uis.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private RecyclerView recyclerContact;
    private ContactAdapter adapter;
    private List<Contact> contacts;

    @Override
    protected int injectLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        //khoi tao view
        recyclerContact = findViewById(R.id.recyclerContact);
    }

    @Override
    protected void initVariables() {
        contacts = new ArrayList<>();
        adapter = new ContactAdapter(contacts);
        recyclerContact.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerContact.setLayoutManager(manager);

        //thiet lap cac tham so, cac gia tri mac dinh
        getContact();
    }

    private void getContact() {
        //hien thi dialog de thong bao dang tai du lieu
        showProgressDialog();

        Call<ContactResponse> call = RestClient.getRestClient().getContacts();
        call.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                //khi lay ve thanh cong
                List<Contact> list = response.body().getContacts();
                //add mang lay ve vao mang cua recyclerView
                contacts.clear();
                contacts.addAll(list);
                adapter.notifyDataSetChanged();
                //huy dialog quay
                closeProgressDialog();
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                //khi lay ve that bai
                //huy dialog quay
                closeProgressDialog();
                Toast.makeText(MainActivity.this, "Khong the tai du lieu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
