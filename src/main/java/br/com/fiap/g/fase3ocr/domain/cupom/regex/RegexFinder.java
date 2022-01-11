package br.com.fiap.g.fase3ocr.domain.cupom.regex;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public abstract class RegexFinder {

    private String payload;

    public RegexFinder(String payload) {
        this.payload = payload;
    }

    public String findFirst(List<String> values) {
        return findFirst(values, 0);
    }

    public String findFirst(List<String> values, int group) {
        for (String value : values) {
            Pattern p = Pattern.compile(value);
            Matcher m = p.matcher(payload);
            if (m.find()) return m.group(group);
        }
        return null;
    }

    public String find(String value) {
        return find(value, 0);
    }

    public String find(String value, int group) {
        Pattern p = Pattern.compile(value);
        Matcher m = p.matcher(payload);
        return m.find() ? m.group(group) : null;
    }

    public Matcher matcher(String value) {
        Pattern p = Pattern.compile(value);
        return p.matcher(payload);
    }

    protected String removeNonDigit(String value) {
        return StringUtils.isBlank(value)
                ? value
                : value.replaceAll("[^0-9]", "");
    }

    protected String normalizePriceAmount(String value) {
        return StringUtils.isBlank(value)
                ? value
                : value.replaceAll("[^,0-9]", "");
    }

    protected String clampTo(int maxValue, String value) {
        return value.substring(0, Math.min(maxValue, value.length()));
    }
}
