package com.sds.currencydisplay.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sds.currencydisplay.R
import com.sds.currencydisplay.databinding.ActivityMainBinding
import com.sds.currencydisplay.model.Internet.InterfaceForActivity
import com.sds.currencydisplay.model.Internet.InternetBroadcastReceiver
import com.sds.currencydisplay.model.adapter.CurrencyAdapter
import com.sds.currencydisplay.model.repository.Repository
import com.sds.currencydisplay.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), InterfaceForActivity {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var recyclerView: RecyclerView? = null
    private var currencyAdapter: CurrencyAdapter? = null
    private var repository: Repository? = null
    private var job: Job = Job()
    private var internetBroadcast: InternetBroadcastReceiver? = null
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        internetBroadcast = InternetBroadcastReceiver(this)

        repository = Repository(this)

        recyclerView = binding.idRvCurrency
        currencyAdapter = CurrencyAdapter()
        recyclerView?.adapter = currencyAdapter

        mainViewModel?.currency?.observe(this) { data ->
            repository?.showToast(getString(R.string.titleToast))
            isShowProgressBar(false) // скрыть ProgressBar
            isShowRecyclerView() // показать RecyclerView
            showLastUploadTime(data.body()?.date.toString()) // показать время
            currencyAdapter?.setList(data.body()?.valute?.values?.toList()) // отправить полученные данные для отображения
        }

    }

    /** регистрация ресивера */
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(internetBroadcast, filter)
    }

    /** отвязка ресивера */
    override fun onStop() {
        super.onStop()
        unregisterReceiver(internetBroadcast)
    }

    /** выход из приложения */
    override fun onBackPressed() {
        super.onBackPressed()
        if (job.isActive) {
            job.cancel()
        }
        this.finishAffinity()
    }

    /** функция показа или сокрытия загрузочной анимации */
    private fun isShowProgressBar(isShow: Boolean) {
        binding.idPb.isVisible = isShow
    }

    /** функция показа списка */
    private fun isShowRecyclerView() {
        binding.idRvCurrency.isVisible = true
    }

    /** функция показа последнего времени загрузки данных */
    private fun showLastUploadTime(text: String) {
        binding.idTvLastUploadTime.text = text
    }

    /** функция просмотра состояния сети */
    override fun showChangeInternet(flag: Boolean) {
        if (flag) {
            repository?.showToast("интернет есть")
            createJob()
        } else {
            repository?.showToast(getString(R.string.titleToast2))
            job.cancel()
        }
    }

    private fun createJob() {
        job = lifecycleScope.launch(Dispatchers.Main) {
            while (isActive) {
                mainViewModel?.getCurrency() // вызов функции каждые 30 сек
                isShowProgressBar(true) // показ загрузки
                delay(30000)
            }
        }
    }

}