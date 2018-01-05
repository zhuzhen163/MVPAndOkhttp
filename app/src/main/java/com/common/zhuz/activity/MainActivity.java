package com.common.zhuz.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.common.zhuz.R;
import com.common.zhuz.adapter.ViewPagerAdapter;
import com.common.zhuz.application.ZApplication;
import com.common.zhuz.base.BaseActivity;
import com.common.zhuz.entity.RaidersDataEntity;
import com.common.zhuz.fragment.BookFragment;
import com.common.zhuz.fragment.FragmentOne;
import com.common.zhuz.fragment.MoviesFragment;
import com.common.zhuz.fragment.WelfareFragment;
import com.common.zhuz.presenter.MainPresenter;
import com.common.zhuz.tools.ToastUtil;
import com.common.zhuz.view.MainModelView;
import com.common.zhuz.widget.NoScrollViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.os.Process.killProcess;

@RuntimePermissions
public class MainActivity extends BaseActivity<MainPresenter> implements MainModelView {

    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.rb_first)
    RadioButton rb_first;
    @BindView(R.id.rb_two)
    RadioButton rb_two;
    @BindView(R.id.rb_three)
    RadioButton rb_three;
    @BindView(R.id.rb_four)
    TextView rb_four;
    private List<Fragment> viewpagerFragments;
    private FragmentOne fragmentOne;
    private WelfareFragment welfareFragment;
    private BookFragment bookFragment;
    private MoviesFragment moviesFragment;
    private ViewPagerAdapter adapter;
    private long mExitTime;
    private Dialog mDialog;

    @Override
    protected MainPresenter loadPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        rb_first.setOnClickListener(this);
        rb_two.setOnClickListener(this);
        rb_three.setOnClickListener(this);
        rb_four.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        setBaseTitleState(View.GONE);
        viewpagerFragments = new ArrayList<>();
        viewpager.setNoScroll(true);
        viewpager.setOffscreenPageLimit(3);
        fragmentOne = new FragmentOne();
        welfareFragment = new WelfareFragment();
        bookFragment = new BookFragment();
        moviesFragment = new MoviesFragment();
        viewpagerFragments.clear();
        viewpagerFragments.add(fragmentOne);
        viewpagerFragments.add(welfareFragment);
        viewpagerFragments.add(bookFragment);
        viewpagerFragments.add(moviesFragment);
        adapter = new ViewPagerAdapter(viewpagerFragments, getSupportFragmentManager());
        viewpager.setAdapter(adapter);


        MainActivityPermissionsDispatcher.needStorageWithPermissionCheck(this);
//        mPresenter.postPresener(1,10);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.rb_first:
                viewpager.setCurrentItem(0);
                break;
            case R.id.rb_two:
                viewpager.setCurrentItem(1);
                break;
            case R.id.rb_three:
                viewpager.setCurrentItem(2);
                break;
            case R.id.rb_four:
                viewpager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void showLoading() {
        setShowLoading(true);
    }
    @Override
    public void hideLoading() {
        setShowLoading(false);
    }
    /**
     * post请求测试
     * @param entity
     */
    @Override
    public void postceshi(RaidersDataEntity entity) {
        ToastUtil.showToast("post测试成功");
    }
    @Override
    public void failPostceshi(String msg) {
        ToastUtil.showToast("post测试失败");
    }

    @Override
    public void showUpdate(final String version) {
        if (mDialog == null)
            mDialog = new AlertDialog.Builder(this)
                    .setTitle("检测到有新版本")
                    .setMessage("当前版本:"+version)
                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.downApk(MainActivity.this);
                        }
                    })
                    .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.setIgnore(version);
                        }
                    })
                    .create();

        //重写这俩个方法，一般是强制更新不能取消弹窗
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK && mDialog != null && mDialog.isShowing();
            }
        });

        mDialog.show();
    }

    @Override
    public void showProgress(int progress) {

    }

    @Override
    public void showFail(String msg) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void showComplete(File file) {
        try {
            String authority = getApplicationContext().getPackageName() + ".fileProvider";
            Uri fileUri = FileProvider.getUriForFile(this, authority, file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //7.0以上需要添加临时读取权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            } else {
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }

            startActivity(intent);

            //弹出安装窗口把原程序关闭。
            //避免安装完毕点击打开时没反应
            killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                needStorage();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void needStorage() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(),0);
            String local = pi.versionName;
            mPresenter.checkUpdate(local);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void showRational(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("赋予此应用读写文件权限用于版本更新，是否同意?")
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void deniedStorage() {
        ToastUtil.showToast("请赋予此应用读写文件权限用于版本更新");
    }

    /**
     * 双击退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
                ZApplication.getAppContext().exitApp();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
