package com.game.jxj.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.jxj.R;
import com.gm.utils.ToastUtils;


/**
 * 文本输入框
 */
public class TCInputTextMsgDialog extends Dialog {

    boolean blType;

    public void setEditType(boolean blType) {
        if(messageTextView != null){
            if(blType){
                messageTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                messageTextView.setHint("填写您的昵称");
            }else {
                messageTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                messageTextView.setHint("请填写个性签名");

            }
        }
    }

    public interface OnTextSendListener {

       void onTextSend(String msg, boolean tanmuOpen);
    }
    private TextView confirmBtn;
    private LinearLayout mBarrageArea;
    private EditText messageTextView;
    private static final String TAG = TCInputTextMsgDialog.class.getSimpleName();
    private Context mContext;
    private InputMethodManager imm;
    private RelativeLayout rlDlg;
    private int mLastDiff = 0;
    private LinearLayout mConfirmArea;
    private OnTextSendListener mOnTextSendListener;
    private boolean mDanmuOpen = false;
//    private final String reg = "[`~@#$%^&*()-_+=|{}':;,/.<>￥…（）—【】‘；：”“’。，、]";
//    private Pattern pattern = Pattern.compile(reg);

    public TCInputTextMsgDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        setContentView(R.layout.dialog_input_text);

        messageTextView = (EditText) findViewById(R.id.et_input_message);
        messageTextView.setInputType(InputType.TYPE_CLASS_TEXT);
        //修改下划线颜色
        messageTextView.getBackground().setColorFilter(context.getResources().getColor(R.color.transparent), PorterDuff.Mode.CLEAR);
        messageTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               String etText =  messageTextView.getText().toString();
               if(blType){
                   if(etText.length() >=6){
                       ToastUtils.showLong("超出界限了哟,昵称最多输入6个字了,亲");
                   }

               }else{
                   if(etText.length() >=21){
                       ToastUtils.showLong("超出界限了哟,个性签名最多输入21个字了,亲");
                   }

               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmBtn = (TextView) findViewById(R.id.confrim_btn);
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        confirmBtn.setOnClickListener(view -> {
            String msg = messageTextView.getText().toString().trim();
            if (!TextUtils.isEmpty(msg)) {

                mOnTextSendListener.onTextSend(msg, mDanmuOpen);
                imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                messageTextView.setText("");
                dismiss();
            } else {
                Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
            }
            messageTextView.setText(null);
        });

        final Button barrageBtn = (Button) findViewById(R.id.barrage_btn);
        barrageBtn.setOnClickListener(view -> {
//                mDanmuOpen = !mDanmuOpen;
//                if (mDanmuOpen) {
//                    barrageBtn.setBackgroundResource(R.drawable.barrage_slider_on);
//                } else {
//                    barrageBtn.setBackgroundResource(R.drawable.barrage_slider_off);
//                }
        });

        mBarrageArea = (LinearLayout) findViewById(R.id.barrage_area);
        mBarrageArea.setOnClickListener(v -> {
//                mDanmuOpen = !mDanmuOpen;
//                if (mDanmuOpen) {
//                    barrageBtn.setBackgroundResource(R.drawable.barrage_slider_on);
//                } else {
//                    barrageBtn.setBackgroundResource(R.drawable.barrage_slider_off);
//                }
        });

        messageTextView.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case KeyEvent.KEYCODE_ENDCALL:
                case KeyEvent.KEYCODE_ENTER:
                    if (messageTextView.getText().length() > 0) {
//                            mOnTextSendListener.onTextSend("" + messageTextView.getText(), mDanmuOpen);
                        //sendText("" + messageTextView.getText());
                        //imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                        imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
//                            messageTextView.setText("");
                        dismiss();
                    } else {
                        Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
                    }
                    return true;
                case KeyEvent.KEYCODE_BACK:
                    dismiss();
                    return false;
                default:
                    return false;
            }
        });

        mConfirmArea = (LinearLayout) findViewById(R.id.confirm_area);
        mConfirmArea.setOnClickListener(v -> {
            String msg = messageTextView.getText().toString().trim();
            if (!TextUtils.isEmpty(msg)) {
                mOnTextSendListener.onTextSend(msg, mDanmuOpen);
                imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                messageTextView.setText("");
                dismiss();
            } else {
                Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
            }
            messageTextView.setText(null);
        });

        messageTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.d("My test", "onKey " + keyEvent.getCharacters());
                return false;
            }
        });

        rlDlg = (RelativeLayout) findViewById(R.id.rl_outside_view);
        rlDlg.setOnClickListener(v -> {
            if(v.getId() != R.id.rl_inputdlg_view)
                dismiss();
        });

        final LinearLayout rldlgview = (LinearLayout) findViewById(R.id.rl_inputdlg_view);

        rldlgview.addOnLayoutChangeListener((view, i, i1, i2, i3, i4, i5, i6, i7) -> {
            Rect r = new Rect();
            //获取当前界面可视部分
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            //获取屏幕的高度
            int screenHeight =  getWindow().getDecorView().getRootView().getHeight();
            //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
            int heightDifference = screenHeight - r.bottom;

            if (heightDifference <= 0 && mLastDiff > 0){
                //imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                dismiss();
            }
            mLastDiff = heightDifference;
        });
        rldlgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                dismiss();
            }
        });
    }

    public void setmOnTextSendListener(OnTextSendListener onTextSendListener) {
        this.mOnTextSendListener = onTextSendListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0;
    }

    @Override
    public void show() {
        super.show();
    }
}
