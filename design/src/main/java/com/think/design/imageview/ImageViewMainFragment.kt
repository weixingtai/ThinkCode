package com.think.design.imageview

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentImageViewMainBinding
import java.util.Random

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/2/2 16:37
 * desc   :
 */
class ImageViewMainFragment : BaseLandingFragment<FragmentImageViewMainBinding>() {

    override fun getTitleResId(): Int {
        return R.string.imageview_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ) = FragmentImageViewMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shapes = SparseArray<ShapeAppearanceModel>()
        shapes.put(
            R.id.image_diamond,
            ShapeAppearanceModel.builder().setAllCorners(CornerFamily.CUT, 0f)
                .setAllCornerSizes(ShapeAppearanceModel.PILL).build()
        )
        shapes.put(
            R.id.image_circle,
            ShapeAppearanceModel.builder().setAllCornerSizes(ShapeAppearanceModel.PILL).build()
        )
        shapes.put(R.id.image_square, ShapeAppearanceModel.builder().build())

        val random = Random()

        binding.imageToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) {
                return@addOnButtonCheckedListener
            }
            val shape = shapes.get(checkedId)

            binding.imageView.setImageResource(
                if (random.nextBoolean()) R.drawable.image_dog_open_eye else R.drawable.image_dog_close_eye
            )
            binding.imageView.setShapeAppearanceModel(shape)
            binding.imageIcon.setShapeAppearanceModel(shape)
        }
    }

}