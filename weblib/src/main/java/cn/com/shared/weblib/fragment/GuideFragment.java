package cn.com.shared.weblib.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.shared.weblib.R;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/4/13 0013
 * desc :
 */

public abstract class  GuideFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tlog.v(" GuideFragment onCreate() ");
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Tlog.v(" GuideFragment onCreateView() ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Tlog.v(" GuideFragment onDestroyView() ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Tlog.v(" GuideFragment onDestroy() ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tlog.v(" GuideFragment onActivityResult ");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
