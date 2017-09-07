package ru.acteek.weather


import ru.acteek.weather.utils.WeatherApiMock

class ApiClientSpec extends BaseSpec{

 WeatherApiMock.startServer("0.0.0.0",9091)


}
