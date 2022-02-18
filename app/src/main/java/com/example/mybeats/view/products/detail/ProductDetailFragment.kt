package com.example.mybeats.view.products.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mybeats.R
import com.example.mybeats.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupView()
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            elevation = 0f
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.toolbar_color)))
            title = ""
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    private fun setupView() {
        val product = args.product
        binding.apply {
            tvModel.text = product.model
            tvConnection.text = product.connection
            tvCompatibility.text = product.compatibility
            tvPowerSupply.text = product.powerSupply
            tvAutonomy.text = product.autonomy
            tvHeight.text = product.height
            tvCapture.text = product.capture
        }
    }
}
