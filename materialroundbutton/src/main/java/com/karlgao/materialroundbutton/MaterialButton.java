package com.karlgao.materialroundbutton;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;


/**
 * Created by karl on 22/09/2016.
 */

public class MaterialButton extends FrameLayout {

    RoundedImageView mRoundedImageView;
    TextView mTextView;

    private final float DEFAULT_BORDER_WIDTH = 0;
    private final float DEFAULT_CORNER_RADIUS = 0;
    private final float DEFAULT_TEXT_SIZE = 16;
    private Drawable DEFAULT_BUTTON_COLOR;
    private int DEFAULT_BORDER_COLOR;
    private int DEFAULT_RIPPLE_COLOR;
    private int DEFAULT_TEXT_COLOR;
    private int DEFAULT_BUTTON_TYPE = 1;

    private float border_width;
    private float corner_radius;
    private float button_text_size;
    private Drawable button_color;
    private int border_color;
    private int ripple_color;
    private int button_text_color;
    private String button_text;
    private int button_type;

    private TypedArray a;

    public MaterialButton(Context context) {
        super(context);
            init();
    }

    public MaterialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
            a = context.obtainStyledAttributes(attrs, R.styleable.MaterialButton, 0, 0);
            init();
    }

    public MaterialButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
            a = context.obtainStyledAttributes(attrs, R.styleable.MaterialButton, defStyleAttr, 0);
            init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaterialButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
            a = context.obtainStyledAttributes(attrs, R.styleable.MaterialButton, defStyleAttr, defStyleRes);
            init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        float density = getContext().getResources().getDisplayMetrics().density;
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;

        inflate(getContext(), R.layout.layout_button, this);

        mRoundedImageView = (RoundedImageView) findViewById(R.id.roundedImageView);
        mTextView = (TextView) findViewById(R.id.textView);

        setClickable(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DEFAULT_BUTTON_COLOR = new ColorDrawable(getContext().getResources().getColor(R.color.default_button, null));
            DEFAULT_BORDER_COLOR = getContext().getResources().getColor(R.color.default_button, null);
            DEFAULT_RIPPLE_COLOR = getContext().getResources().getColor(R.color.default_ripple, null);
            DEFAULT_TEXT_COLOR = getContext().getResources().getColor(R.color.default_text, null);
        } else {
            DEFAULT_BUTTON_COLOR = new ColorDrawable(getContext().getResources().getColor(R.color.default_button));
            DEFAULT_BORDER_COLOR = getContext().getResources().getColor(R.color.default_button);
            DEFAULT_RIPPLE_COLOR = getContext().getResources().getColor(R.color.default_ripple);
            DEFAULT_TEXT_COLOR = getContext().getResources().getColor(R.color.default_text);
        }


        try {
            button_color = a.getDrawable(R.styleable.MaterialButton_mb_buttonColor);
            border_color = a.getColor(R.styleable.MaterialButton_mb_borderColor, DEFAULT_BORDER_COLOR);
            border_width = a.getDimension(R.styleable.MaterialButton_mb_borderWidth, DEFAULT_BORDER_WIDTH * density);
            corner_radius = a.getDimension(R.styleable.MaterialButton_mb_cornerRadius, DEFAULT_CORNER_RADIUS * density);

            button_text = a.getString(R.styleable.MaterialButton_mb_text);
            button_text_color = a.getColor(R.styleable.MaterialButton_mb_textColor, DEFAULT_TEXT_COLOR);
            button_text_size = a.getDimension(R.styleable.MaterialButton_mb_textSize, DEFAULT_TEXT_SIZE * scaledDensity);

            ripple_color = a.getColor(R.styleable.MaterialButton_mb_rippleColor, DEFAULT_RIPPLE_COLOR);
            button_type = a.getInt(R.styleable.MaterialButton_mb_buttonType, DEFAULT_BUTTON_TYPE);
        } finally {
            a.recycle();
        }

        if (button_color != null) {
            mRoundedImageView.setImageDrawable(button_color);
        } else {
            mRoundedImageView.setImageDrawable(DEFAULT_BUTTON_COLOR);
        }

        mRoundedImageView.setBorderColor(border_color);
        mRoundedImageView.setBorderWidth(border_width);
        mRoundedImageView.setCornerRadius(corner_radius);
        mRoundedImageView.setOval(false);

        if(button_text != null && !button_text.isEmpty()) {
            mTextView.setText(button_text);
        }
        mTextView.setTextColor(button_text_color);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, button_text_size);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            switch (button_type) {
                case 1:
                    setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), R.animator.raised_button_state_animator));
                    break;
                case 2:
                    setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), R.animator.fab_state_animator));
                    break;
            }
        }

        setCustomForeground();

        setCustomBackground();

    }

    public RoundedImageView getButtonImageView() {
        return mRoundedImageView;
    }

    public TextView getButtonTextView() {
        return mTextView;
    }

    public void setButtonColor(int color) {
        button_color = new ColorDrawable(color);
        mRoundedImageView.setImageDrawable(button_color);
    }

    public void setBorderColor(int color) {
        border_color = color;
        mRoundedImageView.setBorderColor(border_color);
    }

    public void setBorderWidth(float width) {
        border_width = width;
        mRoundedImageView.setBorderWidth(border_width);
    }

    public void setCornerRadius(float radius) {
        corner_radius = radius;
        mRoundedImageView.setCornerRadius(corner_radius);
        setCustomForeground();
        setCustomBackground();
    }

    public void setText(String text) {
        button_text = text;
        mTextView.setText(button_text);
    }

    public void setTextColor(int color) {
        button_text_color = color;
        mTextView.setTextColor(button_text_color);
    }

    public void setTextSize(float size) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        button_text_size = size * scaledDensity;
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setRippleColor(int color) {
        ripple_color = color;
        setCustomForeground();
    }

    private void setCustomForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShapeDrawable sd = new ShapeDrawable();
            if(corner_radius != 0) {
                float[] radii = new float[8];
                for (int i = 0; i < radii.length; i++) {
                    radii[i] = corner_radius;
                }
                sd.setShape(new RoundRectShape(radii, null, null));
            } else {
                sd.setShape(new RectShape());
            }
            sd.getPaint().setColor(Color.parseColor("#000000"));
            RippleDrawable rd = new RippleDrawable(ColorStateList.valueOf(ripple_color), null, sd);
            setForeground(rd);
        } else {
            ShapeDrawable sd = new ShapeDrawable();
            if(corner_radius != 0) {
                float[] radii = new float[8];
                for (int i = 0; i < radii.length; i++) {
                    radii[i] = corner_radius;
                }
                sd.setShape(new RoundRectShape(radii, null, null));
            } else {
                sd.setShape(new RectShape());
            }
            sd.getPaint().setColor(ripple_color);
            StateListDrawable sld = new StateListDrawable();
            sld.addState(new int[]{android.R.attr.state_pressed}, sd);
            sld.addState(new int[]{}, null);
            setForeground(sld);
        }
    }

    private void setCustomBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShapeDrawable sd = new ShapeDrawable();
            if(corner_radius != 0) {
                float[] radii = new float[8];
                for (int i = 0; i < radii.length; i++) {
                    radii[i] = corner_radius;
                }
                sd.setShape(new RoundRectShape(radii, null, null));
            } else {
                sd.setShape(new RectShape());
            }
            sd.getPaint().setColor(Color.parseColor("#ffffff"));
            setBackground(sd);
        }
    }
}
