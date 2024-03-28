package com.sds.currencydisplay.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.sds.currencydisplay.databinding.ActivityMainBinding
import com.sds.currencydisplay.model.adapter.CurrencyAdapter
import com.sds.currencydisplay.model.repository.Repository
import com.sds.currencydisplay.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var recyclerView: RecyclerView? = null
    private var currencyAdapter: CurrencyAdapter? = null
    private var repository: Repository? = null
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        repository = Repository()

        // проверка интернет соединения
        if (repository?.checkInternet(this) == true) {

            val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

            // корутина с отложенным стартом
            job = CoroutineScope(Dispatchers.Main).launch(start = CoroutineStart.LAZY) {
                while (isActive) {
                    mainViewModel.getCurrency() // вызов функции каждые 30 сек
                    isShowProgressBar(true) // показ загрузки
                    delay(30000)
                }
            }

            recyclerView = binding?.idRvCurrency
            currencyAdapter = CurrencyAdapter()
            recyclerView?.adapter = currencyAdapter

            job.start() // запуск корутины

            mainViewModel.currency.observe(this) { data ->
                repository?.showToast(this, "новые данные получены")
                isShowProgressBar(false) // скрыть ProgressBar
                isShowRecyclerView() // показать RecyclerView
                showLastUploadTime(data.body()?.date.toString()) // показать время
                currencyAdapter?.setList(data.body()?.valute?.values?.toList()) // отправить полученные данные для отображения
            }

        } else {
            repository?.showToast(this, "нет интернет соединения")
        }

    }

    // выход из приложения
    override fun onBackPressed() {
        super.onBackPressed()
        if (job.isActive) {
            job.cancel()
        }
        this.finishAffinity()
    }

    // функция показа или сокрытия загрузочной анимации
    private fun isShowProgressBar(isShow: Boolean) {
        binding?.idPb?.isVisible = isShow
    }

    // функция показа списка
    private fun isShowRecyclerView() {
        binding?.idRvCurrency?.isVisible = true
    }

    // функция показа последнего времени загрузки данных
    private fun showLastUploadTime(text: String) {
        binding?.idTvLastUploadTime?.text = text
    }

}