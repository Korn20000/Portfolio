package com.example.korn.hoc.HOC.apache;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;

/**
 * Created by KoRn on 12-03-2018.
 *
 * This class helps with changing the tabs, and updating when "almost" bottom of pictures is reached
 */

public abstract class FragTabPagerCompat extends ActionBarActivity
{
    protected TabHost screenTabs;
    protected ViewPager screenTabsPager;
    protected TabsAdapter screenTabsAdapter;

    protected abstract int getLayoutID();
    protected abstract int getTabHostID();
    protected abstract int getPagerID();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutID());
        screenTabs = (TabHost)findViewById(getTabHostID());
        screenTabs.setup();

        screenTabsPager = (ViewPager)findViewById(getPagerID());

        screenTabsAdapter = new TabsAdapter(this, screenTabs, screenTabsPager);
    }

    public static class TabsAdapter extends FragmentPagerAdapter
            implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener
    {
        public static final int tabHeight = -1;
        
        private int defaultTabHeight = tabHeight;
        
        private final ActionBarActivity context;
        private final TabHost tabHost;
        private final ViewPager viewPager;
        private final ArrayList<infoTabs> tabs = new ArrayList<infoTabs>();
        protected final FragmentManager fragmentManager;

        static final class infoTabs
        {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            infoTabs(String myTags, Class<?> myClass, Bundle myArgs)
            {
                tag = myTags;
                clss = myClass;
                args = myArgs;
            }
        }

        static class tabFactory implements TabHost.TabContentFactory
        {
            private final Context context;

            public tabFactory(Context context)
            {
                this.context = context;
            }

            @Override
            public View createTabContent(String tag)
            {
                View v = new View(context);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(ActionBarActivity activity, TabHost tabHost, ViewPager pager)
        {
            super(activity.getSupportFragmentManager());
            fragmentManager = activity.getSupportFragmentManager();
            context = activity;
            this.tabHost = tabHost;
            viewPager = pager;
            this.tabHost.setOnTabChangedListener(this);
            viewPager.setAdapter(this);
            viewPager.setOnPageChangeListener(this);
        }

        // Specific tab is returned
        public int addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args)
        {
            tabSpec.setContent(new tabFactory(context));
            String tag = tabSpec.getTag();
            
            infoTabs info = new infoTabs(tag, clss, args);
            tabs.add(info);
            tabHost.addTab(tabSpec);
            notifyDataSetChanged();

            int addedIndex = tabHost.getTabWidget().getChildCount() - 1;

            // If a tab height has been set then find the last tab and adjst it
            if(defaultTabHeight != tabHeight)
            {
                tabHost.getTabWidget().getChildAt(addedIndex).getLayoutParams().height = defaultTabHeight;
            }
            return addedIndex;
        }

        @Override
        public int getCount()
        {
            return tabs.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            infoTabs info = tabs.get(position);
            return Fragment.instantiate(context, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId)
        {
            int position = tabHost.getCurrentTab();
            viewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            TabWidget widget = tabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            tabHost.setCurrentTab(position);
            tabHost.getTabContentView().requestFocus(View.FOCUS_FORWARD);
            widget.setDescendantFocusability(oldFocusability);
        }

        // Update the menu when scroll is almost done, and once when completely done
        @Override
        public void onPageScrollStateChanged(int state) {
            if(ViewPager.SCROLL_STATE_IDLE == state || ViewPager.SCROLL_STATE_SETTLING == state) {
                context.supportInvalidateOptionsMenu();
            }
        }
    }
}