package com.example.n12.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.n12.*
import com.example.n12.adapter.CountAdapter
import com.example.n12.databinding.FragmentCountBinding
import com.example.n12.model.GithubUser
import com.example.n12.retrofit.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CountFragment : Fragment() {
    private var _binding: FragmentCountBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCountBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = false
            }
            val layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

            recyclerView.layoutManager = layoutManager
            recyclerView.addSpaceDecoration(SPACE_SIZE)
            recyclerView.addPaginationScrollListener(layoutManager, 15) {

            }

        }
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(view.context)


            GitHubService.githubApi.getUsers(0, 50)
                .enqueue(object : Callback<List<GithubUser>> {
                    override fun onResponse(
                        call: Call<List<GithubUser>>,
                        response: Response<List<GithubUser>>
                    ) {
                        if (response.isSuccessful) {
                            val users = response.body() ?: return
                            recyclerView.adapter = CountAdapter(users) {
                                findNavController().navigate(
                                    CountFragmentDirections.item(it.login)
                                )
                            }
                        } else {
                            HttpException(response).message()
                        }
                    }

                    override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SPACE_SIZE = 50
    }
}





