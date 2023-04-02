package com.example.tvshowsappmvvm.features.tv_show_details

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.example.tvshowsappmvvm.ImageSliderAdapter
import com.mma.tvshowsappmvvm.R
import com.mma.tvshowsappmvvm.databinding.FragmentTvShowsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetails:Fragment(R.layout.fragment_tv_shows_details) {
    private var currentBinding: FragmentTvShowsDetailsBinding?=null
    private val binding get() = currentBinding!!
    private val viewModel: TvShowDetailsViewModel by viewModels()
    private val args by navArgs<TvShowDetailsArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = FragmentTvShowsDetailsBinding.bind(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val binding = FragmentTvShowsDetailsBinding.bind(view)
        getTvShowDetails()
    }

    private fun getTvShowDetails(){
        val tvShowId:String = args.dataObject.id.toString()
        viewModel.getTVShowDetails(tvShowId)?.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.tvShow.pictures != null) {
                    loadImageSlider(it.tvShow.pictures)
                }
                Glide.with(this@TvShowDetails)
                    .load(it.tvShow.image_path)
                    .into(binding.imageTVShow)
                binding.imageTVShow.visibility = View.VISIBLE
                binding.textDescription.text =
                    HtmlCompat.fromHtml(
                        it.tvShow.description,
                        FROM_HTML_MODE_LEGACY
                    ).toString()

                binding.textDescription.visibility = View.VISIBLE
                binding.textReadMore.visibility = View.VISIBLE
                binding.textReadMore.setOnClickListener {
                    if (binding.textReadMore.text.toString() == "Read more"
                    ) {
                        binding.textDescription.maxLines = Int.MAX_VALUE
                        binding.textDescription.ellipsize = null
                        binding.textReadMore.setText(R.string.read_less)
                    } else {
                        binding.textDescription.maxLines = 4
                        binding.textDescription.ellipsize =
                            TextUtils.TruncateAt.END
                        binding.textReadMore.setText(R.string.read_more)
                    }
                }
                loadBasicDetails()
            }
        }

    }

    private fun loadImageSlider(pictures: List<String>) {
        binding.sliderImageViewer.offscreenPageLimit = 2
        binding.sliderImageViewer.adapter = ImageSliderAdapter(pictures)
        binding.sliderImageViewer.visibility = View.VISIBLE
        binding.viewFadingEdge.visibility = View.VISIBLE
        setUpSliderIndicator(pictures.size)
        binding.sliderImageViewer.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(binding,position)
            }
        })
    }

    private fun setUpSliderIndicator(size: Int) {
        val indicators = arrayOfNulls<ImageView>(size)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.background_slider_indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = layoutParams
            binding.layoutSliderIndicator.addView(indicators[i])
        }
        binding.layoutSliderIndicator.visibility = View.VISIBLE
        setCurrentSliderIndicator(binding,0)
    }

    private fun setCurrentSliderIndicator(tvShowDetailsBinding:FragmentTvShowsDetailsBinding, position: Int) {
        val childCount: Int = tvShowDetailsBinding.layoutSliderIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = tvShowDetailsBinding.layoutSliderIndicator.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.background_slider_indicator_inactive
                    )
                )
            }
        }
    }

    private fun loadBasicDetails() {
        binding.textName.text = args.dataObject.name

        binding.textNetworkCountry.text =
            args.dataObject.network + " (" +
                   args.dataObject.country + ")"

        binding.textStatus.text = args.dataObject.status
        binding.textStarted.text = args.dataObject.start_date
        binding.textName.visibility = View.VISIBLE
        binding.textNetworkCountry.visibility = View.VISIBLE
        binding.textStatus.visibility = View.VISIBLE
        binding.textStarted.visibility = View.VISIBLE
    }
}