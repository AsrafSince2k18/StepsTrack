package com.since.run.domain.activerun_screen

import com.since.core.domain.active_run.LocationWithTimeStamp
import com.since.run.domain.repo.LocationObserve
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@PurposeOfClass(mssg = "WHY THIS CLASS USED SOME STATE FLOW BEACAUSE OF THIS CLASS SINGLETON,Reason you declare state viewmodel class" +
        "Viewmodel scope cancel also data lost, so i decide using singleTon,Application lifecycle aware")
class TrackerData(
    private val locationObserve: LocationObserve,
    private val applicationScope: CoroutineScope
) {
    val time = CurrentTimeUpdate.currentTime()
        .stateIn(applicationScope, SharingStarted.Eagerly, System.currentTimeMillis())

   private val observeTheLocation = MutableStateFlow(false)

    private val startRun = MutableStateFlow(false)

    private val _runData = MutableStateFlow(RunData())
    val runData = _runData.asStateFlow()
    private val _duration = MutableStateFlow(Duration.ZERO)
    val duration = _duration.asStateFlow()

    val currentLocation = observeTheLocation
        .flatMapLatest {
            if(it){
                locationObserve.observeLocation(1000)
            }else{
                flowOf()
            }
        }.stateIn(applicationScope, SharingStarted.Lazily,null)


    init {

        startRun
            .onEach { active->
                if(!active){
                    val userPauseUpdate = buildList {
                        addAll(runData.value.listOfListLocation)
                        add(emptyList())
                    }.toList()
                    _runData.update {
                        it.copy(
                            listOfListLocation = userPauseUpdate
                        )
                    }
                }
            }
            .flatMapLatest{start->
                if(start){
                    Timer.elapsedTimer()
                }else{
                    flowOf()
                }
            }.onEach {
                _duration.value+=it
            }.launchIn(applicationScope)



        currentLocation.filterNotNull()
            .combineTransform(startRun){location,runStatus->
                if(runStatus){
                    emit(location)
                }
            }.zip(_duration){location,duration ->
                LocationWithTimeStamp(
                    locationWithAltitude = location,
                    elapsedTime = duration
                )
            }.onEach { location->
                val currentLocation = runData.value.listOfListLocation
                val innerListLastUpdate = if(currentLocation.isNotEmpty()){
                    currentLocation.last() +  location
                }else{
                    listOf(location)
                }

                val updatedLocation = currentLocation.replaceLast(innerListLastUpdate)

                val distanceMeter = locationToLocationDistance(location = updatedLocation)
                val distanceKm = distanceMeter/ 1000.0

                val paceCalculate = if(distanceKm<0.0){
                    0
                }else{
                    (location.elapsedTime.inWholeSeconds / distanceKm).toInt()
                }

                _runData.update {
                    RunData(
                        distanceMeter = distanceMeter,
                        pace = paceCalculate.seconds,
                        listOfListLocation = updatedLocation
                    )
                }

            }.launchIn(applicationScope)


    }

    fun startObserveLocation(allPermissionGranted: Boolean){
        observeTheLocation.value=allPermissionGranted
    }


    fun runStatus(active: Boolean){
        startRun.value=active
    }


    fun stopRunning(){
        _duration.value= Duration.ZERO
        _runData.value= RunData()
    }

    private fun <T> List<List<T>>.replaceLast(location:List<T>) : List<List<T>>{
        if(this.isEmpty()){
            return listOf(location)
        }

        return dropLast(1) + listOf(location)
    }
}



annotation class PurposeOfClass(val mssg:String)