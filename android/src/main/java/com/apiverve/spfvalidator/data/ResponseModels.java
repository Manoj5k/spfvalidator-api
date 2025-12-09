// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     SPFValidatorData data = Converter.fromJsonString(jsonString);

package com.apiverve.spfvalidator.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static SPFValidatorData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(SPFValidatorData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(SPFValidatorData.class);
        writer = mapper.writerFor(SPFValidatorData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// SPFValidatorData.java

package com.apiverve.spfvalidator.data;

import com.fasterxml.jackson.annotation.*;

public class SPFValidatorData {
    private AuthorizedIPS authorizedIPS;
    private long dnsLookupsNum;
    private String[] domainsExtracted;
    private long elapsedMS;
    private boolean hasIssues;
    private boolean hasSPFRecord;
    private String host;
    private boolean ipPass;
    private boolean macrosFound;
    private String spfRecord;
    private SPFRecordsList[] spfRecordsList;
    private boolean spfValid;

    @JsonProperty("authorized_ips")
    public AuthorizedIPS getAuthorizedIPS() { return authorizedIPS; }
    @JsonProperty("authorized_ips")
    public void setAuthorizedIPS(AuthorizedIPS value) { this.authorizedIPS = value; }

    @JsonProperty("dns_lookups_num")
    public long getDNSLookupsNum() { return dnsLookupsNum; }
    @JsonProperty("dns_lookups_num")
    public void setDNSLookupsNum(long value) { this.dnsLookupsNum = value; }

    @JsonProperty("domains_extracted")
    public String[] getDomainsExtracted() { return domainsExtracted; }
    @JsonProperty("domains_extracted")
    public void setDomainsExtracted(String[] value) { this.domainsExtracted = value; }

    @JsonProperty("elapsed_ms")
    public long getElapsedMS() { return elapsedMS; }
    @JsonProperty("elapsed_ms")
    public void setElapsedMS(long value) { this.elapsedMS = value; }

    @JsonProperty("has_issues")
    public boolean getHasIssues() { return hasIssues; }
    @JsonProperty("has_issues")
    public void setHasIssues(boolean value) { this.hasIssues = value; }

    @JsonProperty("has_spf_record")
    public boolean getHasSPFRecord() { return hasSPFRecord; }
    @JsonProperty("has_spf_record")
    public void setHasSPFRecord(boolean value) { this.hasSPFRecord = value; }

    @JsonProperty("host")
    public String getHost() { return host; }
    @JsonProperty("host")
    public void setHost(String value) { this.host = value; }

    @JsonProperty("ip_pass")
    public boolean getIPPass() { return ipPass; }
    @JsonProperty("ip_pass")
    public void setIPPass(boolean value) { this.ipPass = value; }

    @JsonProperty("macros_found")
    public boolean getMacrosFound() { return macrosFound; }
    @JsonProperty("macros_found")
    public void setMacrosFound(boolean value) { this.macrosFound = value; }

    @JsonProperty("spf_record")
    public String getSPFRecord() { return spfRecord; }
    @JsonProperty("spf_record")
    public void setSPFRecord(String value) { this.spfRecord = value; }

    @JsonProperty("spf_records_list")
    public SPFRecordsList[] getSPFRecordsList() { return spfRecordsList; }
    @JsonProperty("spf_records_list")
    public void setSPFRecordsList(SPFRecordsList[] value) { this.spfRecordsList = value; }

    @JsonProperty("spf_valid")
    public boolean getSPFValid() { return spfValid; }
    @JsonProperty("spf_valid")
    public void setSPFValid(boolean value) { this.spfValid = value; }
}

// AuthorizedIPS.java

package com.apiverve.spfvalidator.data;

import com.fasterxml.jackson.annotation.*;

public class AuthorizedIPS {
    private String[] ipv4;

    @JsonProperty("ipv4")
    public String[] getIpv4() { return ipv4; }
    @JsonProperty("ipv4")
    public void setIpv4(String[] value) { this.ipv4 = value; }
}

// SPFRecordsList.java

package com.apiverve.spfvalidator.data;

import com.fasterxml.jackson.annotation.*;

public class SPFRecordsList {
    private AuthorizedIPS authorizedIPS;
    private long charsNum;
    private String[] domains;
    private String origin;
    private String record;
    private boolean useMacro;

    @JsonProperty("authorized_ips")
    public AuthorizedIPS getAuthorizedIPS() { return authorizedIPS; }
    @JsonProperty("authorized_ips")
    public void setAuthorizedIPS(AuthorizedIPS value) { this.authorizedIPS = value; }

    @JsonProperty("chars_num")
    public long getCharsNum() { return charsNum; }
    @JsonProperty("chars_num")
    public void setCharsNum(long value) { this.charsNum = value; }

    @JsonProperty("domains")
    public String[] getDomains() { return domains; }
    @JsonProperty("domains")
    public void setDomains(String[] value) { this.domains = value; }

    @JsonProperty("origin")
    public String getOrigin() { return origin; }
    @JsonProperty("origin")
    public void setOrigin(String value) { this.origin = value; }

    @JsonProperty("record")
    public String getRecord() { return record; }
    @JsonProperty("record")
    public void setRecord(String value) { this.record = value; }

    @JsonProperty("use_macro")
    public boolean getUseMacro() { return useMacro; }
    @JsonProperty("use_macro")
    public void setUseMacro(boolean value) { this.useMacro = value; }
}