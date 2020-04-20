package com.example.ltbasemodule.basemvp;

import com.example.ltbasemodule.base.BaseFragment;
import com.example.ltbasemodule.base.BasePresenter;
import com.example.ltbasemodule.base.BaseView;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public abstract class BaseMvpFragment<T extends BasePresenter>  extends BaseFragment implements BaseView {

    protected T mPresenter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }
}
