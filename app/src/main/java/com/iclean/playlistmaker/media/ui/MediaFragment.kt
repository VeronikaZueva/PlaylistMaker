package com.iclean.playlistmaker.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.FragmentMediaBinding


class MediaFragment : Fragment() {
    private lateinit var binding: FragmentMediaBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Работаем с TabLayout и ViewPager
        binding.viewPager.adapter = MediaViewPagerAdapter(childFragmentManager, lifecycle)

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) {
                tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.favorite_tracks)
                1 -> tab.text = getString(R.string.playlist_tracks)
            }
        }
        tabMediator.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }

}