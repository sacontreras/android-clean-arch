package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event

enum class Event {
    SERVICE_STOPPED,
    SERVICE_STOPPED_UNEXPECTEDLY,
    SERVICE_STARTING,
    SERVICE_CONNECTED,
    SERVICE_RUNNING,
    SERVICE_STOPPING,
    SERVICE_DISCONNECTING,
    SERVICE_DISCONNECTED,
    NO_GPS,             //FINE location updates
    NO_NETWORK,         //COARSE location updates
    NO_GPS_OR_NETWORK,
    MISSING_PERRMISSIONS,
    LOCATION_CHANGED,   //the secret sauce!
    LOCATION_PLATFORM_PROVIDER_STATUS_CHANGED,
    LOCATION_PLATFORM_PROVIDER_ENABLED,
    LOCATION_PLATFORM_PROVIDER_DISABLED
}