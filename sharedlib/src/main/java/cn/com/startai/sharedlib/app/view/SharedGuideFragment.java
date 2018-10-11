package cn.com.startai.sharedlib.app.view;

import android.view.View;

import cn.com.shared.weblib.fragment.GuideFragment;
import cn.com.startai.sharedlib.R;
import cn.com.swain169.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/9/25 0025
 * desc :
 */
public class SharedGuideFragment extends GuideFragment {

    @Override
    protected View inflateView() {
        Tlog.v(" GuideFragment inflateView() ");
        return View.inflate(getActivity(), R.layout.ruioo_framgment_guide,
                null);
    }

}
