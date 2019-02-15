package cn.com.startai.sharedlib.app.view;

import android.view.View;

import cn.com.startai.sharedlib.R;
import cn.com.startai.sharedlib.app.global.CustomManager;
import cn.com.swain.baselib.log.Tlog;

/**
 * author: Guoqiang_Sun
 * date : 2018/9/25 0025
 * desc :
 */
public class SharedGuideFragment extends BaseFragment {

    @Override
    protected View inflateView() {
        Tlog.v(" GuideFragment inflateView() ");

        if (CustomManager.getInstance().isRuioo()) {
            return View.inflate(getActivity(), R.layout.ruioo_framgment_guide,
                    null);

        } else if (CustomManager.getInstance().isSynerMax()) {
            return View.inflate(getActivity(), R.layout.synermax_framgment_guide,
                    null);
        }

        return View.inflate(getActivity(), R.layout.shared_framgment_guide,
                null);
    }

}
