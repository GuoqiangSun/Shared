package cn.com.startai.sharedlib.app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: Guoqiang_Sun
 * date : 2018/4/16 0016
 * desc :
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View mRootView;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflateView();
        }
        return mRootView;
    }


    protected abstract View inflateView();

    @Override
    public void onDestroyView() {
        if (mRootView != null) {
            ViewGroup vg = (ViewGroup) mRootView.getParent();
            if (vg != null) {
                vg.removeView(mRootView);
            }
            mRootView = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
