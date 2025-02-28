package com.idz.rentit.fragments
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.idz.rentIt.databinding.FragmentPropertyListBinding
import com.idz.rentit.adapters.PropertyAdapter
import com.idz.rentit.enums.LoadingState
import com.idz.rentit.listeners.authentication.OnItemClickListener
import com.idz.rentit.notifications.NotificationManager
import com.idz.rentit.repository.Repository
import com.idz.rentit.viewModels.PropertyListFragmentViewModel
//import com.idz.rentit.databinding.FragmentPropertyListBinding
import java.util.Objects


class PropertyListFragment : PropertyBaseFragment() {
    private var viewBindings: FragmentPropertyListBinding? = null
    private var propertyCategoryPosition: Int? = null
    private var propertyAdapter: PropertyAdapter? = null
    private var viewModel: PropertyListFragmentViewModel? = null
//    private val retrofit: Retrofit =
//        Retrofit.Builder().baseUrl(property_API_URL).addConverterFactory(GsonConverterFactory.create())
//            .build()
//    private val service: PropertyApiCaller = retrofit.create<T>(PropertyApiCaller::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val displayMetrics: DisplayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        this.viewBindings = FragmentPropertyListBinding.inflate(inflater, container, false)
        viewBindings!!.propertyListFragmentPropertiesList.setHasFixedSize(true)
        viewBindings!!.propertyListFragmentPropertiesList.setLayoutManager(
            GridLayoutManager(
                requireContext(),
                Math.round(dpWidth / 137)
            )
        )
        this.propertyAdapter = PropertyAdapter(
            getLayoutInflater(),
            viewModel?.getPropertyList()?.value!!
        )
        viewBindings!!.propertyListFragmentPropertiesList.setAdapter(this.propertyAdapter)
//        syncPropertiesApiWithRemoteDb()
        this.configureMenuOptions(viewBindings!!.getRoot())
//        activateItemListListener()
        NotificationManager.instance()
            .getEventPropertyListLoadingState()
            .observe(
                getViewLifecycleOwner()
            ) { loadingState ->
                viewBindings!!.swipeRefresh.isRefreshing = loadingState === LoadingState.LOADING
            }
        return viewBindings!!.getRoot()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.viewModel = ViewModelProvider(this)[PropertyListFragmentViewModel::class.java]
    }

    private fun reloadPropertyList() {
        if (Objects.nonNull(viewModel?.getPropertyList()?.getValue())) {
            Repository.repositoryInstance.getLocalModel().propertyHandler.allProperties
        }
    }

    private fun activateItemListListener() {
        val listener = object : OnItemClickListener {
            override fun onItemClick(position: Int?) {
                val action = PropertyListFragmentDirections
                    .actionPropertyListFragmentToUserProfileFragment()
                findNavController().navigate(action)
            }
        }
        this.propertyAdapter?.setOnItemClickListener(listener)
    }

//    fun syncPropertysApiWithRemoteDb() {
//        if (Objects.nonNull(viewModel?.getPropertyList()) && !viewModel?.getPropertyList()) {
//            service.getJson(
//                property_API_KEY, Categories().getIdByName(
//                    viewModel.getpropertyCategory().getCategoryName()
//                )
//            ).enqueue((object : Callback<propertyApiList> {
//                override fun onResponse(
//                    call: Call<propertyApiList>,
//                    response: Response<propertyApiList>
//                ) {
//                    addpropertysToDb(response)
//                }
//
//                override fun onFailure(call: Call<propertyApiList>, throwable: Throwable) {
//                    Log.d("apiError", throwable.toString())
//                }
//            }))
//        }
//        viewModel.getpropertyList().observe(
//            getViewLifecycleOwner()
//        ) { propertys -> Repository.getRepositoryInstance().refreshAllpropertys() }
//        viewModel.getpropertyList().observe(
//            getViewLifecycleOwner()
//        ) { propertys -> reloadpropertyList() }
//    }

//    fun addPropertiesToDb(response: Response<propertyApiList>) {
//        val executor: propertyExecutor =
//            Repository.getRepositoryInstance().getFirebaseModel().getpropertyExecutor()
//
//        for (item in response.body().getResults()) {
//            executor.getpropertyByName(item.getOriginal_title()) { propertys ->
//                if (propertys.isEmpty() || propertys.stream().noneMatch { property ->
//                        property.getpropertyCategoryId()
//                            .contentEquals(
//                                viewModel.getpropertyCategory()
//                                    .getCategoryId()
//                            )
//                    }) {
//                    executor.addproperty(
//                        property(
//                            viewModel.getpropertyCategory().getCategoryId(),
//                            item.getOriginal_title(),
//                            item.getVote_average().toString(),
//                            item.getOverview(),
//                            item.getPoster_path()
//                        )
//                    ) {}
//                }
//            }
//        }
//    }
}