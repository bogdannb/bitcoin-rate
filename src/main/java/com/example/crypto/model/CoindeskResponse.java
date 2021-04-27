package com.example.crypto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoindeskResponse {

    private double bitcoinToUsdRate;
    private Date exchangeDateUTC;

    @JsonProperty("bpi")
    public void setBitcoinToUsdRate(Map<String, Map<String, String>> bpiMap) throws ParseException {
        Map<String, String> usdMap = bpiMap.getOrDefault("USD", new HashMap<>());
        String rate = usdMap.getOrDefault("rate", "0");
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        this.bitcoinToUsdRate = format.parse(rate).doubleValue();
    }

    @JsonProperty("time")
    public void setExchangeDateUTC(Map<String, String> timeMap) throws ParseException {
        String updateTime = timeMap.getOrDefault("updatedISO", "");
        if (!StringUtils.isEmpty(updateTime)) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            this.exchangeDateUTC = format.parse(updateTime);
        }
    }
}
