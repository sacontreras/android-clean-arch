package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.mock

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.LocationWeather
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class Util {
    class Fixed {
        companion object {
            val MOCK_LW_0__JSON_STR =
                "{\n" +
                "    \"coord\": {\n" +
                "        \"lon\": -117.23,\n" +
                "        \"lat\": 33.35\n" +
                "    },\n" +
                "    \"weather\": [\n" +
                "        {\n" +
                "            \"id\": 500,\n" +
                "            \"main\": \"Rain\",\n" +
                "            \"description\": \"light rain\",\n" +
                "            \"icon\": \"10n\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"base\": \"stations\",\n" +
                "    \"main\": {\n" +
                "        \"temp\": 293.41,\n" +
                "        \"pressure\": 1012,\n" +
                "        \"humidity\": 80,\n" +
                "        \"temp_min\": 291.25,\n" +
                "        \"temp_max\": 294.85\n" +
                "    },\n" +
                "    \"visibility\": 11265,\n" +
                "    \"wind\": {\n" +
                "        \"speed\": 2.1,\n" +
                "        \"deg\": 190\n" +
                "    },\n" +
                "    \"clouds\": {\n" +
                "        \"all\": 90\n" +
                "    },\n" +
                "    \"dt\": 1535974500,\n" +
                "    \"sys\": {\n" +
                "        \"type\": 1,\n" +
                "        \"id\": 441,\n" +
                "        \"message\": 0.0051,\n" +
                "        \"country\": \"US\",\n" +
                "        \"sunrise\": 1535981091,\n" +
                "        \"sunset\": 1536027041\n" +
                "    },\n" +
                "    \"id\": 5347578,\n" +
                "    \"name\": \"Fallbrook\",\n" +
                "    \"cod\": 200\n" +
                "}"

            val MOCK_LW_1__JSON_STR =
                "{\n" +
                "    \"coord\": {\n" +
                "        \"lon\": -117.23,\n" +
                "        \"lat\": 33.35\n" +
                "    },\n" +
                "    \"weather\": [\n" +
                "        {\n" +
                "            \"id\": 500,\n" +
                "            \"main\": \"Rain\",\n" +
                "            \"description\": \"light rain\",\n" +
                "            \"icon\": \"10d\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"base\": \"stations\",\n" +
                "    \"main\": {\n" +
                "        \"temp\": 297.59,\n" +
                "        \"pressure\": 1012,\n" +
                "        \"humidity\": 73,\n" +
                "        \"temp_min\": 295.35,\n" +
                "        \"temp_max\": 299.25\n" +
                "    },\n" +
                "    \"visibility\": 16093,\n" +
                "    \"wind\": {\n" +
                "        \"speed\": 4.6,\n" +
                "        \"deg\": 250\n" +
                "    },\n" +
                "    \"clouds\": {\n" +
                "        \"all\": 90\n" +
                "    },\n" +
                "    \"dt\": 1536003240,\n" +
                "    \"sys\": {\n" +
                "        \"type\": 1,\n" +
                "        \"id\": 369,\n" +
                "        \"message\": 0.0063,\n" +
                "        \"country\": \"US\",\n" +
                "        \"sunrise\": 1535981104,\n" +
                "        \"sunset\": 1536027015\n" +
                "    },\n" +
                "    \"id\": 5347578,\n" +
                "    \"name\": \"Fallbrook\",\n" +
                "    \"cod\": 200\n" +
                "}"

            val mockLWJsonObject: ArrayList<JsonObject> = arrayListOf(
                JsonParser().parse(MOCK_LW_0__JSON_STR).asJsonObject,
                JsonParser().parse(MOCK_LW_1__JSON_STR).asJsonObject
            )
            val mockLW: ArrayList<LocationWeather> = arrayListOf(
                Gson().fromJson(MOCK_LW_0__JSON_STR, LocationWeather::class.java),
                Gson().fromJson(MOCK_LW_1__JSON_STR, LocationWeather::class.java)
            )
        }
    }
}