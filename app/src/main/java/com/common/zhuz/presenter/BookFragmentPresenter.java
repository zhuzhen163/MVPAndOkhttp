package com.common.zhuz.presenter;

import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.fragment.BookFragment;
import com.common.zhuz.model.BookFragmentModel;


/**
 * Created by zhuzhen
 */

public class BookFragmentPresenter extends BasePresenter <BookFragmentModel,BookFragment>{

    @Override
    public BookFragmentModel loadModel() {
        return new BookFragmentModel();
    }
}
