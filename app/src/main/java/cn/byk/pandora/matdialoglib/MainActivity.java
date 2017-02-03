package cn.byk.pandora.matdialoglib;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.byk.pandora.matdialog.MatDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalMatDialog();
            }
        });

        findViewById(R.id.btn_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog();
            }
        });

        findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionDialog();
            }
        });
    }

    private void showNormalMatDialog() {
        MatDialog.with(this)
                 .themeColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null))
                 .title("Hello")
                 .content("Life is...")
                 .positiveTxt("Sugar")
                 .detailTxt("https://github.com/bluesofy/MatDialog")
                 .icon(R.mipmap.ic_launcher)
                 .show();
    }

    private void showLoadingDialog() {
        MatDialog.with(this)
                 .themeColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null))
                 .ableProgressMode(true)
                 .content("Tea Time, Pls Wait...")
                 .cancelWithParent(true)
                 .show();
    }

    private void showActionDialog() {
        MatDialog.with(this)
                 .themeColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null))
                 .title("Hello")
                 .content("Life is...")
                 .positiveTxt("Sugar")
                 .negativeTxt("Apple")
                 .detailTxt("<u>jump to...</u>")
                 .cancelOnTouchOutside(false)
                 .setOnBtnClickListener(new MatDialog.BtnClickCallback() {
                     @Override
                     public void onPositive(MatDialog dialog) {
                         super.onPositive(dialog);
                         showToast("Click Sugar");
                     }

                     @Override
                     public void onNegative(MatDialog dialog) {
                         super.onNegative(dialog);
                         showToast("Click Apple");
                     }

                     @Override
                     public void onDetailLink(MatDialog dialog) {
                         super.onDetailLink(dialog);
                         showToast("U Jump, I Jump");
                     }
                 })
                 .autoDismiss(true)
                 .show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
             .show();
    }

}
