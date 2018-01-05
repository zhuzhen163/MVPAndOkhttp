package com.common.zhuz.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;

import com.common.zhuz.R;
import com.common.zhuz.application.ZApplication;
import com.common.zhuz.tools.CommonUtils;
import com.common.zhuz.tools.LogUtil;
import com.common.zhuz.tools.ToastUtil;
import com.common.zhuz.widget.LoadingDialog;
import com.common.zhuz.widget.PublicTitleView;
import com.common.zhuz.widget.statusbar.StatusBarUtil;

import java.lang.reflect.Method;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zhuzhen on 2017/11/2.
 */

public abstract class BaseActivity <P extends BasePresenter>  extends FragmentActivity implements BaseView,View.OnClickListener{
    protected View view;
    protected P mPresenter;
    public Context mContext;
    private LinearLayout content;
    private LoadingDialog loadingDialog;
    private int id = -1;
    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private Unbinder unbinder;
    public PublicTitleView base_title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_base);
        initApp();
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);
    }
    public void initApp(){
        baseSetContentView();
        mContext = this;
        ZApplication.getAppContext().addActivity(this);
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mContext);
        }
        mPresenter = loadPresenter();
        initCommonData();
        initView();
        initListener();
        initData();
    }
    public void baseSetContentView() {
        content = (LinearLayout) findViewById(R.id.content);
        base_title = (PublicTitleView) findViewById(R.id.base_title);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(getLayoutId(),content,true);
        unbinder= ButterKnife.bind(this,view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    protected abstract P loadPresenter();

    private void initCommonData() {
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void otherViewClick(View view);

    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    /**
     * 点击的事件的统一的处理
     * 防双击，避免多次查询或插入
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(isOpenNoDoubleClick()){
            long currentTime = Calendar.getInstance().getTimeInMillis();
            int mId = view.getId();
            if (id != mId) {
                id = mId;
                lastClickTime = currentTime;
                otherViewClick(view);
                return;
            }
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                otherViewClick(view);
            }
        }else{
            otherViewClick(view);
        }
    }

    protected boolean isOpenNoDoubleClick(){
        return true;
    }


    public void setBaseTitleState(int state) {
        base_title.setVisibility(state);
    }

    public void setBaseBackListener(View.OnClickListener clickListener) {
        base_title.setBackListener(clickListener);
    }

    public void setBackState(int state){
        base_title.setBackState(state);
    }

    public void setTitleName(String name){
        base_title.setTitleName(name);
    }

    public void setRightIcon(int state){
        base_title.setRightIcon(state);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        //解除绑定
        if (unbinder!=null){
            unbinder.unbind();
        }
        ZApplication.getAppContext().removeActivity(this);
    }
    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        ToastUtil.showToast(str);
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {

        ToastUtil.showToast(contentId);
    }

    /**
     * @param str 日志的处理
     */
    public void LogE(String str) {
        LogUtil.i(this.getClass().getName(), str);
    }

    /**
     * 关闭辅助功能，针对4.2.1和4.2.2 崩溃问题 java.lang.NullPointerException at
     * android.webkit.AccessibilityInjector$TextToSpeechWrapper$1.onInit(
     * AccessibilityInjector.java: 753) ... ... at
     * android.webkit.CallbackProxy.handleMessage(CallbackProxy.java:321)
     */
    private void disableAccessibility() {
        /*
          * 4.2
		 * (Build.VERSION_CODES.JELLY_BEAN_MR1)
			*/
        if (Build.VERSION.SDK_INT == 17) {
            try {
                AccessibilityManager am = (AccessibilityManager) mContext
                        .getSystemService(Context.ACCESSIBILITY_SERVICE);
                if (!am.isEnabled()) {
                    return;
                }
                Method set = am.getClass().getDeclaredMethod("setState", int.class);
                set.setAccessible(true);
                set.invoke(am, 0);
            } catch (Exception e) {
            }
        }
    }

    /**
     * loading显示与隐藏
     *
     * @param is_show
     */
    public void setShowLoading(boolean is_show) {
        if (is_show) {
            if (loadingDialog != null) {
                loadingDialog.show();
            }
        } else {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        }
    }
}
