package com.aqif.hackernews.screens.storycomments.recycler.adapter;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aqif.hackernews.R;
import com.aqif.hackernews.BR;

import java.util.List;

/**
 * Created by aqifhamid.
 */

public class StoryCommentBinderModel extends BaseObservable
{

    private IOnStoryCommentViewItemClickListener mOnStoryCommentViewItemClickListener;

    private int mId;
    private String mText;
    private String mCommentBy;
    private int mSubCommentsCount;
    private List<Integer> mKidsIds;
    private int mLevel; // Level of comment in the comments tree.
    private String mTag;

    private boolean mIsDeleted;
    private boolean mIsCollapsed;

    /***** Observerable Fields *******/

    public StoryCommentBinderModel(int id,
                                   String text,
                                   String commentBy,
                                   List<Integer> kidsIds,
                                   int level,
                                   String tag,
                                   boolean isDeleted)
    {
        mId = id;
        mText = text == null ? "" : text;
        mCommentBy = commentBy;
        mSubCommentsCount = kidsIds == null ? 0 : kidsIds.size();
        mKidsIds = kidsIds;
        mLevel = level;
        mTag = tag;
        mIsDeleted = isDeleted;
        mIsCollapsed = true;
    }

    public void setOnStoryCommentViewItemClickListener(IOnStoryCommentViewItemClickListener onStoryCommentViewItemClickListener)
    {
        mOnStoryCommentViewItemClickListener = onStoryCommentViewItemClickListener;
    }

    public IOnStoryCommentViewItemClickListener getOnStoryCommentViewItemClickListener()
    {
        return mOnStoryCommentViewItemClickListener;
    }

    public int getId()
    {
        return mId;
    }

    public int getLevel()
    {
        return mLevel;
    }

    public String getText()
    {
        //TODO: different build variant.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return String.valueOf(Html.fromHtml(mText, Html.FROM_HTML_MODE_LEGACY));
        }
        else
        {
            return String.valueOf(Html.fromHtml(mText));
        }
    }

    public int getCollapseArrowVisibility()
    {
        return mKidsIds!=null && mKidsIds.size()>0 ? View.VISIBLE : View.INVISIBLE;
    }

    public boolean isCollapsed()
    {
        return mIsCollapsed;
    }

    public void collapsed(boolean isCollapsed)
    {
        mIsCollapsed = isCollapsed;
        notifyPropertyChanged(BR.storyCommentBinder);
    }

    public String getSubCommentsCount()
    {
        if(mSubCommentsCount==1)
            return String.format("%d comment", mSubCommentsCount);
        else
            return String.format("%d comments", mSubCommentsCount);
    }

    public String getBy()
    {
        return String.format(" by %s", mCommentBy);
    }

    public boolean isDeleted()
    {
        return  mIsDeleted;
    }

    public List<Integer> getKidsIds()
    {
        return mKidsIds;
    }

    public String getTag()
    {
        return mTag;
    }


    public boolean isChildTag(String tag)
    {
        return tag.length() > mTag.length() && tag.substring(0, mTag.length()).equals(mTag);
    }

    /*********** Binding Adapter for border view ***********/
    @BindingAdapter("level")
    public static void changeViewDimensions(final View view, final int level)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = level * view.getContext().getResources().getDimensionPixelSize(R.dimen.story_comment_border_width_unit);
        view.setLayoutParams(params);
    }

    /*********** View click event ***********/
    public void onStoryCommentViewItemClicked()
    {
        if(mOnStoryCommentViewItemClickListener!=null)
        {
            mOnStoryCommentViewItemClickListener.onStoryCommentViewItemClicked(this, mIsCollapsed);
        }
    }

    /*********** Listener for click event ***********/
    public interface IOnStoryCommentViewItemClickListener
    {
        void onStoryCommentViewItemClicked(StoryCommentBinderModel storyCommentBinderModel, boolean isCollapsed);
    }


}
