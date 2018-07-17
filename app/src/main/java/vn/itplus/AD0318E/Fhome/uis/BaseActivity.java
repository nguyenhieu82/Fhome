package vn.itplus.AD0318E.Fhome.uis;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(injectLayout());

        context = BaseActivity.this;

        initViews();
        initVariables();
    }

    /**
     * dung de xac dinh layout de hien thi cho activity
     *
     * @return
     */
    protected abstract int injectLayout();

    /**
     * Dung de khoi tao cac views trong activity
     */
    protected abstract void initViews();

    /**
     * Khoi tao cac bien, cac tham so trong activity
     */
    protected abstract void initVariables();

    /**
     * Hien thi dialog khi load du lieu
     */
    public void showProgressDialog() {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(context);
                dialog.show();
                dialog.setCancelable(false);
            }
        } catch (Exception e) {
            Log.e("TAG", "Loi....");
        }
    }

    /**
     * Dong dialog khi load xong
     */
    public void closeProgressDialog() {
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }
        } catch (Exception e) {
            Log.e("TAG", "Loi....");
        }
    }
}
