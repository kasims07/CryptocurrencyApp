package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): kotlinx.coroutines.flow.Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoin().map { it.toCoin() }
            emit(Resource.Success(coins))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?:"Unexpected error occured"))

        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach Internet connection, Check your Internet connection"))

        }
    }

}