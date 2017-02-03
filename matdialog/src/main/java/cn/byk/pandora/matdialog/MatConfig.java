package cn.byk.pandora.matdialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Point;
import android.support.annotation.UiThread;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.byk.pandora.matdialog.progress.ProgressWheel;

public class MatConfig {

    public static final int TYPE_BASE = 1;
    public static final int TYPE_PROGRESS = 2;

    public static int getContentViewId(int dialogType) {
        if (dialogType == TYPE_BASE) {
            return R.layout.view_material_dialog;
        }

        if (dialogType == TYPE_PROGRESS) {
            return R.layout.view_material_dialog_progress;
        }

        return R.layout.view_material_dialog;
    }

    @UiThread
    public static void init(final MatDialog dialog) {
        final MatDialog.Builder builder = dialog.getBuilder();
        final Context context = builder.context;
        View view = dialog.getContentView();

        dialog.setCancelable(builder.cancelable);
        dialog.setCanceledOnTouchOutside(!builder.cancelWithParent && builder.cancelOnTouchOutside);

        ScrollView scMsg = (ScrollView) view.findViewById(R.id.msg);
        RelativeLayout middleView = (RelativeLayout) view.findViewById(R.id.content);
        View bottomView = view.findViewById(R.id.bottom);
        View topView = view.findViewById(R.id.top);

        // progress mode
        if (builder.ableProgressMode) {
            builder.customView(getContentViewId(TYPE_PROGRESS));
            builder.hideTop(true);
            builder.hideBottom(true);

            TextView tvProgress = (TextView) builder.customView.findViewById(R.id.loading);
            if (TextUtils.isEmpty(builder.content)) {
                tvProgress.setText(R.string.txt_loading);
            } else {
                tvProgress.setText(builder.content);
            }

            if (builder.themeColor != 0) {
                ProgressWheel progress = (ProgressWheel) builder.customView.findViewById(R.id.progress);
                progress.setBarColor(builder.themeColor);
            }
        }

        if (builder.hideTop) {
            topView.setVisibility(View.GONE);
        } else {
            TextView tvTitle = (TextView) view.findViewById(R.id.title);
            tvTitle.setText(builder.title);
        }

        ImageView ivIcon = (ImageView) view.findViewById(R.id.icon);
        if (builder.icon != null) {
            ivIcon.setImageDrawable(builder.icon);
            ivIcon.setVisibility(View.VISIBLE);
        }

        // custom view
        if (builder.customView != null) {
            scMsg.setVisibility(View.GONE);
            middleView.addView(builder.customView);
        } else {
            TextView tvContent = (TextView) view.findViewById(R.id.message);
            tvContent.setText(builder.content);

            if (!TextUtils.isEmpty(builder.detailTxt)) {
                TextView detailLink = (TextView) view.findViewById(R.id.link);
                detailLink.setTag(MatDialog.TAG_DETAIL);
                detailLink.setText(Html.fromHtml(builder.detailTxt));
                detailLink.setOnClickListener(dialog);
                detailLink.setVisibility(View.VISIBLE);
            }
        }

        if (builder.hideBottom) {
            bottomView.setVisibility(View.GONE);
        } else {
            Button btnPositive = (Button) view.findViewById(R.id.btn_positive);
            Button btnNegative = (Button) view.findViewById(R.id.btn_negative);
            Button btnNeutral = (Button) view.findViewById(R.id.btn_neutral);

            if (builder.themeColor != 0) {
                btnPositive.setTextColor(builder.themeColor);
                btnNegative.setTextColor(builder.themeColor);
                btnNeutral.setTextColor(builder.themeColor);
            }

            if (!TextUtils.isEmpty(builder.positiveTxt)) {
                btnPositive.setText(builder.positiveTxt);
            }
            btnPositive.setTag(MatDialog.TAG_POSITIVE);
            btnPositive.setOnClickListener(dialog);

            if (!TextUtils.isEmpty(builder.negativeTxt)) {
                btnNegative.setTag(MatDialog.TAG_NEGATIVE);
                btnNegative.setText(builder.negativeTxt);
                btnNegative.setOnClickListener(dialog);
                btnNegative.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(builder.neutralTxt)) {
                btnNeutral.setTag(MatDialog.TAG_NEUTRAL);
                btnNeutral.setText(builder.neutralTxt);
                btnNeutral.setOnClickListener(dialog);
                btnNeutral.setVisibility(View.VISIBLE);
            }
        }

        Point wh = getScreenWidth(context);
        int width = wh.x / 10 * 9;
        int height = LayoutParams.WRAP_CONTENT;
        if (builder.heightWeight > 0) {
            height = wh.y / 10 * builder.heightWeight;
        }
        dialog.setContentView(view, new LayoutParams(width, height));

        dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    dialog.onPreClose();
                    if (builder.cancelWithParent && context instanceof Activity) {
                        dialogInterface.dismiss();
                        ((Activity) context).finish();
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
    }

    public static Point getScreenWidth(Context context) {
        Point point = new Point();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay()
                     .getMetrics(dm);
        point.set(dm.widthPixels, dm.heightPixels);
        return point;
    }

}
