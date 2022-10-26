package net.shinedev.github.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.shinedev.github.FollowerFragment
import net.shinedev.github.FollowingFragment
import net.shinedev.github.R

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var username: String? = null
    var follower: Int? = 0
    var following: Int? = 0

    @StringRes
    private val titles = intArrayOf(R.string.follower, R.string.following)

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        val resources = mContext.resources
        return when (position) {
            0 -> resources.getString(titles[position], follower)
            1 -> resources.getString(titles[position], following)
            else -> resources.getString(titles[position], follower)
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }
}