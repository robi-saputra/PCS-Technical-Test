package pcs.technicaltest.robisaputra.ui.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import pcs.technicaltest.robisaputra.databinding.FragmentUserDetailBinding
import rmtz.lib.baseapplication.BaseFragment
import rmtz.lib.baseapplication.utils.formatDate
import rmtz.lib.baseapplication.utils.parseNames
import rmtz.lib.baseapplication.utils.timeElapsed
import robi.codingchallenge.networks.data.User
import java.util.Locale

@AndroidEntryPoint
class DetailUserFragment: BaseFragment<FragmentUserDetailBinding>() {
    private var user: User? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserDetailBinding {
        user = requireArguments().getParcelable("User")
        return FragmentUserDetailBinding.inflate(inflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        if (user!=null) {
            binding.apply {
                Glide.with(requireContext()).load(user!!.avatar).centerCrop().into(ivAvatar)
                topAppBar.setTitle(user?.name)

                val address = listOfNotNull(
                    user?.addressNo?.takeIf { it.isNotBlank() },
                    user?.street?.takeIf { it.isNotBlank() },
                    user?.city?.takeIf { it.isNotBlank() },
                    user?.county?.takeIf { it.isNotBlank() },
                    user?.zipCode?.takeIf { it.isNotBlank() },
                    user?.country?.takeIf { it.isNotBlank() }
                ).joinToString(", ")

                tvFirstName.text = user?.name?.parseNames()!!.first
                tvLastName.text = user?.name?.parseNames()!!.second
                tvJoined.text = user?.createdAt.formatDate("yyyy-MM-dd'T'HH:mm:ss.SSSX", "dd MMM yyyy", Locale.ROOT)
                tvElapsed.text = user?.createdAt.timeElapsed("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ROOT)
                tvAddress.text = address
            }
        }
    }

    private fun setupToolbar() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}