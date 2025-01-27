package com.example.navigationmenu.ui.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.navigationmenu.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HeroDetailFragment : Fragment() {

    companion object {
        fun newInstance(hero: HeroInfo): HeroDetailFragment {
            val fragment = HeroDetailFragment()
            val args = Bundle()
            args.putString("HERO_NAME", hero.name)
            args.putString("HERO_IMAGE_URL", hero.imageUrl)
            args.putString("HERO_DESCRIPTION", hero.description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hero_detail, container, false)

        val heroImageView: ImageView = view.findViewById(R.id.hero_image)
        val heroNameTextView: TextView = view.findViewById(R.id.hero_name)
        val heroDescriptionTextView: TextView = view.findViewById(R.id.hero_description)

        val heroName = arguments?.getString("HERO_NAME")
        val heroImageUrl = arguments?.getString("HERO_IMAGE_URL")
        val heroDescription = arguments?.getString("HERO_DESCRIPTION")

        heroNameTextView.text = heroName
        heroDescriptionTextView.text = heroDescription

        // Загрузка изображения
        Glide.with(this).load(heroImageUrl).into(heroImageView)

        return view
    }
}

