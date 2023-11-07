package com.example.study_choi.retrofit


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("cnvenFcltGuid")
    val cnvenFcltGuid: String,
    @SerializedName("mapLat")
    val mapLat: String,
    @SerializedName("mapLot")
    val mapLot: String,
    @SerializedName("mngTime")
    val mngTime: String,
    @SerializedName("pkgFclt")
    val pkgFclt: String,
    @SerializedName("refadNo")
    val refadNo: String,
    @SerializedName("tourUtlzAmt")
    val tourUtlzAmt: String,
    @SerializedName("tourspotAddr")
    val tourspotAddr: String,
    @SerializedName("tourspotDtlAddr")
    val tourspotDtlAddr: String,
    @SerializedName("tourspotNm")
    val tourspotNm: String,
    @SerializedName("tourspotSumm")
    val tourspotSumm: String,
    @SerializedName("tourspotZip")
    val tourspotZip: String,
    @SerializedName("urlAddr")
    val urlAddr: String
)