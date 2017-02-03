package cn.byk.pandora.matdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Byk on 2017/1/20.
 * <p>
 * Material Style Dialog
 */
public class MatDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = MatDialog.class.getSimpleName();

    public static final int TAG_NEGATIVE = 1;
    public static final int TAG_NEUTRAL = 2;
    public static final int TAG_POSITIVE = 3;
    public static final int TAG_DETAIL = 4;

    protected View mContentView;
    protected Builder mBuilder;

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getCustomView() {
        return mBuilder.customView;
    }

    protected MatDialog(Builder builder) {
        super(builder.context, R.style.dialog_transparent);

        mBuilder = builder;
        mContentView = LayoutInflater.from(builder.context)
                                     .inflate(MatConfig.getContentViewId(-1), null);

        MatConfig.init(this);
    }

    protected void onPreClose() {
        if (mBuilder.btnCallback != null) {
            mBuilder.btnCallback.onPreClose(this);
        }
    }

    @Override
    public void onClick(View v) {
        int tag = Integer.parseInt(v.getTag()
                                    .toString());

        if (mBuilder.btnCallback != null) {
            // click counter
            mBuilder.btnCallback.onClick(this);

            switch (tag) {
                case TAG_NEGATIVE:
                    mBuilder.btnCallback.onNegative(this);
                    break;
                case TAG_POSITIVE:
                    mBuilder.btnCallback.onPositive(this);
                    break;
                case TAG_NEUTRAL:
                    mBuilder.btnCallback.onNeutral(this);
                    break;
                case TAG_DETAIL:
                    mBuilder.btnCallback.onDetailLink(this);
                    break;
                default:
                    break;
            }
        }

        if (tag != TAG_DETAIL && mBuilder.autoDismiss) {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    @UiThread
    public void show() {
        try {
            super.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e(TAG,
                  "Bad window token, you cannot show a dialog before an Activity is created or after it's hidden.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        protected Context context;

        protected int themeColor;
        protected int heightWeight;
        protected Drawable icon;
        protected String title = "";
        protected String content = "";
        protected String detailTxt;
        protected String positiveTxt;
        protected String neutralTxt;
        protected String negativeTxt;

        protected View customView;

        protected boolean ableProgressMode = false;
        protected boolean autoDismiss = true;
        protected boolean cancelable = true;
        protected boolean cancelOnTouchOutside = true;
        protected boolean cancelWithParent;

        protected boolean hideTop;
        protected boolean hideBottom;

        protected BtnClickCallback btnCallback;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setOnBtnClickListener(BtnClickCallback callback) {
            this.btnCallback = callback;
            return this;
        }

        public Builder customView(@LayoutRes int id) {
            this.customView = LayoutInflater.from(context)
                                            .inflate(id, null);
            return this;
        }

        public Builder customView(@NonNull View view) {
            this.customView = view;
            return this;
        }

        public Builder title(@StringRes int id) {
            title(context.getString(id));
            return this;
        }

        public Builder title(@NonNull String txt) {
            this.title = txt;
            return this;
        }

        public Builder content(@StringRes int id) {
            content(context.getString(id));
            return this;
        }

        public Builder content(@NonNull String txt) {
            this.content = txt;
            return this;
        }

        public Builder detailTxt(@StringRes int id) {
            detailTxt(context.getString(id));
            return this;
        }

        public Builder detailTxt(@NonNull String txt) {
            this.detailTxt = txt;
            return this;
        }

        public Builder positiveTxt(@StringRes int id) {
            positiveTxt(context.getString(id));
            return this;
        }

        public Builder positiveTxt(@NonNull String txt) {
            this.positiveTxt = txt;
            return this;
        }

        public Builder neutralTxt(@StringRes int id) {
            neutralTxt(context.getString(id));
            return this;
        }

        public Builder neutralTxt(@NonNull String txt) {
            this.neutralTxt = txt;
            return this;
        }

        public Builder negativeTxt(@StringRes int id) {
            negativeTxt(context.getString(id));
            return this;
        }

        public Builder negativeTxt(@NonNull String txt) {
            this.negativeTxt = txt;
            return this;
        }

        public Builder icon(@NonNull Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public Builder icon(@DrawableRes int id) {
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), id, null);
            if (drawable != null) {
                icon(drawable);
            }
            return this;
        }

        public Builder heightWeight(int value) {
            this.heightWeight = value;
            return this;
        }

        public Builder themeColor(@ColorInt int color) {
            this.themeColor = color;
            return this;
        }

        public Builder hideTop(boolean enable) {
            this.hideTop = enable;
            return this;
        }

        public Builder hideBottom(boolean enable) {
            this.hideBottom = enable;
            return this;
        }

        public Builder autoDismiss(boolean enable) {
            this.autoDismiss = enable;
            return this;
        }

        public Builder ableProgressMode(boolean enable) {
            this.ableProgressMode = enable;
            return this;
        }

        public Builder cancelable(boolean enable) {
            this.cancelable = enable;
            return this;
        }

        public Builder cancelOnTouchOutside(boolean enable) {
            this.cancelOnTouchOutside = enable;
            return this;
        }

        public Builder cancelWithParent(boolean enable) {
            this.cancelWithParent = enable;
            return this;
        }

        @UiThread
        public MatDialog build() {
            return new MatDialog(this);
        }

        @UiThread
        public MatDialog show() {
            MatDialog dialog = build();
            dialog.show();
            return dialog;
        }

    }

    public static abstract class BtnClickCallback {

        public void onClick(MatDialog dialog) {
        }

        public void onPositive(MatDialog dialog) {
        }

        public void onNeutral(MatDialog dialog) {
        }

        public void onNegative(MatDialog dialog) {
        }

        public void onDetailLink(MatDialog dialog) {
        }

        public void onPreClose(MatDialog dialog) {
        }

        public BtnClickCallback() {
            super();
        }

    }
}
